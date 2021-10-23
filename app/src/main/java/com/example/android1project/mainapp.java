package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;


public class mainapp extends AppCompatActivity implements IData{


    static ArrayList<item> itemArrayList = new ArrayList<>();
    ArrayList<item> itemArrayListfilter = new ArrayList<>();

    static ArrayList<type> typeArrayList = new ArrayList<>();
    boolean flagfilter=false;

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
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, login_activity.class));
        }
        rcmain.addOnItemTouchListener(new RecyclerTouchListener(this, rcmain, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(mainapp.this,detail_item.class);
                item item;
                if (flagfilter) {
                    item = itemArrayListfilter.get(position);
                }else {
                    item = itemArrayList.get(position);
                }
                String getidname = "";
                for (type type:typeArrayList){
                    if (item.getItemtype().equals(type.getTypeid())){
                        getidname=type.getTypename();
                        break;
                    }
                }
                String[]putitem=new String[]{item.getItemid(),item.getItemname(),item.getItempicture(),item.getItemsum()
                        ,getidname,item.getItemexpiration()};
                intent.putExtra("item",putitem);
                startActivity(intent);
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
                flagfilter=true;

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
