package chuong.example.movieapp.Episode;

public class Episode {
    private String MovieId;

    private String Id;

    private String EpisodeNumber;

    private String Url;

    public Episode(String movieId, String id, String episodeNumber, String url) {
        MovieId = movieId;
        Id = id;
        EpisodeNumber = episodeNumber;
        Url = url;
    }

    public Episode() {
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEpisodeNumber() {
        return EpisodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        EpisodeNumber = episodeNumber;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
