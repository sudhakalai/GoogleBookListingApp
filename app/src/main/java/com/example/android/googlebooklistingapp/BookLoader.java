package com.example.android.googlebooklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Sudha on 14-Jun-17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    /** Query URL */
    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public ArrayList<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        ArrayList<Book> books = QueryUtils.fetchBooks(mUrl);
        return books;
    }
}
