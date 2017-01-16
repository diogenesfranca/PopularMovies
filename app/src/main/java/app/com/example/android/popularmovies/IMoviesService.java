package app.com.example.android.popularmovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IMoviesService {
    @GET("movie/{search_type}")
    Call<List<Movie>> getMovies(@Path("search_type") String searchType);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") String movieId);
}