package chuong.example.movieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.app.SearchManager;
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
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import chuong.example.movieapp.ListIDMovies.IDMovies;
import chuong.example.movieapp.Movies_cu.MovieAdapter;
import chuong.example.movieapp.Movies_cu.MovieCategoryAdapter;
import chuong.example.movieapp.Movies_cu.MovieItemClickListener;
import chuong.example.movieapp.Movies_cu.Movies;
import chuong.example.movieapp.R;
import chuong.example.movieapp.SlideMovies.SlideMovie;
import chuong.example.movieapp.SlideMovies.SlideMovieAdapter;
import chuong.example.movieapp.Slider_Cu.Slide;
import chuong.example.movieapp.Slider_Cu.SliderPagerAdapter;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {
    private List<Slide> lstSlides;
    private ArrayList<SlideMovie> lstSlideMovies;
    private ViewPager sliderPager;
    private TabLayout indicator;
    private RecyclerView MoviesRV,MoviesRVCategory;
    private TabItem tblTv,tblMovie,tblAnime,tblVideo;
    SlideMovieAdapter adapterSlideMovies;
    List<Movies> lstMovies,listMoviesCategory ;
    MovieAdapter movieAdapter;
    MovieCategoryAdapter movieAdapterCategory;
    SearchView searchView;
    IDMovies idMovies = new IDMovies();



    public static String Api_key="AIzaSyB7hA1R7YLrHQERL1J2IAtCbWijLvovrQA";
    public static String Api_Web="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=";
    //&maxResults=50
    String get_Json_url=Api_Web+idMovies.getID_playlist()+"&key="+Api_key+"&maxResults=50";
    String get_new=Api_Web+idMovies.getID_NewMovies()+"&key="+Api_key+"&maxResults=50";
    String get_Movies=Api_Web+idMovies.getID_Movies()+"&key="+Api_key+"&maxResults=50";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa
        sliderPager=findViewById(R.id.sldier_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV=findViewById(R.id.Rv_movies);
        MoviesRVCategory= findViewById(R.id.Rv_TheLoai);
        tblMovie=findViewById(R.id.tblMovie);
        tblTv=findViewById(R.id.tblTV);
        tblAnime=findViewById(R.id.tblAnime);
        tblVideo=findViewById(R.id.tblAnime);


        CreateMoviesViewPager();
        CreateRyclyView();
        CreateRyclyViewCayegory(get_Movies);


        
    }
    private void getJsonSlider(String Url){
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

    private void getJsonMovies(String Url,List<Movies> lstMovies,MovieAdapter movieAdapter){
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
                                lstMovies.add(new Movies(title,description,url,url,idvieo));
                                //arrayListMovies.add(new MovieYoutube(title,url,idvieo));
                            }
                            movieAdapter.notifyDataSetChanged();

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
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onMoviesClick(Movies movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        //set movie infomat to the
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbmail());
        intent.putExtra("imgCover",movie.getConverphoto());
        intent.putExtra("decription",movie.getDecription());
        intent.putExtra("streaminglink",movie.getStreaminglink());
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

    private  void CreateRyclyView() {
        //tao trang hien thi danh sach phim

        lstMovies = new ArrayList<>();

        movieAdapter = new MovieAdapter(this,lstMovies,this,R.layout.item_movie);
        getJsonMovies(get_Movies,lstMovies,movieAdapter);
        MoviesRV.setAdapter(movieAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }
    private  void CreateMoviesViewPager()
    {
        //tao danh sach slide Movies
        lstSlideMovies=new ArrayList<>();
        //gan vao adapter
        adapterSlideMovies=new SlideMovieAdapter(this,lstSlideMovies);
        //them vao Json
        getJsonSlider(get_new);
        //gan vao slide Pager
        sliderPager.setAdapter(adapterSlideMovies);
        //setup time
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderPager,true);
    }

    private  void CreateRyclyViewCayegory(String Url ) {
        //tao trang hien thi danh sach phim

        listMoviesCategory = new ArrayList<>();

        movieAdapterCategory = new MovieCategoryAdapter(this,listMoviesCategory,this,R.layout.item_moviecategory);
        getJsonMoviesCategory(Url,listMoviesCategory,movieAdapterCategory);
        MoviesRVCategory.setAdapter(movieAdapterCategory);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        MoviesRVCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    private void getJsonMoviesCategory(String Url,List<Movies> lstMovies,MovieCategoryAdapter movieAdapter){
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
                                lstMovies.add(new Movies(title,description,url,url,idvieo));
                                //arrayListMovies.add(new MovieYoutube(title,url,idvieo));
                            }
                            movieAdapter.notifyDataSetChanged();

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


}