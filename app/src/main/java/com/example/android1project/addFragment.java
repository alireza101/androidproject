package com.example.android1project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class addFragment extends Fragment {
    Button btnsave, btncustom;
    public addFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        btncustom = view.findViewById(R.id.add_customitem);
        btnsave = view.findViewById(R.id.add_saveitem);

        btncustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadprodile(4);

            }
        });

        return view;
    }
    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_profile, fragment)
                .commit();
    }
    private void closeFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().popBackStack();
//                .beginTransaction()
//                .remove(fragment)
//                .commit();
    }
    private void loadprodile(int i) {
        Intent intent = new Intent(getActivity(), profile.class);
        intent.putExtra("profileitem", i);
        startActivity(intent);
    }

}