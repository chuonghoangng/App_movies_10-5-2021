package chuong.example.movieapp.Movies_cu;

public class Movies {
    private String title;
    private String decription;
    private String thumbmail;
    private String studio;
    private String rating;
    private String streaminglink;
    private String converphoto;

    public Movies() {
    }

    public Movies(String title, String decription, String thumbmail, String converphoto, String streaminglink) {
        this.title = title;
        this.decription = decription;
        this.thumbmail = thumbmail;
        this.streaminglink = streaminglink;
        this.converphoto = converphoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getThumbmail() {
        return thumbmail;
    }

    public void setThumbmail(String thumbmail) {
        this.thumbmail = thumbmail;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStreaminglink() {
        return streaminglink;
    }

    public void setStreaminglink(String streaminglink) {
        this.streaminglink = streaminglink;
    }

    public String getConverphoto() {
        return converphoto;
    }

    public void setConverphoto(String converphoto) {
        this.converphoto = converphoto;
    }
}
