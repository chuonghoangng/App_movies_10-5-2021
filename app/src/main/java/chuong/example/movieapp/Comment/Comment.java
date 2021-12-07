package chuong.example.movieapp.Comment;

import java.io.Serializable;

public class Comment implements Serializable {
    private String ten;
    private String binhluan;
    private String MovieId;

    public Comment() {
    }

    public Comment(String ten, String binhluan, String movieId) {
        this.ten = ten;
        this.binhluan = binhluan;
        MovieId = movieId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public String getMovieId() {
        return MovieId;
    }

    public void setMovieId(String movieId) {
        MovieId = movieId;
    }
}
