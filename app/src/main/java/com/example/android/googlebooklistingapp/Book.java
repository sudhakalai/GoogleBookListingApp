package com.example.android.googlebooklistingapp;

/**
 * Created by Sudha on 13-Jun-17.
 */

public class Book {

    //Global member variables declaration
    private String mTitle;
    private String mAuthor;

    //Constructor declaration
    Book (String title, String author){
        mTitle = title;
        mAuthor = author;
    }

    //Getter methods declaration

    public String getTitle(){ return mTitle; }

    public String getAuthor(){ return mAuthor; }

}
