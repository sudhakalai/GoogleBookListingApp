package com.example.android.googlebooklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sudha on 13-Jun-17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    //Constructor declaration
    public BookAdapter (Context context, int resource, ArrayList<Book> books){
        super(context, 0, books);
    }

    //Overriding getView method
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }

        //Getting item position
        Book currentBook = getItem(position);

        //Updating title text View
        TextView titleTextView = (TextView) listView.findViewById(R.id.title_text);
        titleTextView.setText(currentBook.getTitle());

        //Updating author text View
        TextView authorTextView = (TextView) listView.findViewById(R.id.author_text);
        authorTextView.setText(currentBook.getAuthor());


        return listView;
    }
}
