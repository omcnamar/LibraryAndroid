package com.olegsagenadatrytwo.library;

import android.content.ContentValues;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GetBookRequest extends StringRequest{

    public static final String REGISTER_REQUEST_URL = "http://www.selfiewars-vote.com/GetBook.php";
    private static final String TAG = "library";
    private Map<String, String> params;

    public GetBookRequest(UUID uuid, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        Log.i(TAG, "GetBookRequest: constructor");
        params = new HashMap<>();

        params.put("id", uuid.toString());

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
