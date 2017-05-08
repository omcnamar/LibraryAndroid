package com.olegsagenadatrytwo.library;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FetchArray extends AsyncTask<Void, Void, List<Book>> {

    private static final String TAG = "library";
    private Context mContext;
    private ArrayList<Book> myBooks;


    public FetchArray(Context context, ArrayList<Book> myBooks){
        mContext = context;
        this.myBooks = myBooks;
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        Log.i(TAG, "doInBackground");
//        //
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//
//                try{
//
//                    Log.i(TAG, "Fetch: getBooks response try");
//                    //create JSONObject to retrieve response from the server
//                    JSONObject jsonResponse = new JSONObject(response);
//                    //retrieve all the information from json
//                    //success is set to true if retrieving was successful in the server (php)
//                    boolean success         = jsonResponse.getBoolean("success");
//                    String numberofRow      = jsonResponse.getString("numberofRows");
//                    int numberofRows        = Integer.parseInt(numberofRow);
//                    if(success){
//                        Log.i(TAG, "Fetch: getBooks response success");
//                        for(int i = 0; i < numberofRows; i = i+7) {
//
//                            String BookId = jsonResponse.getString(i+"");
//                            String BookISBN = jsonResponse.getString(i+1+"");
//                            String BookTitle= jsonResponse.getString(i+2+"");
//                            String BookAuthor = jsonResponse.getString(i+3+"");
//                            String BookGenre = jsonResponse.getString(i+4+"");
//                            String BookSynopsis = jsonResponse.getString(i+5+"");
//                            String Available    = jsonResponse.getString(i+6+"");
//
//                            Log.i(TAG,"Fetch: " +  BookId);
//                            Log.i(TAG, "Fetch: " + BookISBN);
//                            Log.i(TAG,"Fetch: " + BookTitle);
//                            Log.i(TAG,"Fetch: " + BookAuthor);
//                            Log.i(TAG,"Fetch: " + BookGenre);
//                            Log.i(TAG,"Fetch: " + BookGenre);
//                            Log.i(TAG,"Fetch: " + BookSynopsis);
//                            Log.i(TAG,"Fetch: " + Available);
//
//                            Book book = new Book();
//                            book.setId(UUID.fromString(BookId));
//                            book.setIsbn(BookISBN);
//                            book.setTitle(BookTitle);
//                            book.setAuthor(BookAuthor);
//                            book.setGenre(BookGenre);
//                            book.setSynopsis(BookSynopsis);
//                            book.setAvailable(Integer.parseInt(Available));
//                            myBooks.add(book);
//
//                        }
//
//                    }
//                    else{
//                        Log.i(TAG, "Fetch: getBooks response else");
//                    }
//                }
//                catch (JSONException e){
//                    e.printStackTrace();
//                }
//                catch (Exception e){
//
//                }
//            }
//
//        };
//        Log.i(TAG, "Fetch: getBooks     2");
//        GetBooksRequest getBooksRequest = new GetBooksRequest(responseListener);
//        Log.i(TAG, "Fetch: getBooks     3");
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//        Log.i(TAG, "Fetch: getBooks     4");
//        queue.add(getBooksRequest);
//        Log.i(TAG, "Fetch: getBooks     5");
//        return myBooks;

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("http://www.selfiewars-vote.com/GetBooks.php");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.i(TAG, line);
                try {
                    Log.i(TAG, "Fetch: getBooks response try");
                    //create JSONObject to retrieve response from the server
                    JSONObject jsonResponse = new JSONObject(line);
                    Log.i(TAG, "here");
                    //retrieve all the information from json
                    //success is set to true if retrieving was successful in the server (php)
                    boolean success = jsonResponse.getBoolean("success");
                    String numberofRow = jsonResponse.getString("numberofRows");
                    int numberofRows = Integer.parseInt(numberofRow);
                    myBooks.clear();
                    if (success) {
                        Log.i(TAG, "Fetch: getBooks response success");
                        for (int i = 0; i < numberofRows * 7; i = i + 7) {

                            String BookId = jsonResponse.getString(i + "");
                            String BookISBN = jsonResponse.getString(i + 1 + "");
                            String BookTitle = jsonResponse.getString(i + 2 + "");
                            String BookAuthor = jsonResponse.getString(i + 3 + "");
                            String BookGenre = jsonResponse.getString(i + 4 + "");
                            String BookSynopsis = jsonResponse.getString(i + 5 + "");
                            String Available = jsonResponse.getString(i + 6 + "");

                            Log.i(TAG, "Fetch: " + BookId);
                            Log.i(TAG, "Fetch: " + BookISBN);
                            Log.i(TAG, "Fetch: " + BookTitle);
                            Log.i(TAG, "Fetch: " + BookAuthor);
                            Log.i(TAG, "Fetch: " + BookGenre);
                            Log.i(TAG, "Fetch: " + BookGenre);
                            Log.i(TAG, "Fetch: " + BookSynopsis);
                            Log.i(TAG, "Fetch: " + Available);

                            Book book = new Book();
                            Log.i(TAG, "here2");
                            book.setId(UUID.fromString(BookId));
                            Log.i(TAG, "here3");
                            book.setIsbn(BookISBN);
                            Log.i(TAG, "here4");
                            book.setTitle(BookTitle);
                            Log.i(TAG, "here5");
                            book.setAuthor(BookAuthor);
                            Log.i(TAG, "here6");
                            book.setGenre(BookGenre);
                            Log.i(TAG, "here7");
                            book.setSynopsis(BookSynopsis);
                            Log.i(TAG, "here8");
                            book.setAvailable(Integer.parseInt(Available));
                            Log.i(TAG, "here9");
                            myBooks.add(book);
                            Log.i(TAG, "here10");

                        }

                    } else {
                        Log.i(TAG, "Fetch: getBooks response else");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;

        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);

                }

            }

        }
        Log.i(TAG, "doInBackground end");
        return myBooks;
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        Log.i(TAG, "onPostExecute");
        super.onPostExecute(books);

    }
}
