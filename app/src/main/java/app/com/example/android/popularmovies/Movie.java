package app.com.example.android.popularmovies;

public class Movie {
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

    public String getReleaseYear (){
        String[] dateParts = releaseDate.split("-");
        if(dateParts != null && dateParts.length > 0){
            return dateParts[0];
        }
        return "";
    }

    public String getPosterPath(){ return posterPath; }
}