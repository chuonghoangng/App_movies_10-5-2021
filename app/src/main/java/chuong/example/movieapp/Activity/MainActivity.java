package chuong.example.movieapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lib.InterfaceRepository.Methods;
import com.example.lib.Model.MovieModel;
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
import chuong.example.movieapp.Login;
import chuong.example.movieapp.Movies_cu.MovieAdapter;
import chuong.example.movieapp.Movies_cu.MovieCategoryAdapter;
import chuong.example.movieapp.Movies_cu.MovieItemClickListener;
import chuong.example.movieapp.Movies_cu.Movies;
import chuong.example.movieapp.R;
import chuong.example.movieapp.SlideMovies.SlideMovie;
import chuong.example.movieapp.SlideMovies.SlideMovieAdapter;

import retrofit2.Call;
import retrofit2.Callback;


import static com.example.lib.RetrofitClient.getRetrofit;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity implements MovieItemClickListener, View.OnClickListener {



    private ViewPager sliderPager;
    private TabLayout indicator;
    private RecyclerView MoviesRV,MoviesRVCategory;

    private Button btnAnime,btnChina,btnMy,btnVN,btnIndia;
    private TextView txtcategory,txtCast;
    SlideMovieAdapter adapterSlideMovies;
    List<Movies> lstMovies,listMoviesCategory,lstSlidesmovie ;
    MovieAdapter movieAdapter;
    MovieCategoryAdapter movieAdapterCategory;
    SearchView searchView;

    public static String Api_key="AIzaSyB7hA1R7YLrHQERL1J2IAtCbWijLvovrQA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa
        anhxa();


        //hien thi danh sach viewpager
        CreateMoviesViewPager();
        //hien danh sach phim
        CreateRyclyView();
        //hien danh sach phim theo the loai
        CreateRyclyViewCayegory("anime");



        
    }
    private void anhxa()
    {
        sliderPager=findViewById(R.id.sldier_pager);
        indicator = findViewById(R.id.indicator);
        MoviesRV=findViewById(R.id.Rv_movies);
        MoviesRVCategory= findViewById(R.id.Rv_TheLoai);

        btnAnime=findViewById(R.id.btnAnime);
        btnChina=findViewById(R.id.btnChina);
        btnMy=findViewById(R.id.btnUSA);
        btnVN=findViewById(R.id.btnVN);
        btnIndia=findViewById(R.id.btnIndia);

        btnAnime.setOnClickListener(this);
        btnChina.setOnClickListener(this);
        btnMy.setOnClickListener(this);
        btnIndia.setOnClickListener(this);
        btnVN.setOnClickListener(this);

        txtcategory=findViewById(R.id.txtCategory);

        txtCast=findViewById(R.id.txtCast);







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
        intent.putExtra("id",movie.getId());
        intent.putExtra("title",movie.getTitle());
        intent.putExtra("imgURL",movie.getThumbmail());
        intent.putExtra("imgCover",movie.getConverphoto());
        intent.putExtra("decription",movie.getDecription());
        intent.putExtra("streaminglink",movie.getStreaminglink());
        intent.putExtra("category",movie.getCategory());
        //let crezate the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                movieImageView,"sharedName");
        startActivity(intent,options.toBundle());
        //Toast.makeText(this,"item click :" + movie.getTitle(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {


            case R.id.btnChina:
            {
                txtcategory.setText("CHINA");
                CreateRyclyViewCayegory("new");
            }
            break;

            case R.id.btnAnime:
            {
                txtcategory.setText("ANIME");
                CreateRyclyViewCayegory("anime");
            }
            break;
            case R.id.btnUSA:
            {
                txtcategory.setText("AMERICA");
                CreateRyclyViewCayegory("USA");
            }
            break;


            case R.id.btnIndia:
            {
                txtcategory.setText("INDIA");
                CreateRyclyViewCayegory("India");
            }
            break;
            case R.id.btnVN:
            {
                txtcategory.setText("VIET NAM");
                CreateRyclyViewCayegory("VN");
            }
            break;

        }
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();


    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(sliderPager.getCurrentItem()<lstSlidesmovie.size()-1)
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
        //getJsonMovies(get_Movies,lstMovies,movieAdapter);
        getMovies(lstMovies,movieAdapter);
        MoviesRV.setAdapter(movieAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL);
        MoviesRV.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }
    private  void CreateMoviesViewPager()
    {
        //tao danh sach slide Movies
        //lstSlideMovies=new ArrayList<>();
        lstSlidesmovie=new ArrayList<>();
        //gan vao adapter
        adapterSlideMovies=new SlideMovieAdapter(this,lstSlidesmovie);
        //them vao Json
        //getJsonSlider(get_new);
        getMovies(4);
        //gan vao slide Pager
        sliderPager.setAdapter(adapterSlideMovies);
        //setup time
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.SliderTimer(),4000,6000);
        indicator.setupWithViewPager(sliderPager,true);
    }

    private  void CreateRyclyViewCayegory(String theloai) {
        //tao trang hien thi danh sach phim

        listMoviesCategory = new ArrayList<>();

        movieAdapterCategory = new MovieCategoryAdapter(this,listMoviesCategory,this,R.layout.item_moviecategory);
        //getJsonMoviesCategory(Url,listMoviesCategory,movieAdapterCategory);
        getMovies(theloai,listMoviesCategory,movieAdapterCategory);
        MoviesRVCategory.setAdapter(movieAdapterCategory);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        //MoviesRVCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        Configuration orientation = new Configuration();
        if(this.MoviesRVCategory.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            MoviesRVCategory.setLayoutManager(new GridLayoutManager(this, 1));
        } else if (this.MoviesRVCategory.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MoviesRVCategory.setLayoutManager(new GridLayoutManager(this, 2));
        }
        //connectGetApiData();

    }



    public void getMovies(List<Movies> lstMovies,MovieAdapter movieAdapter){
        Methods methods = getRetrofit().create(Methods.class);
        Call<MovieModel> call = methods.getMovies();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, retrofit2.Response<MovieModel> response) {

                MovieModel.Data[] data = response.body().getData();
                for(MovieModel.Data dt: data){

                    //Movies movies = new Movies();
                    String id=dt.getId();
                    String title=dt.getTitle();
                    String description=dt.getDescription();
                    String url=dt.getUrl().toString();
                    String idvieo=dt.getIdvieo();
                    String category=dt.getTheloai();
                    lstMovies.add(new Movies(id,title,description,url,url,idvieo,category));
                    //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                    //textView.append(dt.getId()+dt.getName()+"\n");
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void getMovies(String theloai,List<Movies> lstMovies,MovieCategoryAdapter movieCategoryAdapter){
        Methods methods = getRetrofit().create(Methods.class);
        Call<MovieModel> call = methods.getMovies();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, retrofit2.Response<MovieModel> response) {
                //Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                MovieModel.Data[] data = response.body().getData();
                for(MovieModel.Data dt: data){
                    String category=dt.getTheloai();
                    if(theloai.equals(category)){
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
                movieCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void getMovies(int soluong) {
        Methods methods = getRetrofit().create(Methods.class);
        Call<MovieModel> call = methods.getMovies();
        //lstMovies=new ArrayList<>();
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, retrofit2.Response<MovieModel> response) {

                MovieModel.Data[] data = response.body().getData();
                int i=0;
                for(MovieModel.Data dt: data){
                    if(i<soluong){

                        String id=dt.getId();
                        String title=dt.getTitle();
                        String description=dt.getDescription();
                        String url=dt.getUrl().toString();
                        String idvieo=dt.getIdvieo();
                        String category=dt.getTheloai();
                        lstSlidesmovie.add(new Movies(id,title,description,url,url,idvieo,category));
                        i=i+1;
                    }
                    //Movies movies = new Movies();

                    //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
                    //textView.append(dt.getId()+dt.getName()+"\n");
                }
                adapterSlideMovies.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
            }
        });
    }


}