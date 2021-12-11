package com.example.android1project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class profileFragment extends Fragment {
    Button btn_logout, btn_myprofile, btn_about, btn_feedback;
    TextView textView;
    ImageView imageView;
    SwitchCompat switchCompat;
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
        switchCompat=view.findViewById(R.id.profile_switch1);

        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (switchCompat.isChecked()){
                run_service.flag_notification=1;
                Toast.makeText(getActivity(), "notification is enable", Toast.LENGTH_SHORT).show();
            }else {
                run_service.flag_notification=0;
                Toast.makeText(getActivity(), "notification is disable", Toast.LENGTH_SHORT).show();

            }
        });
        btn_feedback.setOnClickListener(view1 -> loadprodile(3));
        btn_about.setOnClickListener(view12 -> loadprodile(2));
        btn_myprofile.setOnClickListener(view13 -> loadprodile(1));
        btn_logout.setOnClickListener(view14 -> {
            getActivity().finish();
            SharedPrefManager_user.getInstance(getActivity()).logout();
        });
        user user = SharedPrefManager_user.getInstance(getActivity()).getUser();
        textView.setText(user.getUsername().trim());

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