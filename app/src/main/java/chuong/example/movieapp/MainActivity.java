package chuong.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {
    private List<Slide> lstSlides;
    private ViewPager sliderPager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderPager=findViewById(R.id.sldier_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV=findViewById(R.id.Rv_movies);

        lstSlides= new ArrayList<>();
        lstSlides.add(new Slide(R.drawable.movies,"The Thor 5"));
        lstSlides.add(new Slide(R.drawable.anhphim2,"Netfix"));
        lstSlides.add(new Slide(R.drawable.pokemon,"pokemon phần 5"));
        lstSlides.add(new Slide(R.drawable.anhphim,"Biệt Đội Cảm Tử"));

        SliderPagerAdapter adapter=new SliderPagerAdapter(this,lstSlides);

        sliderPager.setAdapter(adapter);

        //setup time
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderPager,true);

        List<Movies> lstMovies = new ArrayList<>();
        lstMovies.add(new Movies("The Thor 5",R.drawable.movies,R.drawable.anh1));
        lstMovies.add(new Movies("Netfix",R.drawable.anhphim2,R.drawable.anh2));
        lstMovies.add(new Movies("pokemon phần 5",R.drawable.pokemon,R.drawable.uoc_mo_3));
        lstMovies.add(new Movies("Biệt Đội Cảm Tử",R.drawable.anhphim,R.drawable.anh1));
        lstMovies.add(new Movies("The Thor 5",R.drawable.movies,R.drawable.anh2));
        lstMovies.add(new Movies("Netfix",R.drawable.anhphim2,R.drawable.anh3));
        lstMovies.add(new Movies("pokemon phần 5",R.drawable.pokemon,R.drawable.uoc_mo_3));


        MovieAdapter movieAdapter = new MovieAdapter(this,lstMovies,this);
        MoviesRV.setAdapter(movieAdapter);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public void onMoviesClick(Movies movie, ImageView movieImageView) {
        Intent intent = new Intent(this,MovieDetailActivity.class);
        //set movie infomat to the
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbmail());
        intent.putExtra("imgCover",movie.getConverphoto());
        //let crezate the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                movieImageView,"sharedName");
        startActivity(intent,options.toBundle());
        //Toast.makeText(this,"item click :" + movie.getTitle(),Toast.LENGTH_LONG).show();

    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(sliderPager.getCurrentItem()<lstSlides.size()-1)
                        sliderPager.setCurrentItem(sliderPager.getCurrentItem()+1);
                    else{
                        sliderPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}