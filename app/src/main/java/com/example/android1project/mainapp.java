package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class mainapp extends AppCompatActivity implements IData{
    private ArrayList<String> mnamehor = new ArrayList<>();
    private ArrayList<Integer> mimagehor = new ArrayList<Integer>();

    static ArrayList<item> itemArrayList = new ArrayList<>();

    webapihandler webapihandler;
    RecyclerView rcmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainapp);
        inititemshor();
        webapihandler=new webapihandler(this);
        webapihandler.apiconecct("register");
        rcmain=findViewById(R.id.recyclerviewver);
    }


    private void inititemshor() {
        mnamehor.add("beverages");
        mimagehor.add(R.drawable.beverages);

        mnamehor.add("dairy");
        mimagehor.add(R.drawable.dairy);

        mnamehor.add("fruit");
        mimagehor.add(R.drawable.fruit);

        mnamehor.add("protein");
        mimagehor.add(R.drawable.protein);

        mnamehor.add("vegetable");
        mimagehor.add(R.drawable.vegetable);

        initrecyclerview();
    }

    private void initrecyclerview(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerviewadapter_hor adapter=new recyclerviewadapter_hor(this,mnamehor,mimagehor);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void sendata() {

        recyclerviewadapter_ver adapter =new recyclerviewadapter_ver(this,itemArrayList);
        rcmain.setLayoutManager(new LinearLayoutManager(this));
        rcmain.setAdapter(adapter);
    }
}