package chuong.example.movieapp.Cast;

public class Cast {
    public Cast(String id, String url_image, String name) {
        this.id = id;
        this.url_image = url_image;
        this.name = name;
    }

    public Cast() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String id;
    String url_image;
    String name;


}
