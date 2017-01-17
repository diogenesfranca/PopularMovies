package app.com.example.android.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMoviesService {
    @GET("movie/{search_type}")
    Call<MoviesSerializer> getMovies(@Path("search_type") String searchType, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey);
}