package com.example.android1project;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class save_item_Fragment extends Fragment {
    private  ArrayList<type> typeArrayList_save ;
    private  ArrayList<type> typeArrayList_save1 ;
    RecyclerView recyclerView;
    public save_item_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_save_item, container, false);
        typeArrayList_save = new ArrayList<>();
        typeArrayList_save1 = new ArrayList<>();

        typeArrayList_save=homeFragment.typeArrayList;
        for (int i=0;i<typeArrayList_save.size();i++){
            type type=typeArrayList_save.get(i);
            if (!type.getTypename().equals("All")){
                typeArrayList_save1.add(type);
            }
        }
        recyclerView=view.findViewById(R.id.saveitem_recyclerview_save);

        recyclerviewadapter_saveitem adapte1 = new recyclerviewadapter_saveitem(getActivity(), typeArrayList_save1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapte1);

        return view;
    }


}