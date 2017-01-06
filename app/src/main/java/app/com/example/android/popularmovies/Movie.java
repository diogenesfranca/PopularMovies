package app.com.example.android.popularmovies;

public class Movie {
    public final static String RESULTS_PROPERTY = "results";
    public final static String ID_PROPERTY = "id";
    public final static String TITLE_PROPERTY = "title";
    public final static String OVERVIEW_PROPERTY = "overview";
    public final static String POSTER_PATH_PROPERTY = "poster_path";
    private String id;
    private String title;
    private String overview;
    private String posterPath;

    public Movie(String id, String title, String overview, String posterPath){
        this.id = id;
        this.title = title;
        this. overview = overview;
        this.posterPath = posterPath;
    }

    public String getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getOverview (){
        return overview;
    }

    public String getPosterPath(){
        return posterPath;
    }
}
