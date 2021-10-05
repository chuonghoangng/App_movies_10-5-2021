package chuong.example.movieapp.SlideMovies;

public class SlideMovie {

    private  String Title;
    private String Image;

    public SlideMovie() {
    }

    public SlideMovie(String title, String image) {
        Title = title;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
