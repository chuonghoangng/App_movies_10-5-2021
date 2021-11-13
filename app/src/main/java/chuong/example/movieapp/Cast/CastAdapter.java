package chuong.example.movieapp.Cast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import chuong.example.movieapp.Movies_cu.MovieCategoryAdapter;
import chuong.example.movieapp.Movies_cu.Movies;
import chuong.example.movieapp.R;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder>{

    Context context;
    List<Cast> mDataCast;
    int layout;

    public CastAdapter(Context context, List<Cast> mDataCast,int layout) {
        this.context = context;
        this.mDataCast = mDataCast;
        this.layout=layout;
    }

    public CastAdapter() {
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.from(context).inflate(layout,parent,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);

        //return null;
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Glide.with(context).load(mDataCast.get(position).getUrl_image()).into(holder.img);
        //Picasso.get().load(mDataCast.get(position).getUrl_image()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mDataCast.size();
    }

    public class CastViewHolder extends  RecyclerView.ViewHolder{
        ImageView img;
        public CastViewHolder(@NonNull View itemView) {

            super(itemView);

            img=itemView.findViewById(R.id.image_cast);
        }
    }
}
