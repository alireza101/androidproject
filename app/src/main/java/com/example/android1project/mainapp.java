package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class mainapp extends AppCompatActivity implements IData{
//    private ArrayList<String> mnamehor = new ArrayList<>();
//    private ArrayList<Integer> mimagehor = new ArrayList<Integer>();

    static ArrayList<item> itemArrayList = new ArrayList<>();
    static ArrayList<type> typeArrayList = new ArrayList<>();

    webapihandler webapihandler;
    RecyclerView rcmain,rcmainhor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainapp);
        webapihandler=new webapihandler(this);
        webapihandler.apiconecct("itemtype");
        webapihandler.apiconecct("register");
        rcmain=findViewById(R.id.recyclerviewver);
        rcmainhor=findViewById(R.id.recyclerview);
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


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    @Override
    public void sendata() {
        recyclerviewadapter_hor adapte1=new recyclerviewadapter_hor(this,typeArrayList);
        rcmainhor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rcmainhor.setAdapter(adapte1);
        recyclerviewadapter_ver adapter =new recyclerviewadapter_ver(this,itemArrayList);
        rcmain.setLayoutManager(new LinearLayoutManager(this));
        rcmain.setAdapter(adapter);
    }
}
