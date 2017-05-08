package com.olegsagenadatrytwo.library;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {
    //initialize static variables
    private static final String TAG = "library";
    private EditText        usernamefield;
    private EditText        passwordfield;

    //onCreate is the first method that is called by the androids os
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout view to activity_log_in
        setContentView(R.layout.activity_log_in);

        //all the Log.i will display a particular string to keep track of the execution flow
        Log.i(TAG, "LogIn onCreate");

        //initialize variables by finding them by id from the layout files
        TextView createaccount = (TextView) findViewById(R.id.registerbutton);
        Button log             = (Button)   findViewById(R.id.logButton);
        usernamefield          = (EditText) findViewById(R.id.usernameEmailfield);
        passwordfield          = (EditText) findViewById(R.id.passwordfield);

        //on action for the log in button
        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //display log message
                Log.i(TAG, "Log In was clicked");
                //get strings entered into username and password fields
                final String username = usernamefield.getText().toString();
                final String password = passwordfield.getText().toString();

                //create a response listener to listen for response from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i(TAG, "LogIn: response try");
                            //create a json Object to get the response from the server
                            JSONObject jsonResponse = new JSONObject(response);
                            /**create a boolean variable and initialize it to true if registration
                             was successful*/
                            boolean success = jsonResponse.getBoolean("success");

                            /**if jsonResponse("success") was true from the server then the user
                             can proceed and the if statement will run*/
                            if(success){
                                //display log message to console
                                Log.i(TAG, "Log in success");

                                /**get all the variables that were sent by the server for the user
                                 that is trying to log in **/
                                String firstName = jsonResponse.getString("firstName");
                                String lastName  = jsonResponse.getString("lastName");
                                int user_id      = jsonResponse.getInt("user_id");
                                String email     = jsonResponse.getString("email");
                                String userName  = jsonResponse.getString("userName");
                                int administrator= jsonResponse.getInt("administrator");

                                /**Create an intent to jump to the Home Activity after the user
                                 passes the registration **/
                                Intent intent = new Intent(LogIn.this, Home.class);
                                //display a message to the user that log in was successful
                                Toast.makeText(LogIn.this, "success", Toast.LENGTH_SHORT).show();
                                //pass some information to the Home activity
                                intent.putExtra("firstName", firstName);
                                intent.putExtra("lastName", lastName);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("email", email);
                                intent.putExtra("userName", userName);
                                intent.putExtra("password", password);
                                intent.putExtra("administrator", administrator);

                                //Start the Home Activity with the intent
                                LogIn.this.startActivity(intent);
                            }
                            //if user log in failed than the message of failure will be displayed
                            else{
                                Log.i(TAG, "Login failed");
                                AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                };
                /**Create an instance of LoginRequest and pass the username and password to attempt
                 log in*/
                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LogIn.this);
                queue.add(loginRequest);
            }
        });

        //on action for the register button
        createaccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //display message log
                Log.i(TAG, "Create Account was clicked");
                //use intent to get to the RegisterActivity
                Intent intent = new Intent(view.getContext(), RegisterActivity.class);
                startActivityForResult(intent, 0);
                Toast.makeText(LogIn.this, "Register", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

}
