package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {
    Fragment fragment = null;
    ImageView imageView;
    Bitmap selectedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        int pi = intent.getIntExtra("profileitem", 1);
        switch (pi) {
            case 1:
                fragment = new myprofileFragment();
                loadfragment(fragment);
                break;
            case 2:
                fragment = new aboutFragment();
                loadfragment(fragment);
                break;
            case 3:
                fragment = new feedbackFragment();
                loadfragment(fragment);
                break;
            case 4:
                fragment = new save_item_Fragment();
                loadfragment(fragment);
                break;
                case 5:
                fragment = new custom_item_Fragment();
                loadfragment(fragment);
                break;
                case 6:
                fragment = new favoriteFragment();
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