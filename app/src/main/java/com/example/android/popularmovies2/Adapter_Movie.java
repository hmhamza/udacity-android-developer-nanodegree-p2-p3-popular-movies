package com.example.android.popularmovies2;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by HaZa on 06-Apr-18.
 */

public class Adapter_Movie extends BaseAdapter {
    private Context mContext;
    MoviesList moviesList;

    public Adapter_Movie(Context c, MoviesList ml) {
        mContext = c;
        moviesList=ml;
    }

    public void updateData(MoviesList ml) {
        moviesList=ml;
    }

    public int getCount() {
        if(moviesList==null)
            return 0;
        return moviesList.getNoOfMovies();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,800));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(TheMovieDbAPI.BASE_URL_FOR_POSTER+moviesList.getSingleMovie(position).getPoster_path()).into(imageView);
        return imageView;
    }
}