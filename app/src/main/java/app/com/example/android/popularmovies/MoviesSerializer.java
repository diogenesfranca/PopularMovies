package app.com.example.android.popularmovies;

import java.util.List;

public class MoviesSerializer {
    private List<Movie> results;

    public MoviesSerializer(List<Movie> movies) {
        this.results = movies;
    }

    public List<Movie> getMovies() {
        return this.results;
    }
}