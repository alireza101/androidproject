package com.example.android1project;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

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

public class webapihandler {
    Context context;
    String apilink = "";
    ArrayList<item> itemArrayList = new ArrayList<>();

    public webapihandler(Context context) {
        this.context = context;
    }

    void apiconecct(String type) {
        switch (type) {
            case "register": {
                apilink = config.selectitem_api;
                break;
            }
            case "register1":{
                apilink=config.getpicture;
                break;
            }
        }
        ProgressDialog progressDialog = ProgressDialog.show(context, "connecting...", "please wait", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, apilink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJeson(response);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
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
                String itemtime = jsonObject1.getString("itemtime");
                String itemexpiration = jsonObject1.getString("itemexpiration");
                String itemsum = jsonObject1.getString("itemsum");

                item item=new item(itemid,itempicture,itemname,itemtype,itemtime,itemexpiration,itemsum);
                itemArrayList.add(item);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mainapp.itemArrayList=itemArrayList;
        IData iData= (IData) context;
        iData.sendata();
    }
}
