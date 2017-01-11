package app.com.example.android.popularmovies;

public class Movie {
    public final static String RESULTS_PROPERTY = "results";
    public final static String ID_PROPERTY = "id";
    public final static String TITLE_PROPERTY = "title";
    public final static String ORIGINAL_TITLE_PROPERTY = "original_title";
    public final static String OVERVIEW_PROPERTY = "overview";
    public final static String VOTE_AVERAGE_PROPERTY = "vote_average";
    public final static String RELEASE_DATE_PROPERTY = "release_date";
    public final static String POSTER_PATH_PROPERTY = "poster_path";
    private String id;
    private String title;
    private String originalTitle;
    private String overview;
    private String voteAverage;
    private String releaseDate;
    private String posterPath;

    public Movie(String id, String title, String originalTitle, String overview, String voteAverage, String releaseDate, String posterPath){
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    public String getId(){ return id; }

    public String getTitle(){ return title; }

    public String getOriginalTitle() { return originalTitle; }

    public String getOverview (){ return overview; }

    public String getVoteAverage (){ return voteAverage; }

    public String getReleaseDate (){ return releaseDate; }

    public String getPosterPath(){ return posterPath; }
}
