package com.example.android1project;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class webapihandler {
    Context context;
    String apilink="";
    public webapihandler(Context context) {
        this.context = context;
    }
    void apiconecct(String type){
        switch (type){
            case "register":{
                apilink=config.selectitem_api;
                break;
            }
        }
        StringRequest request=new StringRequest(Request.Method.POST, apilink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
