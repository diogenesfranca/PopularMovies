package app.com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    ArrayAdapter<ImageView> moviesAdapter;
    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moviesAdapter = new ArrayAdapter<ImageView>(
                getActivity(),
                R.layout.grid_item_movies,
                R.id.grid_item_movies_imageview,
                new ArrayList<ImageView>());

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        final GridView moviesGridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        moviesGridView.setAdapter(moviesAdapter);
        return rootView;
    }
}
