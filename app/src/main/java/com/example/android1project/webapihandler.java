package com.example.android1project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class webapihandler {
    Context context;
    String apilink = "";
    ArrayList<item> itemArrayList = new ArrayList<>();
    ArrayList<type>typeArrayList=new ArrayList<>();

    public webapihandler(Context context) {
        this.context = context;
    }

    void apiconecct(String type) {
        switch (type) {
            case "register": {
                apilink = config.selectitem_api;
                break;
            }
            case "itemtype":{
                apilink=config.selecttype;
            }


        }
        ProgressDialog progressDialog = ProgressDialog.show(context, "connecting...", "please wait", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, apilink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (type.equals("register")) {
                    showJeson(response);
                }
                if (type.equals("itemtype")) {
                    showJesontype(response);
                }

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private void showJesontype(String response) {
        typeArrayList.clear();

            try {
                JSONObject jsonObjecttype=new JSONObject(response);
                JSONArray jsonArraytype=jsonObjecttype.getJSONArray("itemtype");
                for (int i=0;i<jsonArraytype.length();i++){
                    JSONObject jsonObjecttype1=jsonArraytype.getJSONObject(i);
                    String typeid=jsonObjecttype1.getString("typeid");
                    String typename=jsonObjecttype1.getString("typename");
                    String typepicture=jsonObjecttype1.getString("typepicture");
                    type type=new type(typeid,typename,typepicture);
                    typeArrayList.add(type);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mainapp.typeArrayList=typeArrayList;
            IData iData= (IData) context;
            iData.sendata();

    }

    private void showJeson(String response) {
        itemArrayList.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String itemid = jsonObject1.getString("itemid");
                String itempicture = jsonObject1.getString("itempicture");
                String itemname = jsonObject1.getString("itemname");
                String itemtype = jsonObject1.getString("itemtype");
                String itemremaining = jsonObject1.getString("itemremaining");
                String itemexpiration = jsonObject1.getString("itemexpiration");
                String itemsum = jsonObject1.getString("itemsum");
                String itemuser = jsonObject1.getString("itemuser");

                item item = new item(itemid, itempicture, itemname, itemtype, itemremaining, itemexpiration, itemsum, itemuser);
                itemArrayList.add(item);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainapp.itemArrayList = itemArrayList;
        IData iData = (IData) context;
        iData.sendata();
    }
}
