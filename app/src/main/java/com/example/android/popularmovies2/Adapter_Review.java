package com.example.android.popularmovies2;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HaZa on 12-Apr-18.
 */

public class Adapter_Review extends RecyclerView.Adapter<Adapter_Review.MyViewHolder> {

    private ReviewsList reviewsList=null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_lvrow_tv)
        TextView author_tv;

        @BindView(R.id.content_lvrow_tv)
        TextView content_tv;

        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


    public Adapter_Review(ReviewsList reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review review = reviewsList.getSingleReview(position);

        String authorText = "by <font color='#00cc00'>"+ review.getAuthor()+"</font>";
        holder.author_tv.setText(Html.fromHtml(authorText), TextView.BufferType.SPANNABLE);
        
        holder.content_tv.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if(reviewsList==null)
            return 0;
        return reviewsList.getNoOfReviews();
    }
}
