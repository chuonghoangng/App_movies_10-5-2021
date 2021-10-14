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

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryAdapter.MyViewHolderCategory>{


    Context contextcategory;
    List<Movies> mDatacategory;
    MovieItemClickListener movieItemClickListenercategory;
    int layout;

    public MovieCategoryAdapter(Context contextcategory, List<Movies> mDatacategory, MovieItemClickListener movieItemClickListenercategory, int layout) {
        this.contextcategory = contextcategory;
        this.mDatacategory = mDatacategory;
        this.movieItemClickListenercategory = movieItemClickListenercategory;
        this.layout = layout;
    }



    @NonNull
    @Override
    public MyViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) contextcategory.getSystemService(contextcategory.LAYOUT_INFLATER_SERVICE);
        View view=inflater.from(contextcategory).inflate(layout,parent,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
        return new MyViewHolderCategory(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCategory holder, int position) {
        holder.Tvtitle_category.setText(mDatacategory.get(position).getTitle());
        //myViewHolder.ImgMovie.setImageResource(mData.get(i).getThumbmail());
        if (mDatacategory.get(position).getThumbmail().isEmpty()) {
            holder.ImgMovie_category.setImageResource(R.drawable.anh1);
        } else{
            Picasso.get().load(mDatacategory.get(position).getThumbmail()).into(holder.ImgMovie_category);
        }
    }

    @Override
    public int getItemCount() {
        return mDatacategory.size();
    }

    public class MyViewHolderCategory extends RecyclerView.ViewHolder {
        private TextView Tvtitle_category;
        private ImageView ImgMovie_category;
        public MyViewHolderCategory(@NonNull View itemView) {
            super(itemView);

            Tvtitle_category = itemView.findViewById(R.id.item_movie_title_category);
            ImgMovie_category=itemView.findViewById(R.id.item_movie_img_category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    movieItemClickListenercategory.onMoviesClick(mDatacategory.get(getAdapterPosition()),ImgMovie_category);
                }
            });
        }
    }
}
