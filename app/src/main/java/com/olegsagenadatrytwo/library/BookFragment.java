package com.olegsagenadatrytwo.library;


import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class BookFragment extends Fragment{

    private static final String TAG = "library";
    private static final String ARGS_BOOK_ID = "book_id";
    private static final int RESULT_LOAD_IMAGE = 1;

    private Book       mBook;
    private ImageView amBookImage;
    private Bitmap   amBitmapImage;
    private String   amEncodedImage;
    private EditText amBookTitleField;
    private EditText amBookAuthorField;
    private EditText amBookGenreField;
    private EditText amBookISBNField;
    private EditText amBookSynopsis;
    private Button   amSubmitBook;
    private EditText amPrice;

    private ImageView mImageView;
    private TextView  mBookTitleField;
    private TextView  mBookAuthorField;
    private TextView  mBookGenreField;
    private TextView  mBookISBNField;
    private TextView  mBookSynopsis;
    private TextView  mBookPrice;
    private ImageView mBookImage;

    private ArrayList<Book> myBooks;
    private int administrator;

    public static BookFragment newInstance(UUID bookId, int administrator){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_BOOK_ID,bookId);
        args.putSerializable("administrator", administrator);
        BookFragment fragment = new BookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "BookFragment: onCreate");
        UUID bookId = (UUID)getArguments().getSerializable(ARGS_BOOK_ID);
        administrator = (int)getArguments().getSerializable("administrator");
        myBooks = BookLab.get(getActivity()).getBooks();
        mBook = getBook(bookId);
        setHasOptionsMenu(true);
        Log.i(TAG, "BookFragment: end onCreate "+ administrator);
    }

    public Book getBook(UUID id){
        for(int i = 0; i<myBooks.size(); i++){
            if(myBooks.get(i).getId() == id){
                return myBooks.get(i);
            }
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "BookFragment: onCreateView");
        View v;

        if(administrator==1) {
             v = inflater.inflate(R.layout.book_complete_detail, container, false);
            //__________________________________________________________________________________________
            amBookImage = (ImageView)v.findViewById(R.id.abook_image);
            if(mBook.getBitmap() == null){
                Log.i(TAG, "bitmap was null in bookfragment");
                amBookImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_book_picture_default));
            }
            else{
                amBookImage.setImageBitmap(mBook.getBitmap());
            }

            amBookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery,RESULT_LOAD_IMAGE);
                }
            });

            amPrice = (EditText) v.findViewById(R.id.abook_price);
            amPrice.setText(mBook.getPrice());
            amPrice.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mBook.setPrice(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mBook.setPrice(editable.toString());
                }
            });

            amBookTitleField = (EditText)v.findViewById(R.id.abook_title);
            Log.i(TAG, "BookFragment: onCreateView 2");
            amBookTitleField.setText(mBook.getTitle());
            amBookTitleField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i(TAG, "BookFragment: beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i(TAG, "BookFragment: onTextChanged");
                    mBook.setTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i(TAG, "BookFragment: afterTextChanged");
                    mBook.setTitle(s.toString());
                }
            });
            //__________________________________________________________________________________________
            amBookAuthorField = (EditText)v.findViewById(R.id.abook_author);
            amBookAuthorField.setText(mBook.getAuthor());
            amBookAuthorField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i(TAG, "BookFragment: beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i(TAG, "BookFragment: onTextChanged");
                    mBook.setAuthor(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i(TAG, "BookFragment: afterTextChanged");
                    mBook.setAuthor(s.toString());
                }
            });
            //__________________________________________________________________________________________
            amBookGenreField = (EditText)v.findViewById(R.id.abook_genre);
            amBookGenreField.setText(mBook.getGenre());
            amBookGenreField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i(TAG, "BookFragment: beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i(TAG, "BookFragment: onTextChanged");
                    mBook.setGenre(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i(TAG, "BookFragment: afterTextChanged");
                    mBook.setGenre(s.toString());
                }
            });
            //__________________________________________________________________________________________
            amBookISBNField = (EditText)v.findViewById(R.id.abook_ISBN);
            amBookISBNField.setText(mBook.getIsbn());
            amBookISBNField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i(TAG, "BookFragment: beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i(TAG, "BookFragment: onTextChanged");
                    mBook.setIsbn(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i(TAG, "BookFragment: afterTextChanged");
                    mBook.setIsbn(s.toString());
                }
            });
            //__________________________________________________________________________________________
            amBookSynopsis = (EditText)v.findViewById(R.id.abook_synopsis);
            amBookSynopsis.setText(mBook.getSynopsis());
            amBookSynopsis.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.i(TAG, "BookFragment: beforeTextChanged");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.i(TAG, "BookFragment: onTextChanged");
                    mBook.setSynopsis(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i(TAG, "BookFragment: afterTextChanged");
                    mBook.setSynopsis(s.toString());
                }
            });

            Log.i(TAG, "BookFragment: before button");
            amSubmitBook = (Button)v.findViewById(R.id.submit_new_book);
            amSubmitBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                Log.i(TAG, "BookFragment: response try");
                                //create a json Object to get the response from the server
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success         = jsonResponse.getBoolean("success");
                                if(success){
                                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                    if(amBitmapImage != null) {
                                        mBook.setBitmap(amBitmapImage);
                                    }
                                }
                                else{
                                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    };

                    AddBookRequest addBookRequest = new AddBookRequest(getContentValues(mBook), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(addBookRequest);

                }
            });
        }
        else{
             v = inflater.inflate(R.layout.book_complete_details_for_regular,container,false);

            mImageView = (ImageView)v.findViewById(R.id.book_image);
            mBookTitleField = (TextView)v.findViewById(R.id.book_title);
            mBookAuthorField = (TextView)v.findViewById(R.id.book_author) ;
            mBookGenreField = (TextView)v.findViewById(R.id.book_genre);
            mBookISBNField = (TextView)v.findViewById(R.id.book_ISBN);
            mBookSynopsis = (TextView)v.findViewById(R.id.book_synopsis);
            mBookPrice  = (TextView)v.findViewById(R.id.book_price);

            if(mBook.getBitmap() == null){
                mImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_book_picture_default));
            }
            else{
                mImageView.setImageBitmap(mBook.getBitmap());
            }

            mBookTitleField.setText(mBook.getTitle());
            mBookAuthorField.setText(mBook.getAuthor());
            mBookGenreField.setText(mBook.getGenre());
            mBookISBNField.setText(mBook.getIsbn());
            mBookSynopsis.setText(mBook.getSynopsis());
            mBookPrice.setText("$" +mBook.getPrice());
        }
        Log.i(TAG, "BookFragment: end onCreateView");
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public static ContentValues getContentValues(Book book){
        Log.i(TAG, "BookLab: getContentValues");
        ContentValues values = new ContentValues();

        values.put(BookDbSchema.BookTable.Cols.BOOKID, book.getId().toString());
        values.put(BookDbSchema.BookTable.Cols.BOOKISBN, book.getIsbn());
        values.put(BookDbSchema.BookTable.Cols.BOOKTITLE, book.getTitle());
        values.put(BookDbSchema.BookTable.Cols.BOOKAUTHOR, book.getAuthor());
        values.put(BookDbSchema.BookTable.Cols.BOOKGENRE, book.getGenre());
        values.put(BookDbSchema.BookTable.Cols.BOOKSYNOPSIS, book.getSynopsis());
        values.put(BookDbSchema.BookTable.Cols.BOOKPRICE, book.getPrice());
        values.put(BookDbSchema.BookTable.Cols.ENCODEDIMAGE, book.getEncodedImage());

        Log.i(TAG, "CrimeLab: end getContentValues");
        return values;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            amBookImage.setImageURI(selectedImage);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            amBitmapImage = ((BitmapDrawable)amBookImage.getDrawable()).getBitmap();
            amBitmapImage.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
            amEncodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
            mBook.setEncodedImage(amEncodedImage);
        }
    }

    //this method places the menu in the action bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Log.i(TAG, "BookFragment: onCreateOptionsMenu");
            inflater.inflate(R.menu.toolbar_inside_complete_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "BookListFragment: onOptionsItemSelected");
        //switch statement to determine which of the action bar items have been selected
        switch (item.getItemId()){
            case R.id.menu_add_to_cart:

                Cart.get().addBookToCart(mBook);
                Log.i(TAG, "BookFragment: case: add to cart");
                return true;
            case R.id.menu_view_cart:
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

