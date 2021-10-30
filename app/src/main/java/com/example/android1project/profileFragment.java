package com.example.android1project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class profileFragment extends Fragment {
    Button btn_logout, btn_myprofile, btn_about, btn_feedback;
    TextView textView;
    ImageView imageView;

    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btn_logout = view.findViewById(R.id.profile_logout);
        textView = view.findViewById(R.id.profile_name);
        btn_myprofile = view.findViewById(R.id.profile_myprofile);
        btn_about = view.findViewById(R.id.profile_aboutapp);
        btn_feedback = view.findViewById(R.id.profile_feedback);
        imageView = view.findViewById(R.id.profile_image);

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadprodile(3);
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadprodile(2);
            }
        });
        btn_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadprodile(1);

            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getActivity()).logout();
            }
        });
        user user = SharedPrefManager.getInstance(getActivity()).getUser();
        textView.setText(user.getUsername().toString().trim());

        if (user.getUsergender() == "man") {
            imageView.setImageResource(R.drawable.male_user);
        } else {
            imageView.setImageResource(R.drawable.female_user);
        }
        return view;
    }

    private void loadprodile(int i) {
        Intent intent = new Intent(getActivity(), profile.class);
        intent.putExtra("profileitem", i);
        startActivity(intent);
    }

}