package app.com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private Context mContext;
    private List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies){
        mContext = context;
        mMovies = movies;
    }

    //Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Inflate the custom layout
        View movieView = inflater.inflate(R.layout.recycler_item_movies, parent, false);

        //Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    //Involves populatign data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Get the data model based on position
        Movie movie = mMovies.get(position);

        if(movie != null) {
            ImageView imageView = holder.movieImageView;
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    //Provide a direct reference to each of the views within a data item
    //Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.recycler_item_movies_imageview);
        }
    }
}

//public class MovieAdapter extends ArrayAdapter<Movie>{
//
//    public MovieAdapter(Context context, List<Movie> movies) {
//        //resource int: The resource ID for a layout file containing a TextView
//        //to use when instantiating views. not used in this case, so 0.
//        super(context, 0, movies);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//
//        //The Adapter recycle views to AdapterViews.
//        //If this is a new View object then we inflate the layout, if not,
//        //this view already has the layout inflated from a previous call to getView,
//        //and we modify the View widgets as usual.
//        //Explanation from Udacity android-custom-arrayadapter project in:
//        //https://github.com/udacity/android-custom-arrayadapter/blob/master/app/src/main/java/demo/example/com/customarrayadapter/AndroidFlavorAdapter.java
//        if(convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movies, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.posterImageView = (ImageView) convertView.findViewById(R.id.grid_item_movies_imageview);
//
//            //store the holder with the ImageView
//            convertView.setTag(viewHolder);
//        }
//        else{
//            //this way I avoid using findViewById every time the getView method is called
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        //Gets the Movie object from the ArrayAdapter in the position given
//        Movie movie = getItem(position);
//
//        //get the posterImageView from the ViewHolder and fill it with Picasso
//        if(movie != null)
//            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).into(viewHolder.posterImageView);
//
//        return convertView;
//    }
//
//    private static class ViewHolder{
//        ImageView posterImageView;
//    }
//}