package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login_activity extends AppCompatActivity {

    EditText txtusername,txtuserpassword;
    TextView txtlogingo;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtusername=findViewById(R.id.txtusername);
        txtuserpassword=findViewById(R.id.txtuserpassword);
        txtlogingo=findViewById(R.id.txtlogingo);

        btnlogin=findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        txtlogingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(login_activity.this,signup_activity.class));
            }
        });
    }
    private void userLogin() {
        //first getting the values
        final String username = txtusername.getText().toString();
        final String userpassword = txtuserpassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            txtusername.setError("Please enter your username");
            txtusername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userpassword)) {
            txtuserpassword.setError("Please enter your password");
            txtuserpassword.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
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
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("userpassword", userpassword);

                //returing the response
                return requestHandler.sendPostRequest(config.login, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
}