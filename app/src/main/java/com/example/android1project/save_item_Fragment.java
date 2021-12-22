package com.example.android1project;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContentInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class save_item_Fragment extends Fragment {
    private ArrayList<type> typeArrayList_save = new ArrayList<>();

    private final ArrayList<item> itemArrayList_save = new ArrayList<>();
    private final ArrayList<item> itemArrayList_save_filter = new ArrayList<>();

    RecyclerView recyclerViewtype, recyclerViewitem;
    LinearLayout layout_type,layout_item;
    RelativeLayout relativeLayout;
    ImageView typeimage,itemimage,imageview, add, minus;
    TextView typename,saveitem_back,edsumn;
    Dialog dialog;
    Button btnclose, btnadd;
    EditText edM, edD, edname, edsum,edsearch;
    boolean fsum=false;
    RadioGroup radioGroup;
    RadioButton radioButton;

    String name,em,ed,sum,grading;
    int calorie;

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
        relativeLayout=view.findViewById(R.id.saveitem_card);

        typeimage=view.findViewById(R.id.saveitem_image);
        typename=view.findViewById(R.id.saveitem_name);

        saveitem_back=view.findViewById(R.id.saveitem_back);
        edsearch=view.findViewById(R.id.saveitem_search);
        Registeritem();

        //paramet
        typeArrayList_save.clear();
        for (int i = 0; i < mainapp.typeArrayList.size(); i++) {
            type type = mainapp.typeArrayList.get(i);
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
                itemArrayList_save_filter.clear();
                type type = typeArrayList_save.get(position);
                typename.setText(type.getTypename());
                Picasso.get().load(type.getTypepicture1()).into(typeimage);
                layout_type.setVisibility(View.GONE);
                layout_item.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);


                for (item item:itemArrayList_save){
                    if (item.getItemtype().equals(type.getTypeid())){
                        itemArrayList_save_filter.add(item);
                    }
                }
                recyclerviewadapter_saveitem adapte = new recyclerviewadapter_saveitem(getActivity(), itemArrayList_save_filter);
                recyclerViewitem.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewitem.setAdapter(adapte);
                typeimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_type.setVisibility(View.VISIBLE);
                        layout_item.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);

                    }
                });





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
        recyclerviewadapter_saveitem adapte = new recyclerviewadapter_saveitem(getActivity(), itemArrayList_save_filter);
        recyclerViewitem.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewitem.setAdapter(adapte);

        recyclerViewitem.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewitem, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                item item=itemArrayList_save_filter.get(position);
                itemimage=view.findViewById(R.id.saveitem_showitem);
                itemimage.setOnClickListener(view1 -> {
                    roateImage(true,itemimage);
                    String []a={item.getItemname(),item.getItempicture(),item.getItemexpiration(),item.getItemcalorie()
                            ,item.getItemsnname(),item.getItemsum(),item.getItemtype()};

                    showdialog(a);
                    roateImage(false, itemimage);

                });


            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        saveitem_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getActivity(),mainapp.class));

            }
        });
        edsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text=editable.toString().trim().toLowerCase();
                if (TextUtils.isEmpty(text)){
                    layout_type.setVisibility(View.VISIBLE);
                    layout_item.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                }else {
                    layout_type.setVisibility(View.GONE);
                    layout_item.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    search(text);
                }
            }
        });

        return view;
    }

    private void search(String text) {
        itemArrayList_save_filter.clear();
        for (item item:itemArrayList_save){
            if (item.getItemname().toLowerCase().contains(text)){
                itemArrayList_save_filter.add(item);
            }
            recyclerviewadapter_saveitem adapter_item = new recyclerviewadapter_saveitem(getActivity(),itemArrayList_save_filter);
            recyclerViewitem.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewitem.setAdapter(adapter_item);
        }
    }


    //****************************************************************************************************************  method

//    private void loadfragment(Fragment fragment) {
//
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame_layout_profile, fragment)
//                .addToBackStack(null)
//                .commit();
//    }

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

    private void showdialog(String[] a) {

        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.alert_dialog_additem);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window=dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations=R.style.DialogAnimation;

        btnclose = dialog.findViewById(R.id.additem_close);
        btnadd = dialog.findViewById(R.id.additem_add);
        edname = dialog.findViewById(R.id.additem_name);
        imageview = dialog.findViewById(R.id.additem_image);
        edM = dialog.findViewById(R.id.additem_M);
        edD = dialog.findViewById(R.id.additem_D);
        edsum = dialog.findViewById(R.id.additem_sum);
        add = dialog.findViewById(R.id.additem_sum_add);
        minus = dialog.findViewById(R.id.additem_sum_minus);
        edsumn=dialog.findViewById(R.id.additem_sum_name);
        radioGroup=dialog.findViewById(R.id.additem_grading);


        calorie=Integer.parseInt(a[3]);
        edname.setText(a[0]);
        Picasso.get().load(a[1]).into(imageview);
        float day = Float.parseFloat(a[2]);
        edM.setText(String.valueOf((int) (day / 30.415)));
        edD.setText(String.valueOf((int) (Math.round(day % 30.415))));
        if (a[4].equals("g")){
            fsum=false;
        }else {
            fsum=true;
        }
        edsum.setText("100");
        edsumn.setText(a[4]);
        grading="Household";


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                radioButton=radioGroup.findViewById(i);
                float s= Float.parseFloat(a[2]);
                if (radioButton.getText().equals("Household")){
                    s=s-(s/15);
                    edM.setText(String.valueOf((int) (s / 30.415)));
                    edD.setText(String.valueOf((int) (Math.round(s % 30.415))));
                    a[3]= String.valueOf(calorie-(calorie/15));
                    grading="Household";

                }else {
                    edM.setText(String.valueOf((int) (s / 30.415)));
                    edD.setText(String.valueOf((int) (Math.round(s % 30.415))));
                    a[3]= String.valueOf(calorie+(calorie/15));
                    grading="industrial";
                }
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    getActivity().getSupportFragmentManager().popBackStack();
//
//                }
                dialog.dismiss();
            }
        });



        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name =edname.getText().toString();
                em   =edM.getText().toString();
                ed   =edD.getText().toString();
                sum  =edsum.getText().toString();



                if (TextUtils.isEmpty(name)){
                    edname.setError("Please enter the name");
                    edname.requestFocus();
                    return;
                }if (TextUtils.isEmpty(em)){
                    edM.setError("Please enter the month exp");
                    edM.requestFocus();
                    return;
                }if (TextUtils.isEmpty(ed)){
                    edD.setError("Please enter the day exp");
                    edD.requestFocus();
                    return;
                }if (TextUtils.isEmpty(sum)){
                    edsum.setError("Please enter the sum");
                    edsum.requestFocus();
                    return;
                }

                int m = Integer.parseInt(em);
                int d = Integer.parseInt(ed);


                int exp = (int) ((m * 30.415) + (d));

                String[] b = {name,a[1],String.valueOf(exp),a[3],a[4],sum,a[6],grading};
                registeradditem(b);

                user user= SharedPrefManager_user.getInstance(getActivity()).getUser();
                item item=new item("",name,a[1],String.valueOf(exp),a[3],a[4]
                        ,sum,a[6],grading,String.valueOf(user.getId()),"0");
                homeFragment.itemArrayList_filter.add(item);
                homeFragment.adapter.notifyDataSetChanged();
                dialog.dismiss();
                costFragment.costarray=SharedPrefManeger_item.getInstance(getActivity()).getArrayList_cost("cost");
                costFragment.costarray.add(new cost(item.getItemname(),item.getItemcost(),item.getItemid(),String.valueOf(Calendar.getInstance().getTimeInMillis())));
                SharedPrefManeger_item.getInstance(getActivity()).saveArrayList_cost(costFragment.costarray,"cost");


            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(edsum.getText().toString());

                if (sum > 199) {
                    sum=sum-100;
                } else {
                    if(!fsum){
                        edsum.setError("Not less than 100 g");
                        edsum.requestFocus();
                    }else {
                        edsum.setError("Not less than 100 ml");
                        edsum.requestFocus();
                    }
                }
                edsum.setText(sum + "");

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(edsum.getText().toString());
                sum=sum+100;
                edsum.setText(sum + "");

            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }


    //****************************************************************************************************************  api


     private void registeradditem(String[] a) {
        user user = SharedPrefManager_user.getInstance(getActivity()).getUser();
        class Registeritem extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("itempicture", a[1]);
                params.put("itemname", a[0]);
                params.put("itemtype", a[6]);
                params.put("itemexpiration", a[2]);
                params.put("itemsum", a[5]);
                params.put("itemcalorie", a[3]);
                params.put("itemsumn", a[4]);
                params.put("itemgrading", a[7]);
                params.put("itemuser", String.valueOf(user.getId()));
                params.put("itemcost", "0");

                //returing the response
                return requestHandler.sendPostRequest(config.additem_save, params);
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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        String id=jsonObject.getString("item");
                        item item=new item(id,a[0],a[1],a[2],a[3],a[4],a[5],a[6],a[7],String.valueOf(user.getId()),"0");
                        for (int i=0;i<homeFragment.itemArrayList.size();i++){
                            item item1=homeFragment.itemArrayList.get(i);
                            if (Integer.parseInt(item1.getItemexpiration())>Integer.parseInt(item.getItemexpiration())){
                                homeFragment.itemArrayList.add(i,item);
                                break;

                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message") + "..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            }
        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();
    }





    private void Registeritem() {
        class Registeritem extends AsyncTask<Void, Void, String> {
            user user= SharedPrefManager_user.getInstance(getActivity()).getUser();

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

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
                            String iscalorie = jsonObject1.getString("iscalorie");
                            String snname = jsonObject1.getString("issum_n");
                            String issum = jsonObject1.getString("issum");
                            String istypeid = jsonObject1.getString("istypeid");

                            item item = new
                                    item(isid, isname, ispicture, isexp,iscalorie,snname,issum,istypeid,"",String.valueOf(user.getId()),"0");
                            itemArrayList_save.add(item);
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message") + "..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();
    }
}

