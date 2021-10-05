package chuong.example.movieapp.SlideMovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import chuong.example.movieapp.R;
import chuong.example.movieapp.Slide;

public class SlideMovieAdapter extends PagerAdapter {
    Context mContext;
    List<SlideMovie> mList;

    public SlideMovieAdapter(Context mContext, List<SlideMovie> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public SlideMovieAdapter() {
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View slideLayout =inflater.inflate(R.layout.slide_item,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);

        //slideImg.setImageResource(mList.get(position).getImage());
        slideText.setText(mList.get(position).getTitle());
        Picasso.get().load(mList.get(position).getImage()).into(slideImg);

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
