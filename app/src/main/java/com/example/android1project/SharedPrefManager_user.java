package com.example.android1project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager_user {
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_userEMAIL = "keyuseremail";
    private static final String KEY_userID = "keyuserid";
    private static final String KEY_userpassword = "keyuserpassword";
    private static final String KEY_usergender = "keyusergender";

    private static SharedPrefManager_user mInstance;
    private static Context mCtx;

    private SharedPrefManager_user(Context context ) {

        mCtx=context;
    }
    public static synchronized SharedPrefManager_user getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager_user(context);
        }
        return mInstance;
    }
    public void userLogin(user user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_userID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_userEMAIL, user.getUsermail());
        editor.putString(KEY_userpassword, user.getUserpassword());
        if (user.getUsergender() == null){
            editor.putString(KEY_usergender, null);
        }else {
            editor.putString(KEY_usergender, user.getUsergender());
        }
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public user getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new user(
                sharedPreferences.getInt(KEY_userID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_userEMAIL, null),
                sharedPreferences.getString(KEY_userpassword, null),
                sharedPreferences.getString(KEY_usergender, null)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        mCtx.startActivity(new Intent(mCtx, signup_activity.class));

    }
}
