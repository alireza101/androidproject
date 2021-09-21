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

public class webapiandler {
    Context context;
    String apilink = "";
    ArrayList<item> itemarraylist = new ArrayList();

    public webapiandler(Context context) {
        this.context = context;
    }

    void apiconnect(String type) {
        switch (type) {
            case "selectpic": {
                apilink = config.selectpic_api;
                break;
            }
        }
        ProgressDialog progressDialog = ProgressDialog.show(context, "connecting", "please wait", false, false);
        StringRequest request = new StringRequest(Request.Method.POST, apilink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showjson(response);
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

    private void showjson(String response) {
        itemarraylist.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject1 = jsonArray.getJSONObject(i);
                String itemid = jsonobject1.getString("itemid");
                String itempicture = jsonobject1.getString("itempicture");
                String itemname = jsonobject1.getString("itemname");
                String itemtype = jsonobject1.getString("itemtype");
                String itemtime = jsonobject1.getString("itemtime");
                String itemexpiration = jsonobject1.getString("itemexpiration");
                String itemsum = jsonobject1.getString("itemsum");
                item item=new item(itemid,itempicture,itemname,itemtype,itemtime,itemexpiration,itemsum);
                itemarraylist.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
