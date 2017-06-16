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

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;


    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            // Get a reference to the LoaderManager, in order to interact with loaders.
           final LoaderManager loaderManager = getLoaderManager();

        //Declaring a list view and attaching an adapter to it
        final ListView bookListView = (ListView) findViewById(R.id.list_view);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        // Hide loading indicator because the data has been loaded
        final View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        adapter = new BookAdapter(this, 0, new ArrayList<Book>());

        bookListView.setAdapter(adapter);


        final EditText searchTextView = (EditText) findViewById(R.id.search);


        Button searchButton =(Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchTextView.getText().toString();
                QUERY_URL = QUERY_URL+searchText;

                loadingIndicator.setVisibility(View.VISIBLE);

                if(isInternetConnectionAvailable()){
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                }else{
                    mEmptyStateTextView.setText(R.string.no_internet);
                }

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

    private boolean isInternetConnectionAvailable(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


}


