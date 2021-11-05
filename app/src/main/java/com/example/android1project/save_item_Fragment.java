package com.example.android1project;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class save_item_Fragment extends Fragment {
    private ArrayList<type> typeArrayList_save = new ArrayList<>();

    private final ArrayList<item> itemArrayList_save = new ArrayList<>();

    RecyclerView recyclerViewtype, recyclerViewitem;
    LinearLayout layout_type,layout_item;
    ImageView typeimage,itemimage;
    TextView typename,saveitem_back;
    Dialog dialog;
    Fragment fragment;
    public save_item_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_item, container, false);
        //find view by id
        recyclerViewtype = view.findViewById(R.id.saveitem_recyclerview_save_type);
        recyclerViewitem = view.findViewById(R.id.saveitem_recyclerview_save_item);

        layout_type=view.findViewById(R.id.recyclerview_lineartype);
        layout_item=view.findViewById(R.id.recyclerview_linearitem);

        typeimage=view.findViewById(R.id.saveitem_image);
        typename=view.findViewById(R.id.saveitem_name);

        saveitem_back=view.findViewById(R.id.saveitem_back);

        //paramet
        typeArrayList_save.clear();
        for (int i = 0; i < homeFragment.typeArrayList.size(); i++) {
            type type = homeFragment.typeArrayList.get(i);
            if (!type.getTypename().equals("All")) {
                typeArrayList_save.add(type);
            }
        }

        recyclerviewadapter_savetype adapte1 = new recyclerviewadapter_savetype(getActivity(), typeArrayList_save);
        recyclerViewtype.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewtype.setAdapter(adapte1);
        recyclerViewtype.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewtype, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //load item of type

                type type = typeArrayList_save.get(position);
                registeritem(type.getTypeid());

                typename.setText(type.getTypename());
                Picasso.with(getActivity()).load(type.getTypepicture1()).into(typeimage);




                layout_type.setVisibility(View.GONE);
                layout_item.setVisibility(View.VISIBLE);
                typeimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_type.setVisibility(View.VISIBLE);
                        layout_item.setVisibility(View.GONE);
                    }
                });
                recyclerViewitem.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewitem, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        itemimage=view.findViewById(R.id.saveitem_showitem);
                        roateImage(true,itemimage);
                        item item=itemArrayList_save.get(position);

                        String []a={item.getItempicture(),item.getItemname(),item.getItemexpiration(),type.getTypeid()};
                        fragment=new additemFragment();
                        Bundle bundle=new Bundle();
                        bundle.putStringArray("dataitempnet",a);
                        fragment.setArguments(bundle);
                        loadfragment(fragment);
                        roateImage(false,itemimage);


                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));




//                //load item of type
//                if (!f) {
//                    type type = typeArrayList_save.get(position);
//                    registeritem(type.getTypeid());
//                }
//                //load 2 parameter of recyclerview layout
//
////                Integer resource = (Integer) imageView.getTag();
//
//                //if add to close if close to add
//                if (!f) {
//                    roateImage(true,imageView);
////                    imageView.setImageResource(R.drawable.close);
////                    imageView.setTag(R.drawable.close);
//                    recyclerViewitem.setVisibility(View.VISIBLE);
//
//
//
//                    f = true;
//                } else {
//                    roateImage(false,imageView);
////                    imageView.setImageResource(R.drawable.add);
////                    imageView.setTag(R.drawable.add);
//                    recyclerViewitem.setVisibility(View.GONE);
//
//                    f = false;
//                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        saveitem_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void loadfragment(Fragment fragment) {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_profile, fragment)
                .addToBackStack(null)
                .commit();
    }
    //****************************************************************************************************************  method

    private void roateImage(boolean f,ImageView imageView) {
        if (f) {
            @SuppressLint("Recycle") ObjectAnimator ro = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 45f);
            ro.setDuration(1000);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ro);
            animatorSet.start();
        }else {
            @SuppressLint("Recycle") ObjectAnimator ro=ObjectAnimator.ofFloat(imageView,"rotation",0f,0f);
            ro.setDuration(1000);

            AnimatorSet animatorSet=new AnimatorSet();
            animatorSet.playTogether(ro);
            animatorSet.start();
        }
    }
//    private void load_recyclerview_type(ArrayList<type> typeArrayList_save) {
//        recyclerviewadapter_savetype adapte1 = new recyclerviewadapter_savetype(getActivity(), typeArrayList_save);
//        recyclerViewtype.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerViewtype.setAdapter(adapte1);
//    }



    private void loaditem() {
        recyclerviewadapter_saveitem adapte = new recyclerviewadapter_saveitem(getActivity(), itemArrayList_save);
        recyclerViewitem.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewitem.setAdapter(adapte);
    }

    //****************************************************************************************************************  api





    private void registeritem(String getid) {
        class Registeritem extends AsyncTask<Void, Void, String> {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("gettypeid", String.valueOf(getid));

                //returing the response
                return requestHandler.sendPostRequest(config.itemsave, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                itemArrayList_save.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("error")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("item");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String isid = jsonObject1.getString("isid");
                            String isname = jsonObject1.getString("isname");
                            String ispicture = jsonObject1.getString("ispicture");
                            String isexp = jsonObject1.getString("isexp");
                            String istypeid = jsonObject1.getString("istypeid");

                            item item = new item(isid, isname, ispicture, isexp, istypeid);
                            itemArrayList_save.add(item);
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message") + "..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                loaditem();
            }
        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();
    }
}

