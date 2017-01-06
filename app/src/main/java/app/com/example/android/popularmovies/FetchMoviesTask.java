package app.com.example.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<Boolean, Void, List<Movie>> {

    MovieAdapter movieAdapter;
    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    public FetchMoviesTask(MovieAdapter movieAdapter){
        this.movieAdapter = movieAdapter;
    }

    @Override
    protected List<Movie> doInBackground(Boolean... params) {
        boolean popularMovies = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonString = null;


        try {
            final String MOVIES_API_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
            final String SORT_BY_PARAM = "sort_by";
            final String CERTIFICATION_COUNTRY_PARAM = "certification_country";
            final String CERTIFICATION_PARAM = "certification";
            final String APY_KEY_PARAM = "api_key";

            Uri uri = null;

            if(popularMovies){
                uri = Uri.parse(MOVIES_API_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY_PARAM, "popularity.desc")
                        .appendQueryParameter(APY_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();
            }
            else{
                uri = Uri.parse(MOVIES_API_BASE_URL).buildUpon()
                        .appendQueryParameter(CERTIFICATION_COUNTRY_PARAM, "US")
                        .appendQueryParameter(CERTIFICATION_PARAM, "R")
                        .appendQueryParameter(SORT_BY_PARAM, "vote_average.desc")
                        .appendQueryParameter(APY_KEY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();
            }

            URL url = new URL(uri.toString());

            //Create the request and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line =reader.readLine()) != null){
                //Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                //But it does make debugging easier if you print out the completed
                //buffer for debugging.
                stringBuffer.append(line + "\n");
            }

            if(stringBuffer.length() == 0){
                //Stream was empty. Don't need to parse
                return null;
            }
            moviesJsonString = stringBuffer.toString();
        } catch (IOException e){
            Log.e(LOG_TAG,"Error", e);
            return null;
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                }
                catch (IOException e){
                    Log.e(LOG_TAG, "Error while closing stream", e);
                }
            }
        }
        try{
            return getMoviesFromJson(moviesJsonString);
        }
        catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        //Add movies to the Adapter
        if(movies != null){
            movieAdapter.clear();
            for(Movie movie: movies){
                movieAdapter.add(movie);
            }
        }
    }

    private List<Movie> getMoviesFromJson(String moviesJsonString) throws JSONException{
        JSONObject moviesJson = new JSONObject(moviesJsonString);
        JSONArray moviesArray = moviesJson.getJSONArray(Movie.RESULTS_PROPERTY);

        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (int i = 0; i< moviesArray.length(); i++){
            JSONObject movieJson = moviesArray.getJSONObject(i);

            String id, title, overview, posterPath;

            id = moviesJson.getString(Movie.ID_PROPERTY);
            title = moviesJson.getString(Movie.TITLE_PROPERTY);
            overview = moviesJson.getString(Movie.OVERVIEW_PROPERTY);
            posterPath = moviesJson.getString(Movie.POSTER_PATH_PROPERTY);

            Movie movie  = new Movie(id, title, overview, posterPath);
            movies.add(movie);
        }
        return movies;
    }
}
