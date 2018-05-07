package com.example.android.popularmovies2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HaZa on 07-Apr-18.
 */

public interface TheMovieDbAPI {

    String BASE_URL="http://api.themoviedb.org/3/movie/";
    String BASE_URL_FOR_POSTER="http://image.tmdb.org/t/p/w185/";

    @GET("popular?api_key=<API_KEY>&sort_by=popularity.desc")
    Call<MoviesList> getPopular();

    @GET("top_rated?api_key=<API_KEY>&sort_by=popularity.desc")
    Call<MoviesList> getTopRated();

    @GET("{movieId}/videos?api_key=<API_KEY>")
    Call<TrailersList> getTrailers(@Path("movieId") String movieId);

    @GET("{movieId}/reviews?api_key=<API_KEY>")
    Call<ReviewsList> getReviews(@Path("movieId") String movieId);

}
