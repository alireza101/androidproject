package com.example.android1project;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefManeger_item {
    private static final String SHARED_PREF_NAME = "sharedprefitem";

    private static SharedPrefManeger_item mItem;
    private static Context mCtx;

    private SharedPrefManeger_item( Context context){

        mCtx =context;
    }

    public static synchronized SharedPrefManeger_item getInstance(Context context){
        if (mItem == null) {
            mItem = new SharedPrefManeger_item( context);
        }
        return mItem;
    }

    public  void saveArrayList(ArrayList<item> list, String key){

        SharedPreferences prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }
    public ArrayList<item> getArrayList(String key){
        SharedPreferences prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<item>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
