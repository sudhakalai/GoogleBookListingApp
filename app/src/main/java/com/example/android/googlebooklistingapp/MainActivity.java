package com.example.android.googlebooklistingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Book>>{

    //Global variables

    public static final String LOG_TAG = MainActivity.class.getName();

    private static String QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private TextView mEmptyStateTextView; //empty textView initialization

    private static final int BOOK_LOADER_ID = 1; //assigning a loader id value


    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoaderManager loaderManager = getLoaderManager(); //initializing load manager

       /**creating a listView object and attaching an adapter to it.
        * Setting empty view when there no items to display
        * removing progress bar before search is performed
        */
        final ListView bookListView = (ListView) findViewById(R.id.list_view);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        final View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        adapter = new BookAdapter(this, 0, new ArrayList<Book>());

        bookListView.setAdapter(adapter);

        /**
         * setting an onClickListener on the search button
         * appending the searched term to the default url
         * initiating the loader
         */
        final EditText searchTextView = (EditText) findViewById(R.id.search);

        Button searchButton =(Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                loadingIndicator.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setText("");
                String searchText = searchTextView.getText().toString();
                QUERY_URL = QUERY_URL+searchText;

                if(isInternetConnectionAvailable()){
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }else{
                    adapter.clear();
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyStateTextView.setText(R.string.no_internet);
                }

                //reset the values
                searchTextView.setText("");
                QUERY_URL ="https://www.googleapis.com/books/v1/volumes?q=";
            }
        });

    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, Bundle bundle) {

        // Create a new loader for the given URL
        return new BookLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.book_not_found);

        // Clear the adapter of previous books data
        adapter.clear();

        // If there is a valid list of {@link books}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }

    /**
     * checks if there is internet connection or not
     * @return a boolean value true or false
     */
    private boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}


