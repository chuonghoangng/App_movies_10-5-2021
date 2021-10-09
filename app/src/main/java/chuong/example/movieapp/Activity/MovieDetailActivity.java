package chuong.example.movieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import chuong.example.movieapp.R;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        //get the data
        inViews();


    }
    void inViews(){
        play_fab=findViewById(R.id.play_fab);
        //get the data
        String movieTitle = getIntent().getExtras().getString("title");
        String imageResourceId=getIntent().getExtras().getString("imgURL");
        String imagecover=getIntent().getExtras().getString("imgCover");
        String decription=getIntent().getExtras().getString("decription");
        String streaminglink=getIntent().getExtras().getString("streaminglink");
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
}