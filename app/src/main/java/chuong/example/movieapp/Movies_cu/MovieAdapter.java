package chuong.example.movieapp.Movies_cu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import chuong.example.movieapp.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context;
    List<Movies> mData;
    MovieItemClickListener movieItemClickListener;
    int layout;

    public MovieAdapter(Context context, List<Movies> mData,MovieItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        this.movieItemClickListener=listener;
    }

    public MovieAdapter(Context context, List<Movies> mData, MovieItemClickListener movieItemClickListener, int layout) {
        this.context = context;
        this.mData = mData;
        this.movieItemClickListener = movieItemClickListener;
        this.layout = layout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.from(context).inflate(layout,viewGroup,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Movies movies = mData.get(i);
        if(movies==null)
            return;
        myViewHolder.TvTitle.setText(mData.get(i).getTitle());
        //myViewHolder.ImgMovie.setImageResource(mData.get(i).getThumbmail());
        if (mData.get(i).getThumbmail().isEmpty()) {
            myViewHolder.ImgMovie.setImageResource(R.drawable.anh1);
        } else{
            Picasso.get().load(mData.get(i).getThumbmail()).into(myViewHolder.ImgMovie);
        }

    }

    @Override
    public int getItemCount() {
        if(mData!=null){
            return mData.size();
        }
        return 0;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TvTitle;
        private ImageView ImgMovie;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.item_movie_title);
            ImgMovie=itemView.findViewById(R.id.item_movie_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClickListener.onMoviesClick(mData.get(getAdapterPosition()),ImgMovie);
                }
            });

        }
    }
}
