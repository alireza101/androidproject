package com.example.android1project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class homeFragment extends Fragment  {
    static ArrayList<item> itemArrayList = new ArrayList<>();
    ArrayList<item> itemArrayListfilter = new ArrayList<>();

    static ArrayList<type> typeArrayList = new ArrayList<>();
    boolean flagfilter=false;

    webapihandler webapihandler;
    RecyclerView rcmain,rcmainhor;

    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle=getArguments();
        boolean loaddata=true;
        if(bundle!=null){
             loaddata=bundle.getBoolean("loaddata");
        }
        if (loaddata!=false || bundle==null) {
            webapihandler = new webapihandler(getActivity());
            webapihandler.apiconecct("itemtype");
            webapihandler.apiconecct("register");
        }
            rcmain = view.findViewById(R.id.recyclerviewver);
            rcmainhor = view.findViewById(R.id.recyclerview);

        rcmain.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rcmain, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(),detail_item.class);
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
        rcmainhor.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rcmainhor, new RecyclerTouchListener.ClickListener() {
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
                recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getActivity(), itemArrayListfilter);
                rcmain.setAdapter(adapter);
                flagfilter=true;

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        typedata();
        return view;
    }
    public void typedata(){
        recyclerviewadapter_hor adapte1=new recyclerviewadapter_hor(getActivity(),typeArrayList);
        rcmainhor.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rcmainhor.setAdapter(adapte1);
        recyclerviewadapter_ver adapter =new recyclerviewadapter_ver(getActivity(),itemArrayList);
        rcmain.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcmain.setAdapter(adapter);
    }


}