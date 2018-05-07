package com.example.android.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies2.data.Contract_FavoriteMovies.MovieEntry;

/**
 * Created by HaZa on 11-Apr-18.
 */

public class DbHelper_FavoriteMovies extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "favorite_movies_db";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;


    public DbHelper_FavoriteMovies(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                        MovieEntry.COLUMN_ID + " TEXT," +
                        MovieEntry.COLUMN_TITLE + " TEXT," +
                        MovieEntry.COLUMN_POSTERPATH + " TEXT," +
                        MovieEntry.COLUMN_OVERVIEW + " TEXT," +
                        MovieEntry.COLUMN_USERRATING + " TEXT," +
                        MovieEntry.COLUMN_RELEASEYEAR + " TEXT)";


        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;

        db.execSQL(SQL_DELETE_ENTRIES);

        // Create tables again
        onCreate(db);
    }
}
