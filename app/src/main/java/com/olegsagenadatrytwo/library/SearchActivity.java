package com.olegsagenadatrytwo.library;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "library";

    private EditText mBookTitle;
    private EditText mBookAuthor;
    private EditText mBookGenre;
    private EditText mBookISBN;
    private Button   mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mBookTitle    = (EditText)findViewById(R.id.advanced_book_title);
        mBookAuthor   = (EditText)findViewById(R.id.advanced_book_author);
        mBookGenre    = (EditText)findViewById(R.id.advanced_book_genre);
        mBookISBN     = (EditText)findViewById(R.id.advanced_book_ISBN);
        mSearchButton = (Button)findViewById(R.id.advanced_search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mBookTitle.getText().toString();
                String author= mBookAuthor.getText().toString();
                String genre = mBookGenre.getText().toString();
                String isbn  = mBookISBN.getText().toString();

                if(title.length() != 0 || author.length() != 0 || genre.length() != 0 || isbn.length() != 0){
                    Log.i(TAG, "search");
                    int found =0;
                    ArrayList<Book> books = BookLab.get(getApplicationContext()).getBooks();
                    for(int i = 0; i<books.size(); i++){
                            if(books.get(i).getTitle().toLowerCase().contains(title.toLowerCase()) && title.length() !=0 ||
                                    books.get(i).getAuthor().toLowerCase().contains(author.toLowerCase()) && author.length() != 0 ||
                                    books.get(i).getGenre().toLowerCase().contains(genre.toLowerCase()) && genre.length() != 0 ||
                                    books.get(i).getIsbn().toLowerCase().contains(isbn.toLowerCase()) && isbn.length() != 0) {
                                Book book = books.get(i);
                                books.remove(i);
                                books.add(0, book);
                                found++;
                                Log.i(TAG, i+"");
                            }
                    }
                    BookLab.get(getApplicationContext()).setBookList(books);
                    if(found == 0){
                        Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(SearchActivity.this, Home.class);
                        SearchActivity.this.startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Enter at least one field", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
