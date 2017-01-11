package app.com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivityFragment extends Fragment {

    MovieAdapter movieAdapter;
    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        GridView moviesGridView = (GridView) rootView.findViewById(R.id.gridview_main_fragment);
        moviesGridView.setAdapter(movieAdapter);

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String movieId = movieAdapter.getItem(i).getId();
                Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
                detailsIntent.putExtra(Intent.EXTRA_TEXT, movieId);
                startActivity(detailsIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovies();
    }

    private void getMovies(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchPreference = preferences.getString(getString(R.string.preferences_search_type_key),
                getString(R.string.preferences_search_type_value_popular_movies));
        FetchMoviesTask moviesTask = new FetchMoviesTask(movieAdapter);
        boolean popularMoviesPreference = searchPreference.equals(getString(R.string.preferences_search_type_value_popular_movies));
        moviesTask.execute(popularMoviesPreference);
    }
}