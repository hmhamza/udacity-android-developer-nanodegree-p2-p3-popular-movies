package com.example.android.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HaZa on 07-Apr-18.
 */

public class Movie implements Parcelable {

    String id;
    String original_title;
    String poster_path;
    String overview;
    @SerializedName("vote_average")
    String user_rating;
    String release_date;

    public Movie(String id,String results, String poster_path, String overview, String user_rating, String release_date) {
        this.id = id;
        this.original_title = results;
        this.poster_path = poster_path;
        this.overview = overview;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }

    public String getTitle() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setTitle(String title) {
        this.original_title = title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movie(Parcel in) {
        id=in.readString();
        original_title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        user_rating = in.readString();
        release_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(user_rating);
        dest.writeString(release_date);
    }


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public int describeContents() {
        return 0;
    }
}
