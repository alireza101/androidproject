package com.example.android1project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class aboutFragment extends Fragment {
TextView tv_back;
    public aboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        tv_back=view.findViewById(R.id.about_back);

        tv_back.setOnClickListener(view1 -> requireActivity().finish());

        return view;
    }
}