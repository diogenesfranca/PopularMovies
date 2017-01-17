package app.com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivityFragment extends Fragment implements Callback<Movie> {

    private final String LOG_TAG = DetailsActivityFragment.class.getSimpleName();
    private View rootView;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        this.rootView = rootView;

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);
            getMovie(movieId);
        }
        return rootView;
    }

    private void getMovie(String movieId) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        String moviesApiBaseUrl = "http://api.themoviedb.org/3/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(moviesApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IMoviesService moviesService = retrofit.create(IMoviesService.class);

        //Put the API in the gradle global properties in the user's home directory under the .gradle folder and the gradle.properties file, if the file doesn't exist, then create it.
        Call<Movie> call = moviesService.getMovie(movieId, BuildConfig.THE_MOVIE_DB_API_KEY);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        Movie movie = response.body();
        if(movie != null){
            ((TextView) rootView.findViewById(R.id.textview_original_title_details_fragment))
                    .setText(movie.getOriginalTitle());
            ImageView poster = (ImageView) rootView.findViewById(R.id.imageview_poster_details_fragment);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).into(poster);

            View linearLayout = rootView.findViewById(R.id.linearlayout_right_info_details_fragment);
            ((TextView) linearLayout.findViewById(R.id.textview_release_date_details_fragment))
                    .setText(movie.getReleaseYear());
            ((TextView) linearLayout.findViewById(R.id.textview_vote_average_details_fragment))
                    .setText(movie.getVoteAverage() + "/10");
            ((TextView) rootView.findViewById(R.id.textview_overview_details_fragment))
                    .setText(movie.getOverview());
        }
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        Toast.makeText(getActivity(), "Erro ao carregar o filme.", Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, t.getMessage(), t);
    }
}