package chuong.example.movieapp.Episode;

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

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>{

    Context context;
    List<Episode> mDataEpisode;
    int layout;
    EpisodeItemClickListener episodeItemClickListener;
    public EpisodeAdapter(Context context, List<Episode> mDataEpisode, int layout) {
        this.context = context;
        this.mDataEpisode = mDataEpisode;
        this.layout = layout;
    }

    public EpisodeAdapter(Context context, List<Episode> mDataEpisode, int layout, EpisodeItemClickListener episodeItemClickListener) {
        this.context = context;
        this.mDataEpisode = mDataEpisode;
        this.layout = layout;
        this.episodeItemClickListener = episodeItemClickListener;
    }

    public EpisodeAdapter() {


    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.from(context).inflate(layout,parent,false);
        //View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);

        //return null;
        return new EpisodeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.txtsotap.setText("Tap " + mDataEpisode.get(position).getEpisodeNumber());
    }

    @Override
    public int getItemCount() {
        return mDataEpisode.size();
    }

    public class EpisodeViewHolder extends  RecyclerView.ViewHolder{
        TextView txtsotap;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtsotap=itemView.findViewById(R.id.txtsotap);
            txtsotap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    episodeItemClickListener.onEpisodesClick(mDataEpisode.get(getAdapterPosition()),txtsotap);
                }
            });
        }
    }
}
