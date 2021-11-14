package com.example.android1project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class custom_item_Fragment extends Fragment {
ImageView imageView;
FloatingActionButton fa;
    public custom_item_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_custom_item, container, false);
        imageView=view.findViewById(R.id.custom_image);
        fa=view.findViewById(R.id.custom_floatingactionbutton);


        fa.setOnClickListener(view1 -> {



        });

        return view;
    }
}