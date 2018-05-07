package com.example.android.popularmovies2;

/**
 * Created by HaZa on 11-Apr-18.
 */

public class Trailer {

    String name;
    String key;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
