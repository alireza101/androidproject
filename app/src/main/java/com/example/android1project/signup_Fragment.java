package com.example.android1project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class signup_Fragment extends Fragment {

    EditText txtusername,txtuserpassword,txtuseremail;
    TextView txtlogingo;
    Button btnlogin;

    public signup_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        txtusername=view.findViewById(R.id.txtusername);
        txtuserpassword=view.findViewById(R.id.txtuserpassword);
        txtuseremail=view.findViewById(R.id.txtuseremail);
        txtlogingo=view.findViewById(R.id.txtlogingo);
        btnlogin=view.findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    registerUser();
            }
        });




            txtlogingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new login_Fragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.page1,fragment)
                        .commit();
//                finish();
//                startActivity(new Intent(signup_activity.this,login_activity.class));

            }
        });


        return view;
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

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);

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

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion

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
                        SharedPrefManager_user.getInstance(getActivity()).userLogin(user);

                        //starting the profile activity
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), mainapp.class));
                    } else {
                        Toast.makeText(getActivity(), obj.getString("message")+"..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }


}
