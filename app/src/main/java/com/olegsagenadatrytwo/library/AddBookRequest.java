package com.olegsagenadatrytwo.library;

import android.content.ContentValues;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import com.olegsagenadatrytwo.library.BookDbSchema.BookTable;

public class AddBookRequest extends StringRequest{

    public static final String REGISTER_REQUEST_URL = "http://www.selfiewars-vote.com/AddBook.php";
    private static final String TAG = "library";
    private Map<String, String> params;

    public AddBookRequest(ContentValues values, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        Log.i(TAG, "AddBookRequest: constructor");
        params = new HashMap<>();

        params.put("id", values.get(BookTable.Cols.BOOKID).toString());
        params.put("isbn",values.get(BookTable.Cols.BOOKISBN).toString() );
        params.put("title", values.get(BookTable.Cols.BOOKTITLE).toString());
        params.put("author", values.get(BookTable.Cols.BOOKAUTHOR).toString());
        params.put("genre", values.get(BookTable.Cols.BOOKGENRE).toString());
        params.put("synopsis", values.get(BookTable.Cols.BOOKSYNOPSIS).toString());
        params.put("Price", values.get(BookTable.Cols.BOOKPRICE).toString());
        params.put("encodedImage", values.get(BookTable.Cols.ENCODEDIMAGE).toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
