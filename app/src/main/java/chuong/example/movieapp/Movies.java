package chuong.example.movieapp;

public class Movies {
    private String title;
    private String decription;
    private int thumbmail;
    private String studio;
    private String rating;
    private String streaminglink;
    private int converphoto;
    public Movies(String title, int thumbmail, int converphoto) {
        this.title = title;
        this.thumbmail = thumbmail;
        this.converphoto = converphoto;
    }

    public Movies(String title, String decription, int thumbmail, String studio, String rating, String streaminglink) {
        this.title = title;
        this.decription = decription;
        this.thumbmail = thumbmail;
        this.studio = studio;
        this.rating = rating;
        this.streaminglink = streaminglink;
    }

    public Movies(String title, int thumbmail) {
        this.title = title;
        this.thumbmail = thumbmail;
    }

    public Movies() {
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

    public int getThumbmail() {
        return thumbmail;
    }

    public void setThumbmail(int thumbmail) {
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
    public int getConverphoto() {
        return converphoto;
    }

    public void setConverphoto(int converphoto) {
        this.converphoto = converphoto;
    }
}
