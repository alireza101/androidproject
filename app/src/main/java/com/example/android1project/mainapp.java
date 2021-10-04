package com.example.android1project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class mainapp extends AppCompatActivity implements IData, NavigationView.OnNavigationItemSelectedListener {
//    private ArrayList<String> mnamehor = new ArrayList<>();
//    private ArrayList<Integer> mimagehor = new ArrayList<Integer>();

    static ArrayList<item> itemArrayList = new ArrayList<>();
     ArrayList<item> itemArrayListfilter = new ArrayList<>();
    static ArrayList<type> typeArrayList = new ArrayList<>();
    private DrawerLayout drawer;

    webapihandler webapihandler;
    RecyclerView rcmain, rcmainhor;
    TextView emailnavigation, namenavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainapp);
        webapihandler = new webapihandler(this);
        webapihandler.apiconecct("itemtype");
        webapihandler.apiconecct("register");
        rcmain = findViewById(R.id.recyclerviewver);
        rcmainhor = findViewById(R.id.recyclerview);
        emailnavigation = findViewById(R.id.email_navigation);
        namenavigation = findViewById(R.id.name_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

 //       user user = SharedPrefManager.getInstance(this).getUser();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, login_activity.class));
        }

 //       String namnav = "ID:" + String.valueOf(user.getId()) + "=" + user.getUsername();
//       namenavigation.setText();
        //emailnavigation.setText(user.getUsermail()+" h");
//        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
//            }
//        });


        // String [] user = new String[2];
        //  user[0]=SharedPrefManager.getInstance(getApplicationContext()).getUser()
        //  Picasso.with(getApplicationContext()).load("https://res.cloudinary.com/dlgnk4lmq/image/upload/v1633006560/android1pro/secoundactivity/2567134_a8pu69.jpg").into(image_header_navigation);


        rcmain.addOnItemTouchListener(new RecyclerTouchListener(this, rcmain, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        rcmainhor.addOnItemTouchListener(new RecyclerTouchListener(this, rcmainhor, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                itemArrayListfilter.clear();

                type type=typeArrayList.get(position);
                for (item item : itemArrayList) {
                    if (type.getTypename().equals("All")){
                        itemArrayListfilter.add(item);
                    }else {
                        if (type.getTypeid().equals(item.getItemtype())) {
                            itemArrayListfilter.add(item);
                        }
                    }
                }
                recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getApplicationContext(), itemArrayListfilter);
                rcmain.setAdapter(adapter);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                break;
            case R.id.db:
                finish();
                startActivity(new Intent(getApplicationContext(), mainapp.class));
                break;

        }
        return true;
    }

    @Override
    public void sendata() {
        recyclerviewadapter_hor adapte1 = new recyclerviewadapter_hor(this, typeArrayList);
        rcmainhor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rcmainhor.setAdapter(adapte1);
        recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(this, itemArrayList);
        rcmain.setLayoutManager(new LinearLayoutManager(this));
        rcmain.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
