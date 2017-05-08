package com.olegsagenadatrytwo.library;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetBooksRequest extends StringRequest {

    public static final String GETBOOKS_REQUEST_URL = "http://www.selfiewars-vote.com/GetBooks.php";
    private static final String TAG = "library";
    private Map<String, String> params;

    public GetBooksRequest(Response.Listener<String> listener) {
        super(Method.POST, GETBOOKS_REQUEST_URL, listener, null);
        Log.i(TAG, "GetBooksRequest constructor");
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        Log.i(TAG, "GetBooksRequest getParams");
        return params;
    }
}
