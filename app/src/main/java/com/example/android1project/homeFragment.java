package com.example.android1project;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


public class homeFragment extends Fragment {
    static ArrayList<item> itemArrayList = new ArrayList<>();
    static ArrayList<item> itemArrayList_filter = new ArrayList<>();
   static recyclerviewadapter_ver adapter;
//    static ArrayList<type> typeArrayList = new ArrayList<>();

    RecyclerView rcmain, rcmainhor;
    LottieAnimationView animationView;
    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        animationView=view.findViewById(R.id.home_anime);
        //        Bundle bundle=getArguments();
//        boolean loaddata=true;
//        if(bundle!=null){
//            loaddata=bundle.getBoolean("loaddata");
//        }
//        if (loaddata || bundle==null) {
//            registeritem();
//            registertype();
//        }

//        registertype();
        if (itemArrayList.size()==0){
            animationView.setVisibility(View.VISIBLE);
        }else {
            animationView.setVisibility(View.GONE);
        }
        registeritem();
        itemArrayList_filter.clear();
        itemArrayList_filter.addAll(itemArrayList);
        rcmain = view.findViewById(R.id.recyclerviewver);
        rcmainhor = view.findViewById(R.id.recyclerview);

        recyclerviewadapter_hor adapte1 = new recyclerviewadapter_hor(getActivity(), mainapp.typeArrayList);
        rcmainhor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rcmainhor.setAdapter(adapte1);

//        while (adapte1.getItemCount()==0){
//            adapte1 = new recyclerviewadapter_hor(getActivity(), mainapp.typeArrayList);
//            rcmainhor.setAdapter(adapte1);
//            rcmain.setAdapter(adapter);
//
//        }
//        rcmain.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rcmain, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), detail_item.class);
//                item item;
//                if (flagfilter) {
//                    item = itemArrayListfilter.get(position);
//                } else {
//                    item = itemArrayList.get(position);
//                }
//                String getidname = "";
//                for (type type : typeArrayList) {
//                    if (item.getItemtype().equals(type.getTypeid())) {
//                        getidname = type.getTypename();
//                        break;
//                    }
//                }
//                String[] putitem = new String[]{item.getItemid(), item.getItemname(), item.getItempicture(), item.getItemsum()
//                        , getidname, item.getItemexpiration()};
//                intent.putExtra("item", putitem);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        rcmainhor.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rcmainhor, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //filter or which one type in itemarraylistfilter

                itemArrayList_filter.clear();

                type type = mainapp.typeArrayList.get(position);

                if (type.getTypename().equals("All")){
                    itemArrayList_filter.addAll(itemArrayList);
                }else {
                    for (item item : itemArrayList) {

                        if (item.getItemtype().equals(type.getTypeid())) {
                            itemArrayList_filter.add(item);
                        }
                    }
                }
                rcmain.setAdapter(adapter);

//                recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getActivity(), itemArrayList);
//                rcmain.setAdapter(adapter);
//                flagfilter = true;

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


//        recyclerviewadapter_hor adapte1 = new recyclerviewadapter_hor(getActivity(), typeArrayList);
//        rcmainhor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        rcmainhor.setAdapter(adapte1);
//        recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getActivity(), itemArrayList);
//        rcmain.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rcmain.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                item item;

                item = itemArrayList_filter.get(viewHolder.getAdapterPosition());
                itemArrayList_filter.remove(viewHolder.getAdapterPosition());
                for (item item1:itemArrayList){
                    if (item1.getItemid().equals(item.getItemid())){
                        itemArrayList.remove(item1);
                        break;
                    }
                }
                deleteitem(item.getItemid());
                adapter.notifyDataSetChanged();
                if (itemArrayList.size()==0){
                    animationView.setVisibility(View.VISIBLE);
                }else {
                    animationView.setVisibility(View.GONE);
                }
            }
        }).attachToRecyclerView(rcmain);


        return view;
    }


    //*******************************************************************************************************************************************************

//    private void registeritem(String typeid) {
//        user user = SharedPrefManager_user.getInstance(getActivity()).getUser();
//        int iduser = user.getId();
//
//
//        //if it passes all the validations
//
//        class Registeritem extends AsyncTask<Void, Void, String> {
//
//            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                if (Integer.parseInt(typeid) == 1) {
//                    f = true;
//                }
//                if (Integer.parseInt(typeid) > 1) {
//                    f = false;
//                }
//                if (f) {
//                    params.put("itemuser", String.valueOf(iduser));
//
//
//                    //returing the response
//                    return requestHandler.sendPostRequest(config.selectitem, params);
//                } else {
//                    params.put("itemuser", String.valueOf(iduser));
//                    params.put("itemtype", typeid);
//
//
//                    //returing the response
//                    return requestHandler.sendPostRequest(config.selectitem_type, params);
//                }
//
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                //displaying the progress bar while user registers on the server
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                //hiding the progressbar after completion
//                itemArrayList.clear();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    JSONArray jsonArray = jsonObject.getJSONArray("item");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        String itemid = jsonObject1.getString("itemid");
//                        String itempicture = jsonObject1.getString("itempicture");
//                        String itemname = jsonObject1.getString("itemname");
//                        String itemtype = jsonObject1.getString("itemtype");
//                        String itemexpiration = jsonObject1.getString("itemexpiration");
//                        String itemsum = jsonObject1.getString("itemsum");
//                        String itemcalorie = jsonObject1.getString("itemcalorie");
//                        String itemsumn = jsonObject1.getString("itemsumn");
//                        String itemgrading = jsonObject1.getString("itemgrading");
//
//                        item item = new item(itemid, itemname, itempicture, itemexpiration, itemcalorie, itemsumn, itemsum, itemtype, itemgrading,String.valueOf(user.getId()));
//                        itemArrayList.add(item);
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//                recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getActivity(), itemArrayList);
//                rcmain.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rcmain.setAdapter(adapter);
//
//            }
//
//
//        }
//        //executing the async task
//        Registeritem ru = new Registeritem();
//        ru.execute();
//
//    }
//
//    //***********************************************************************************************************************************************
//    private void registertype() {
//
//
//        //if it passes all the validations
//
//        class Registertype extends AsyncTask<Void, Void, String> {
//            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);
//
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//
//
//                //returing the response
//                return requestHandler.sendPostRequest(config.selecttype, params);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                //displaying the progress bar while user registers on the server
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//                typeArrayList.clear();
//
//                try {
//                    JSONObject jsonObjecttype = new JSONObject(s);
//                    JSONArray jsonArraytype = jsonObjecttype.getJSONArray("type");
//                    for (int i = 0; i < jsonArraytype.length(); i++) {
//                        JSONObject jsonObjecttype1 = jsonArraytype.getJSONObject(i);
//                        String typeid = jsonObjecttype1.getString("typeid");
//                        String typename = jsonObjecttype1.getString("typename");
//                        String typepicture = jsonObjecttype1.getString("typepicture");
//                        String typepicture1 = jsonObjecttype1.getString("typepicture1");
//                        type type = new type(typeid, typename, typepicture, typepicture1);
//                        typeArrayList.add(type);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
////                IData iData = (IData) getActivity();
////                iData.sendata();
//                recyclerviewadapter_hor adapte1 = new recyclerviewadapter_hor(getActivity(), typeArrayList);
//                rcmainhor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//                rcmainhor.setAdapter(adapte1);
//            }
//        }
//        //executing the async task
//        Registertype ru = new Registertype();
//        ru.execute();
//    }

    //***********************************************************************************************************************************************
    public void registeritem() {
        user user = SharedPrefManager_user.getInstance(getActivity()).getUser();
        int iduser = user.getId();


        //if it passes all the validations

        class Registeritem extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("itemuser", String.valueOf(iduser));


                //returing the response
                return requestHandler.sendPostRequest(config.selectitem, params);


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
                itemArrayList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("item");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String itemid = jsonObject1.getString("itemid");
                        String itempicture = jsonObject1.getString("itempicture");
                        String itemname = jsonObject1.getString("itemname");
                        String itemtype = jsonObject1.getString("itemtype");
                        String itemexpiration = jsonObject1.getString("itemexpiration");
                        String itemsum = jsonObject1.getString("itemsum");
                        String itemcalorie = jsonObject1.getString("itemcalorie");
                        String itemsumn = jsonObject1.getString("itemsumn");
                        String itemgrading = jsonObject1.getString("itemgrading");
                        String itemcost = jsonObject1.getString("itemcost");

                        item item = new item(itemid, itemname, itempicture, itemexpiration, itemcalorie, itemsumn, itemsum, itemtype, itemgrading,String.valueOf(user.getId()),itemcost);
                        itemArrayList.add(item);

                    }
                    adapter= new recyclerviewadapter_ver(getActivity(), itemArrayList);
                    rcmain.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rcmain.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!itemArrayList.isEmpty()) {
                    Log.d("home", "onPostExecute: load item ");
                }
//                progressDialog.dismiss();
//                recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getActivity(), itemArrayList);
//                rcmain.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rcmain.setAdapter(adapter);

            }


        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();

    }

    public void deleteitem(String id) {

        class deleteitem extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("getitemid", id);
                return requestHandler.sendPostRequest(config.delete, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        deleteitem deleteitem = new deleteitem();
        deleteitem.execute();
    }
}