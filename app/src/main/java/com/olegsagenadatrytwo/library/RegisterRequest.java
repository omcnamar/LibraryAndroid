package com.olegsagenadatrytwo.library;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    public static final String REGISTER_REQUEST_URL = "http://www.selfiewars-vote.com/LibraryRegister.php";
    private static final String TAG = "RegistorReqqest";
    private Map<String, String> params;

    public RegisterRequest(String firstName, String lastName, String email, String username, String password, Response.Listener<String> listener){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}