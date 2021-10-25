package com.example.android1project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class profileFragment extends Fragment {
    Button btn_logout;
    TextView textView;

    public profileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        btn_logout=view.findViewById(R.id.profile_logout);
        textView=view.findViewById(R.id.profile_name);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getActivity()).logout();
            }
        });
        user user=SharedPrefManager.getInstance(getActivity()).getUser();
        textView.setText(user.getUsername().toString().trim());

        return view;
    }
}