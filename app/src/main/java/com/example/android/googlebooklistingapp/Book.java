package com.example.android.googlebooklistingapp;

/**
 * Created by Sudha on 13-Jun-17.
 */

public class Book {

    //Global member variables declaration
    private String mTitle;
    private String mAuthor;
   /* private String mCurrency;
    private String mPrice;
*/
    //Constructor declaration
    Book (String title, String author){
        mTitle = title;
        mAuthor = author;
        /*mCurrency = currency;
        mPrice = price;*/
    }

    //Getter methods declaration

    public String getTitle(){ return mTitle; }

    public String getAuthor(){ return mAuthor; }

   /* public String getCurrency(){ return mCurrency; }

    public String getPrice(){ return mPrice;}*/
}
