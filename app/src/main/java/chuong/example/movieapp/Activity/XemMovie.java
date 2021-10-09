package chuong.example.movieapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import chuong.example.movieapp.R;

public class XemMovie extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener  {

    YouTubePlayerView youTubePlayerView;
    String idMovie = "";
    int REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_movie);
        youTubePlayerView = findViewById(R.id.PlayMovieView);

        idMovie = getIntent().getExtras().getString("streaminglink");
        youTubePlayerView.initialize(MainActivity.Api_key, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(idMovie);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(XemMovie.this,REQUEST);
        }else
        {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST)
        {
            youTubePlayerView.initialize(MainActivity.Api_key,XemMovie.this);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}

