package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

public class profile extends AppCompatActivity {
Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent=getIntent();
        int pi=intent.getIntExtra("profileitem",1);
        switch (pi){
            case 1:
                fragment=new myprofileFragment();
                loadfragment(fragment);
                break;
            case 2:
                fragment=new aboutFragment();
                loadfragment(fragment);
                break;
            case 3:
                fragment=new feedbackFragment();
                loadfragment(fragment);
                break;
        }
    }

    private void loadfragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_profile, fragment)
                .commit();
    }
}