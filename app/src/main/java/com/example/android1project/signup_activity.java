package com.example.android1project;



import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class signup_activity extends AppCompatActivity {
    EditText txtusername,txtuserpassword,txtuseremail;
    TextView txtlogingo;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtusername=findViewById(R.id.txtusername);
        txtuserpassword=findViewById(R.id.txtuserpassword);
        txtuseremail=findViewById(R.id.txtuseremail);
        txtlogingo=findViewById(R.id.txtlogingo);

        btnlogin=findViewById(R.id.btnlogin);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, mainapp.class));
            return;
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        txtlogingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(signup_activity.this,login_activity.class));

            }
        });
    }
    private void registerUser() {
        final String username = txtusername.getText().toString().trim();
        final String useremail = txtuseremail.getText().toString().trim();
        final String userpassword = txtuserpassword.getText().toString().trim();



        if (TextUtils.isEmpty(username)) {
            txtusername.setError("Please enter username");
            txtusername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(useremail)) {
            txtuseremail.setError("Please enter your email");
            txtuseremail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
            txtuseremail.setError("Enter a valid email");
            txtuseremail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userpassword)) {
            txtuserpassword.setError("Enter a password");
            txtuserpassword.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("useremail", useremail);
                params.put("userpassword", userpassword);

                //returing the response
                return requestHandler.sendPostRequest(config.signup, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        user user = new user(
                                userJson.getInt("userid"),
                                userJson.getString("username"),
                                userJson.getString("useremail")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), mainapp.class));
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message")+"..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}