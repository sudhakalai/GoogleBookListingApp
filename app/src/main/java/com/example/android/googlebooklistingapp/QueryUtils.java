package com.example.android.googlebooklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.android.googlebooklistingapp.MainActivity.LOG_TAG;

/**
 * Created by Sudha on 13-Jun-17.
 */

public class QueryUtils {

    //Declaring constructor
    public QueryUtils(){}

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.v(LOG_TAG, "makeHttpRequest: Response code shows error"+responseCode);
            }

        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "makeHttpRequest: IOExpection occurred ",e );


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Extracts the list of books
     */
    public static ArrayList<Book> fetchBooks (String requestUrl){

       URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        ArrayList<Book> books = extractBooks(jsonResponse);

        return books;
    }

    /**
     * Parses the json response
     */

    private static ArrayList<Book> extractBooks(String bookJSON){

        ArrayList<Book> books = new ArrayList<>();

        if(TextUtils.isEmpty(bookJSON)){
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(bookJSON);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for(int i=0; i<itemsArray.length(); i++){
                JSONObject currentBook = itemsArray.getJSONObject(i);

                JSONObject volumeInfoObject = currentBook.getJSONObject("volumeInfo");
                String title = volumeInfoObject.getString("title");

                String author= "Author: ";
                if(volumeInfoObject.has("authors")){
                    JSONArray authors = volumeInfoObject.getJSONArray("authors");
                    for(int j=0; j<authors.length(); j++){
                        author = author+authors.getString(j) +",";
                    }

                    author = author.substring(0, author.length()-1);
                }else{
                    author = author + "No author found";
                }

                Book book = new Book(title,author);
                books.add(book);

            }


        }catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        return books;
    }


}
