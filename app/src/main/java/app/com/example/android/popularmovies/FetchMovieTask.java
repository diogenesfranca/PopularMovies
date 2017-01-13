package app.com.example.android.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovieTask extends AsyncTask<String, Void, Movie> {

    private View rootView;
    private Context context;
    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    public FetchMovieTask(View rootView, Context context) {
        this.rootView = rootView;
        this.context = context;
    }

    @Override
    protected Movie doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonString = null;


        try {
            //params[0] is the id of the movie
            final String MOVIES_API_BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0] + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_KEY;

            Uri uri = null;

            uri = Uri.parse(MOVIES_API_BASE_URL);

            URL url = new URL(uri.toString());

            //Create the request and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                //Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                //But it does make debugging easier if you print out the completed
                //buffer for debugging.
                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) {
                //Stream was empty. Don't need to parse
                return null;
            }
            movieJsonString = stringBuffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error while closing stream", e);
                }
            }
        }
        try {
            return getMovieFromJson(movieJsonString);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        ((TextView) rootView.findViewById(R.id.textview_original_title_details_fragment))
                .setText(movie.getOriginalTitle());
        ImageView poster = (ImageView) rootView.findViewById(R.id.imageview_poster_details_fragment);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).into(poster);

        View linearLayout = rootView.findViewById(R.id.linearlayout_right_info_details_fragment);
        ((TextView) linearLayout.findViewById(R.id.textview_release_date_details_fragment))
                .setText(movie.getReleaseYear());
        ((TextView) linearLayout.findViewById(R.id.textview_vote_average_details_fragment))
                .setText(movie.getVoteAverage() + "/10");
        ((TextView) rootView.findViewById(R.id.textview_overview_details_fragment))
                .setText(movie.getOverview());
    }

    private Movie getMovieFromJson(String moviesJsonString) throws JSONException {
        JSONObject movieJson = new JSONObject(moviesJsonString);

        String id, title, originalTitle, overview, voteAverage, releaseDate, posterPath;

        id = movieJson.getString(Movie.ID_PROPERTY);
        title = movieJson.getString(Movie.TITLE_PROPERTY);
        originalTitle = movieJson.getString(Movie.ORIGINAL_TITLE_PROPERTY);
        overview = movieJson.getString(Movie.OVERVIEW_PROPERTY);
        voteAverage = movieJson.getString(Movie.VOTE_AVERAGE_PROPERTY);
        releaseDate = movieJson.getString(Movie.RELEASE_DATE_PROPERTY);
        posterPath = movieJson.getString(Movie.POSTER_PATH_PROPERTY);

        Movie movie = new Movie(id, title, originalTitle, overview, voteAverage, releaseDate, posterPath);

        return movie;
    }
}
