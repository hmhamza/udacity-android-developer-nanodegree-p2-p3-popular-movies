package com.example.android.popularmovies2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HaZa on 11-Apr-18.
 */

public class TrailersList {

    @SerializedName("results")
    List<Trailer> trailers=null;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public int getNoOfTrailers() {
        if(trailers==null)
            return 0;
        return trailers.size();
    }

    public Trailer getSingleTrailer(int i) {
        return trailers.get(i);
    }
}
