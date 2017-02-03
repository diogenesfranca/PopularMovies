package app.com.example.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityFragment extends Fragment implements Callback<MoviesSerializer> {

    private MovieAdapter mMovieAdapter;
    private RecyclerView mMoviesRecyclerView;
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMovies();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        //GridView moviesGridView = (GridView) rootView.findViewById(R.id.gridview_main_fragment);
         mMoviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_main_fragment);
        //moviesGridView.setAdapter(movieAdapter);

//        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String movieId = movieAdapter.getItem(i).getId();
//                Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
//                detailsIntent.putExtra(Intent.EXTRA_TEXT, movieId);
//                startActivity(detailsIntent);
//            }
//        });

        return rootView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        getMovies();
//    }

    private void getMovies(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchPreference = preferences.getString(getString(R.string.preferences_search_type_key),
                getString(R.string.preferences_search_type_value_popular_movies));
        boolean popularMovies = searchPreference.equals(getString(R.string.preferences_search_type_value_popular_movies));

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        String moviesApiBaseUrl = "http://api.themoviedb.org/3/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(moviesApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IMoviesService moviesService = retrofit.create(IMoviesService.class);

        Call<MoviesSerializer> call;
        //Put the API in the gradle global properties in the user's home directory under the .gradle folder and the gradle.properties file, if the file doesn't exist, then create it.
        if(popularMovies){
            call = moviesService.getMovies("popular", BuildConfig.THE_MOVIE_DB_API_KEY);
        }
        else{
            call = moviesService.getMovies("top_rated", BuildConfig.THE_MOVIE_DB_API_KEY);
        }
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MoviesSerializer> call, Response<MoviesSerializer> response) {
        MoviesSerializer movieSerializer = response.body();
        if(movieSerializer != null && movieSerializer.getMovies() != null){
            mMovieAdapter = new MovieAdapter(getActivity(), movieSerializer.getMovies());
            mMoviesRecyclerView.setAdapter(mMovieAdapter);
            mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            //movieAdapter.clear();
            //movieAdapter.addAll(movieSerializer.getMovies());
        }
    }

    @Override
    public void onFailure(Call<MoviesSerializer> call, Throwable t) {
        Toast.makeText(getActivity(), "Erro ao carregar os filmes.", Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, t.getMessage(), t);
    }
}