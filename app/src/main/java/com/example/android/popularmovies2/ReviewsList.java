package com.example.android.popularmovies2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HaZa on 11-Apr-18.
 */

public class ReviewsList {

    @SerializedName("results")
    List<Review> reviews=null;

    public List<Review> getReviews() {
        return reviews;
    }

    public int getNoOfReviews() {
        if(reviews==null)
            return 0;
        return reviews.size();
    }

    public Review getSingleReview(int i) {
        return reviews.get(i);
    }
}
