package com.example.android.popularmovies2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HaZa on 12-Apr-18.
 */

public class Adapter_Trailer extends RecyclerView.Adapter<Adapter_Trailer.MyViewHolder>{

    private TrailersList trailersList=null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.playBtn_lvrow_iv)
        ImageView playBtn_iv;

        @BindView(R.id.title_lvrow_tv)
        TextView title_tv;

        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


    public Adapter_Trailer(TrailersList trailersList) {
        this.trailersList = trailersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailers_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Trailer trailer = trailersList.getSingleTrailer(position);
        holder.playBtn_iv.setImageResource(R.mipmap.ic_play_circle_filled_black_24dp);
        holder.title_tv.setText(trailer.getName());

        holder.playBtn_iv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailer.getKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(trailersList==null)
            return 0;
        return trailersList.getNoOfTrailers();
    }
}
