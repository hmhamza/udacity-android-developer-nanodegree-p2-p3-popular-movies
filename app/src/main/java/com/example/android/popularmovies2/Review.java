package com.example.android.popularmovies2;

/**
 * Created by HaZa on 11-Apr-18.
 */

public class Review {

    String author;
    String content;

    public Review(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
