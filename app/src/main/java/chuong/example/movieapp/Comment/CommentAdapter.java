package chuong.example.movieapp.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import chuong.example.movieapp.Cast.Cast;
import chuong.example.movieapp.Cast.CastAdapter;
import chuong.example.movieapp.R;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    Context context;
    List<Comment> mdatacomment;
    int layout;

    public CommentAdapter(Context context, List<Comment> mdatacomment, int layout) {
        this.context = context;
        this.mdatacomment = mdatacomment;
        this.layout = layout;
    }

    public CommentAdapter() {
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.from(context).inflate(layout,parent,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);

        //return null;
        return new CommentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        holder.txtname.setText("" + mdatacomment.get(position).getTen());
        holder.txtcomment.setText("" + mdatacomment.get(position).getBinhluan());
    }

    @Override
    public int getItemCount() {
        return mdatacomment.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtcomment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.txtname);
            txtcomment=itemView.findViewById(R.id.txtcomment);
        }
    }
}
