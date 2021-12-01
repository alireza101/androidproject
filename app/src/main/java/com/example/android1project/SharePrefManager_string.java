package com.example.android1project;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SharePrefManager_string {
    private static final String SHARED_PREF_NAME = "sharedprefstring";

    private static SharePrefManager_string mItem;
    private static Context mCtx;

    private SharePrefManager_string(Context context) {
        mCtx=context;
    }
    public static synchronized SharePrefManager_string getInstance(Context context){
        if (mItem == null) {
            mItem = new SharePrefManager_string( context);
        }
        return mItem;
    }

    public  void saveString( String key,String s){

        SharedPreferences prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, s);
        editor.apply();
    }
    public  String getString( String key){

        SharedPreferences prefs = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(key,"");
    }
}
