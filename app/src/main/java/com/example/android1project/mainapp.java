package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class mainapp extends AppCompatActivity{
    private ArrayList<String> mnamehor = new ArrayList<>();
    private ArrayList<Integer> mimagehor = new ArrayList<Integer>();

    private ArrayList<String> miteemname = new ArrayList<>();
    private ArrayList<String> mitemsum = new ArrayList<>();
    private ArrayList<String> mitemimage = new ArrayList<>();
    private ArrayList<String> mitemexprece = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainapp);
        inititemshor();
        inititemsver();

    }

    private void inititemsver() {

        miteemname.add("apple");
        mitemimage.add("https://res.cloudinary.com/dlgnk4lmq/image/upload/v1631766109/sample.jpg");
        mitemexprece.add("5");
        mitemsum.add("2");

        miteemname.add("benana");
        mitemimage.add("https://res.cloudinary.com/dlgnk4lmq/image/upload/v1631766109/sample.jpg");
        mitemexprece.add("2");
        mitemsum.add("1");
        initrecyclerviewver();

    }

    private void initrecyclerviewver() {
        LinearLayoutManager linearLayoutManagerver=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerViewver=findViewById(R.id.recyclerviewver);
        recyclerViewver.setLayoutManager(linearLayoutManagerver);
        recyclerviewadapter_ver adapter1=new recyclerviewadapter_ver(this,miteemname,mitemimage,mitemsum,mitemexprece);
        recyclerViewver.setAdapter(adapter1);
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


}