package com.olegsagenadatrytwo.library;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity{

    private static final String TAG = "register";
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText repassword;
    private Button   register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registor);
        Log.i(TAG, "onCreate");

        //all the fields
        firstName        = (EditText)       findViewById(R.id.firstnamefield);
        lastName         = (EditText)       findViewById(R.id.lastnamefield);
        email            = (EditText)       findViewById(R.id.emailfield);
        username         = (EditText)       findViewById(R.id.usernamefield);
        password         = (EditText)       findViewById(R.id.passwordfield);
        repassword       = (EditText)       findViewById(R.id.retypepasswordfield);
        register         = (Button)         findViewById(R.id.registerbutton);

        //on action for the register button
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i(TAG, "submit was clicked");
                //initialize
                final String fname          = firstName.getText().toString();
                final String lname          = lastName.getText().toString();
                final String emailstring    = email.getText().toString();
                final String usernamestring = username.getText().toString();
                final String passwordstring = password.getText().toString();
                final String re_password    = repassword.getText().toString();

                if (passwordstring.equals(re_password)) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponce = new JSONObject(response);
                                boolean success = jsonResponce.getBoolean("success");

                                if (success) {
                                    Log.i(TAG, "success");
                                    Intent intent = new Intent(RegisterActivity.this, LogIn.class);
                                    startActivityForResult(intent, 0);
                                    Toast.makeText(RegisterActivity.this, "complete", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i(TAG, "Failed");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                Log.i(TAG, "exception");
                                e.printStackTrace();
                            }
                        }
                    };


                    RegisterRequest registerRequest = new RegisterRequest(fname, lname, emailstring, usernamestring, passwordstring, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                } else {
                    Toast.makeText(RegisterActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}

