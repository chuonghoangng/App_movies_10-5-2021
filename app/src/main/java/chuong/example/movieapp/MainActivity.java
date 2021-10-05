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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import chuong.example.movieapp.SlideMovies.SlideMovie;
import chuong.example.movieapp.SlideMovies.SlideMovieAdapter;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {
    private List<Slide> lstSlides;
    private ArrayList<SlideMovie> lstSlideMovies;
    private ViewPager sliderPager;
    private TabLayout indicator;
    private RecyclerView MoviesRV;
    SlideMovieAdapter adapterSlideMovies;

    public  static String Api_key="AIzaSyB7hA1R7YLrHQERL1J2IAtCbWijLvovrQA";
    String ID_playlist="PLgIqj6EWVN2-Pdn_2VfLCcK5lW4b5NpTq";
    //&maxResults=50
    String get_Json_url="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLgIqj6EWVN2-Pdn_2VfLCcK5lW4b5NpTq&key=AIzaSyB7hA1R7YLrHQERL1J2IAtCbWijLvovrQA&maxResults=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa
        sliderPager=findViewById(R.id.sldier_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV=findViewById(R.id.Rv_movies);

        CreateMoviesViewPager();
        CreateRyclyView();


        
    }
    private void getJson(String Url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonItems =response.getJSONArray("items");
                            String title="";
                            String url="";
                            String idvieo="";
                            String description="";
                            for (int i=0;i<jsonItems.length();i++) {
                                JSONObject jsonItem = jsonItems.getJSONObject(i);
                                JSONObject jsonsnippet = jsonItem.getJSONObject("snippet");
                                title = jsonsnippet.getString("title");

                                JSONObject jsonThumbnails = jsonsnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                                url = jsonMedium.getString("url");
                                JSONObject jsonResouceId = jsonsnippet.getJSONObject("resourceId");
                                idvieo = jsonResouceId.getString("videoId");
                                description = jsonsnippet.getString("description");
                                //Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                                lstSlideMovies.add(new SlideMovie(title,url));
                                //arrayListMovies.add(new MovieYoutube(title,url,idvieo));
                            }
                            adapterSlideMovies.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
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
                    if(sliderPager.getCurrentItem()<lstSlideMovies.size()-1)
                        sliderPager.setCurrentItem(sliderPager.getCurrentItem()+1);
                    else{
                        sliderPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    private  void CreateViewPager(){

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


    }
    private  void CreateRyclyView() {
        //tao trang hien thi danh sach phim

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
    private  void CreateMoviesViewPager()
    {
        //tao danh sach slide Movies

        lstSlideMovies=new ArrayList<>();
        //gan vao adapter
        adapterSlideMovies=new SlideMovieAdapter(this,lstSlideMovies);
        //them vao Json
        getJson(get_Json_url);
        //gan vao slide Pager
        sliderPager.setAdapter(adapterSlideMovies);

        //setup time
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderPager,true);
    }



}