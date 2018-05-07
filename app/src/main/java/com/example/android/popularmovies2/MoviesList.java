package com.example.android.popularmovies2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HaZa on 09-Apr-18.
 */

public class MoviesList{

    @SerializedName("results")
    List<Movie> movies=null;

    public List<Movie> getMovies() {
        return movies;
    }

    public void updateList(List<Movie> movies){
        this.movies=movies;
    }

    public int getNoOfMovies() {
        return movies.size();
    }

    public Movie getSingleMovie(int i) {
        return movies.get(i);
    }

    public void clear(){
            movies.clear();
    }
}