package chuong.example.movieapp.Activity;

import static com.example.lib.RetrofitClient.getRetrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lib.InterfaceRepository.Methods;
import com.example.lib.Model.CastModel;
import com.example.lib.Model.CommentInsert;
import com.example.lib.Model.CommentModel;
import com.example.lib.Model.CommentResponse;
import com.example.lib.Model.EpisodeModel;
import com.example.lib.Model.MovieModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import chuong.example.movieapp.Cast.Cast;
import chuong.example.movieapp.Cast.CastAdapter;
import chuong.example.movieapp.Comment.Comment;
import chuong.example.movieapp.Comment.CommentAdapter;
import chuong.example.movieapp.Episode.Episode;
import chuong.example.movieapp.Episode.EpisodeAdapter;
import chuong.example.movieapp.Episode.EpisodeItemClickListener;
import chuong.example.movieapp.Movies_cu.MovieAdapter;
import chuong.example.movieapp.Movies_cu.MovieItemClickListener;
import chuong.example.movieapp.Movies_cu.Movies;
import chuong.example.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity implements MovieItemClickListener, EpisodeItemClickListener {

    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;
    //RV CAST
    private RecyclerView CastRV,MoviesAsRv,DanhsachtapphimRV,CommentRV;
    private List<Cast> castList;
    private CastAdapter castAdapter;
    //RV danh sach tap phim
    private List<Episode> episodeList;
    private EpisodeAdapter episodeAdapter;
    private List<Movies> lstMoviesAs;
    //RV danh sach binh luan
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private EditText edtTen,edtNoidung;
    private Button btnaddcomment;


    MovieAdapter movieasAdapter;
    // Nhan du lieu tu trang Home
    String link ;
    String id;
    String movieTitle;
    String streaminglink;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        //get the data
        inViews();
        setupRvCast();
        SetupRVMoviesAs();
        setupRvEpisode();
        setupRvComment();



    }
    void inViews(){
        CastRV=findViewById(R.id.Rv_Cast);
        MoviesAsRv=findViewById(R.id.Rv_MovisAs);
        play_fab=findViewById(R.id.play_fab);
        DanhsachtapphimRV = findViewById(R.id.danhsachtap);
        CommentRV=findViewById(R.id.Rv_Comment);
        //get the data
        id = getIntent().getExtras().getString("id");
        movieTitle = getIntent().getExtras().getString("title");
        String imageResourceId=getIntent().getExtras().getString("imgURL");
        String imagecover=getIntent().getExtras().getString("imgCover");
        String decription=getIntent().getExtras().getString("decription");
        category=getIntent().getExtras().getString("category");
        streaminglink=getIntent().getExtras().getString("streaminglink");
        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        Glide.with(this).load(imageResourceId).into(MovieThumbnailImg);

        //MovieThumbnailImg.setImageResource(imageResourceId);

        MovieCoverImg=findViewById(R.id.detail_movie_cover);
        Glide.with(this).load(imagecover).into(MovieCoverImg);
        tv_title=findViewById(R.id.detail_movie_title);
        tv_description=findViewById(R.id.detail_movie_desc);
        tv_title.setText(movieTitle);
        tv_description.setText(decription);
        getSupportActionBar().setTitle(movieTitle);
        //set up animetion
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        //comment
        edtTen=findViewById(R.id.edtName);
        edtNoidung=findViewById(R.id.edtComment);
        btnaddcomment=findViewById(R.id.btnAddComment);
        btnaddcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods methods = getRetrofit().create(Methods.class);
                CommentInsert comment= new CommentInsert();
                comment.setTen(edtTen.getText().toString());
                comment.setBinhluan(edtNoidung.getText().toString());
                comment.setMovieId(id);



                Call<CommentResponse> call = methods.insertComment(comment);
                commentAdapter.notifyDataSetChanged();
                CommentRV.setAdapter(commentAdapter);
                call.enqueue(new Callback<CommentResponse>() {
                    @Override
                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                        setupRvComment();
                        edtTen.setText("");
                        edtNoidung.setText("");
                        Toast.makeText(MovieDetailActivity.this, "Comment Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                        Toast.makeText(MovieDetailActivity.this, "Comment Error", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        MovieCoverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //truyen id movie sang trang phat video
                Intent intent = new Intent(MovieDetailActivity.this, XemMovie.class);
                intent.putExtra("streaminglink",streaminglink);
                startActivity(intent);
            }
        });




    }
    //tao ham download movie
    public void download(View view) {
        link = "https://www.youtube.com/watch?v="+streaminglink;
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    int itag = 22;
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
                    request.setTitle(movieTitle);
                    request.setDescription("Downloading");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setVisibleInDownloadsUi(false);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"downloads");
                    downloadmanager.enqueue(request);
                }
            }
        }.extract(link, true, true);
    }

    public void watchMovie(View view) {
        //truyen id movie sang trang phat video
        Intent intent = new Intent(MovieDetailActivity.this, XemMovie.class);
        intent.putExtra("streaminglink",streaminglink);
        startActivity(intent);
    }
    // cai dat danh sach dan dien vien
    public void setupRvCast(){
        //tao trang hien thi danh sach phim
        castList = new ArrayList<>();
        castAdapter = new CastAdapter(this,castList,R.layout.item_cast);
        //getJsonMovies(get_Movies,lstMovies,movieAdapter);
        getCasts(castList,castAdapter);
        CastRV.setAdapter(castAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
        CastRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    public void setupRvEpisode(){
        //tao trang hien thi danh sach phim
        episodeList = new ArrayList<>();
        episodeAdapter = new EpisodeAdapter(this,episodeList,R.layout.item_episode,this);
        //getJsonMovies(get_Movies,lstMovies,movieAdapter);
        getEpisodes(episodeList,episodeAdapter);
        DanhsachtapphimRV.setAdapter(episodeAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
        DanhsachtapphimRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private void getEpisodes(List<Episode> castList, EpisodeAdapter castAdapter) {
        Methods methods = getRetrofit().create(Methods.class);
        Call<EpisodeModel> call = methods.getEpisodes();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<EpisodeModel>() {
            @Override
            public void onResponse(Call<EpisodeModel> call, Response<EpisodeModel> response) {
                EpisodeModel.Data[] data = response.body().getData();
                for(EpisodeModel.Data dt: data){
                    String id2=dt.getMovieId();
                    if(id.equals(id2)){
                        String idepisode=dt.getId();
                        String sotap=dt.getEpisodeNumber().toString();

                        String url=dt.getUrl().toString();

                        episodeList.add(new Episode(id2,idepisode,sotap,url));
                        //Toast.makeText(MovieDetailActivity.this, url, Toast.LENGTH_SHORT).show();
                    }

                    //Movies movies = new Movies();

                    //textView.append(dt.getId()+dt.getName()+"\n");
                }
                episodeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<EpisodeModel> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "ERROR DANH SACH TAP PHIM", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getCasts(List<Cast> castList, CastAdapter castAdapter) {
        /*
        castList.add(new Cast("123","https://file1.dangcongsan.vn/DATA/0/2018/10/68___gi%E1%BA%BFng_l%C3%A0ng_qu%E1%BA%A3ng_ph%C3%BA_c%E1%BA%A7u__%E1%BB%A9ng_h%C3%B2a___%E1%BA%A3nh_vi%E1%BA%BFt_m%E1%BA%A1nh-16_51_07_908.jpg","phong canh"));
        castList.add(new Cast("456","https://media-cdn.laodong.vn/Storage/NewsPortal/2021/3/1/884510/Viet-Anh.jpg","phong canh"));
        castList.add(new Cast("123","https://file1.dangcongsan.vn/DATA/0/2018/10/68___gi%E1%BA%BFng_l%C3%A0ng_qu%E1%BA%A3ng_ph%C3%BA_c%E1%BA%A7u__%E1%BB%A9ng_h%C3%B2a___%E1%BA%A3nh_vi%E1%BA%BFt_m%E1%BA%A1nh-16_51_07_908.jpg","phong canh"));
        castList.add(new Cast("456","https://media-cdn.laodong.vn/Storage/NewsPortal/2021/3/1/884510/Viet-Anh.jpg","phong canh"));
        castList.add(new Cast("123","https://file1.dangcongsan.vn/DATA/0/2018/10/68___gi%E1%BA%BFng_l%C3%A0ng_qu%E1%BA%A3ng_ph%C3%BA_c%E1%BA%A7u__%E1%BB%A9ng_h%C3%B2a___%E1%BA%A3nh_vi%E1%BA%BFt_m%E1%BA%A1nh-16_51_07_908.jpg","phong canh"));
        castList.add(new Cast("456","https://media-cdn.laodong.vn/Storage/NewsPortal/2021/3/1/884510/Viet-Anh.jpg","phong canh"));
        */
        Methods methods = getRetrofit().create(Methods.class);
        Call<CastModel> call = methods.getCasts();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<CastModel>() {
            @Override
            public void onResponse(Call<CastModel> call, Response<CastModel> response) {
                CastModel.Data[] data = response.body().getData();
                for(CastModel.Data dt: data){
                    String id2=dt.getMovieId();
                    if(id.equals(id2)){
                        String idcast=dt.getId();
                        String name=dt.getNameCast();

                        String url=dt.getUrlImage().toString();

                        castList.add(new Cast(idcast,url,name));
                        //Toast.makeText(MovieDetailActivity.this, url, Toast.LENGTH_SHORT).show();
                    }

                    //Movies movies = new Movies();

                    //textView.append(dt.getId()+dt.getName()+"\n");
                }
                castAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CastModel> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

    private  void SetupRVMoviesAs() {
        //tao trang hien thi danh sach phim

        lstMoviesAs = new ArrayList<>();
        movieasAdapter = new MovieAdapter(this,lstMoviesAs,this,R.layout.item_movie);
        //getJsonMovies(get_Movies,lstMovies,movieAdapter);
        getMoviesAs(lstMoviesAs,movieasAdapter);
        MoviesAsRv.setAdapter(movieasAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
        MoviesAsRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onMoviesClick(Movies movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        //set movie infomat to the
        intent.putExtra("id",movie.getId());
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbmail());
        intent.putExtra("imgCover",movie.getConverphoto());
        intent.putExtra("decription",movie.getDecription());
        intent.putExtra("streaminglink",movie.getStreaminglink());
        intent.putExtra("category",movie.getCategory());
        //let crezate the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailActivity.this,
                movieImageView,"sharedName");
        startActivity(intent,options.toBundle());
        //Toast.makeText(this,"item click :" + movie.getTitle(),Toast.LENGTH_LONG).show();
    }
    public void getMoviesAs(List<Movies> lstMovies,MovieAdapter movieAdapter){
        Methods methods = getRetrofit().create(Methods.class);
        Call<MovieModel> call = methods.getMovies();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, retrofit2.Response<MovieModel> response) {

                MovieModel.Data[] data = response.body().getData();

                for(MovieModel.Data dt: data){
                    String theloai=dt.getTheloai();
                    if(category.equals(theloai)){
                        String id=dt.getId();
                        String title=dt.getTitle();
                        String description=dt.getDescription();
                        String url=dt.getUrl().toString();
                        String idvieo=dt.getIdvieo();
                        lstMovies.add(new Movies(id,title,description,url,url,idvieo,category));
                        //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                    }

                    //Movies movies = new Movies();

                    //textView.append(dt.getId()+dt.getName()+"\n");
                }
                movieasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onEpisodesClick(Episode episode, TextView txt) {
        Intent intent = new Intent(MovieDetailActivity.this, XemMovie.class);
        Toast.makeText(MovieDetailActivity.this,episode.getUrl() , Toast.LENGTH_SHORT).show();

        intent.putExtra("streaminglink",episode.getUrl());
        startActivity(intent);

    }

    public void setupRvComment(){
        //tao trang hien thi danh sach phim
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,commentList,R.layout.item_comment);
        //getJsonMovies(get_Movies,lstMovies,movieAdapter);
        getComment(commentList,commentAdapter);
        CommentRV.setAdapter(commentAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        CommentRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    private void getComment(List<Comment> commentList, CommentAdapter commentAdapter) {
        Methods methods = getRetrofit().create(Methods.class);
        Call<CommentModel> call = methods.getComments();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<CommentModel>() {
            @Override
            public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                CommentModel.Data[] data = response.body().getData();
                for(CommentModel.Data dt: data){
                    String id2=dt.getMovieId();
                    if(id.equals(id2)){
                        String ten=dt.getTen();
                        String noidung=dt.getBinhluan().toString();

                        String moviedId=dt.getMovieId().toString();

                        commentList.add(new Comment(ten,noidung,moviedId));
                        //Toast.makeText(MovieDetailActivity.this, url, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MovieDetailActivity.this, "Binh luan thanh cong "+ten+noidung, Toast.LENGTH_LONG).show();
                    }

                    //Movies movies = new Movies();

                    //textView.append(dt.getId()+dt.getName()+"\n");
                }
                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<CommentModel> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this, "ERROR Binh luan ", Toast.LENGTH_LONG).show();
            }
        });

    }
}