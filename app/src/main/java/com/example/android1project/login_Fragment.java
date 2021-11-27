package com.example.android1project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class login_Fragment extends Fragment {

    EditText txtusername,txtuserpassword;
    TextView txtlogingo;
    Button btnlogin;
    public login_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        txtusername=view.findViewById(R.id.txtusername);
        txtuserpassword=view.findViewById(R.id.txtuserpassword);
        txtlogingo=view.findViewById(R.id.txtlogingo);

        btnlogin=view.findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        txtlogingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new signup_Fragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.page1,fragment)
                        .commit();
            }
        });

        return view;
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

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        user user = new user(
                                userJson.getInt("userid"),
                                userJson.getString("username"),
                                userJson.getString("useremail"),
                                userJson.getString("userpassword")

                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getActivity()).userLogin(user);

                        //starting the profile activity
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), mainapp.class));
                    } else {
                        Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

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