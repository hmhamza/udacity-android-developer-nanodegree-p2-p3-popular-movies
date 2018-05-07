package com.example.android.popularmovies2.data;

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HaZa on 11-Apr-18.
 */

public final class Contract_FavoriteMovies {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Contract_FavoriteMovies() {}

    /* Inner class that defines the table contents */
    public static class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTERPATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_USERRATING = "user_rating";
        public static final String COLUMN_RELEASEYEAR = "release_year";
    }
}

