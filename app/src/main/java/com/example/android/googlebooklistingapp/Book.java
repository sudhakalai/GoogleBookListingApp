package com.example.android.googlebooklistingapp;

import android.graphics.Bitmap;

/**
 * Created by Sudha on 13-Jun-17.
 */

public class Book {

    //Global member variables declaration
    private String mTitle;
    private String mAuthor;
    private Bitmap mImgBmp;

    //Constructor declaration
    Book (String title, String author, Bitmap imgBmp){
        mTitle = title;
        mAuthor = author;
        mImgBmp = imgBmp;
    }

    //Getter methods declaration

    public String getTitle(){ return mTitle; }

    public String getAuthor(){ return mAuthor; }

    public Bitmap getImgBmp(){return mImgBmp; }

}
