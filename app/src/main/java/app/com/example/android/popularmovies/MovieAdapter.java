package app.com.example.android.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie>{

    public MovieAdapter(Context context, List<Movie> movies) {
        //resource int: The resource ID for a layout file containing a TextView
        //to use when instantiating views. not used in this case, so 0.
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Gets the Movie object from the ArrayAdapter in the position given
        Movie movie = getItem(position);

        //The Adapter recycle views to AdapterViews.
        //If this is a new View object then we inflate the layout, if not,
        //this view already has the layout inflated from a previous call to getView,
        //and we modify the View widgets as usual.
        //Explanation from Udacity android-custom-arrayadapter project in:
        //https://github.com/udacity/android-custom-arrayadapter/blob/master/app/src/main/java/demo/example/com/customarrayadapter/AndroidFlavorAdapter.java
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movies, parent, false);
        }

        ImageView poster = (ImageView) convertView.findViewById(R.id.grid_item_movies_imageview);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(poster);

        return convertView;
    }
}
