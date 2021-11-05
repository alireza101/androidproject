package com.example.android1project;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class myprofileFragment extends Fragment {
    LottieAnimationView myprofile_man, myprofile_woman;
    TextView tvback, tvok, edgender;
    EditText edname, edemail, edpassword;
    user user;
    String sedname, sedemail, sedpassword;
    ImageView imageView;

    public myprofileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);
        myprofile_man = view.findViewById(R.id.myprofile_man);
        myprofile_woman = view.findViewById(R.id.myprofile_woman);
        tvback = view.findViewById(R.id.myprofile_back);
        tvok = view.findViewById(R.id.myprofile_ok);
        edname = view.findViewById(R.id.myprofile_name);
        edemail = view.findViewById(R.id.myprofile_email);
        edpassword = view.findViewById(R.id.myprofile_password);
        edgender = view.findViewById(R.id.myprofile_gender);
        imageView = view.findViewById(R.id.myprofile_image);

        sedname = edname.getText().toString();
        sedemail = edemail.getText().toString();
        sedpassword = edpassword.getText().toString();

        user = SharedPrefManager.getInstance(getActivity()).getUser();

        edname.setText(user.getUsername());
        edemail.setText(user.getUsermail());
        edpassword.setText(user.getUserpassword());
        edgender.setText(user.getUsergender());


        if (edgender.getText().equals("man")) {
            imageView.setImageResource(R.drawable.male_user);
        } else {
            imageView.setImageResource(R.drawable.female_user);
        }
        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        tvok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
                getActivity().finish();
            }
        });
        myprofile_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edgender.setText("man");
                imageView.setImageResource(R.drawable.male_user);

            }
        });
        myprofile_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edgender.setText("woman");
                imageView.setImageResource(R.drawable.female_user);
            }
        });

        return view;
    }

    private void loadfragment(Fragment fragment) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void registeruser() {

        sedname = edname.getText().toString();
        sedemail = edemail.getText().toString();
        sedpassword = edpassword.getText().toString();
        final String sedgender = edgender.getText().toString();
        if (TextUtils.isEmpty(sedname)) {
            edname.setError("Please enter username");
            edname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sedemail)) {
            edemail.setError("Please enter your email");
            edemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(sedemail).matches()) {
            edemail.setError("Enter a valid email");
            edemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sedpassword)) {
            edpassword.setError("Enter a password");
            edpassword.requestFocus();
            return;
        }


        //if it passes all the validations

        class registeruser extends AsyncTask<Void, Void, String> {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("getuserid", String.valueOf(user.getId()));
                params.put("getusername", sedname);
                params.put("getuserpassword", sedpassword);
                params.put("getuseremail", sedemail);
                params.put("getusergender", sedgender);

                //returing the response
                return requestHandler.sendPostRequest(config.change, params);
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
                    JSONObject jsonObject = new JSONObject(s);
                    Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    user = new user(
                            user.getId(),
                            sedname,
                            sedemail,
                            sedpassword,
                            sedgender
                    );
                    SharedPrefManager.getInstance(getActivity()).userLogin(user);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }


        }
        //executing the async task
        registeruser ru = new registeruser();
        ru.execute();

    }


}