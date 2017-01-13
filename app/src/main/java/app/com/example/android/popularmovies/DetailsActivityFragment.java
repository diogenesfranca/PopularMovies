package app.com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);
            getMovie(movieId, rootView);
        }
        return rootView;
    }

    private void getMovie(String movieId, View rootView){
        FetchMovieTask movieTask = new FetchMovieTask(rootView, getActivity());
        movieTask.execute(movieId);
    }
}