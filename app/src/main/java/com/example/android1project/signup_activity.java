package com.example.android1project;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class signup_activity extends AppCompatActivity  {
    Fragment fragment = null;
    private static final String LOG_TAG = "signup_CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    RelativeLayout main1,main2;
    TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        main1=findViewById(R.id.sign_main1);
        main2=findViewById(R.id.sign_main2);
        main1.setVisibility(View.GONE);
        main2.setVisibility(View.VISIBLE);
        view=findViewById(R.id.sing_connect);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
        fragment = new login_Fragment();
        loadFragment(fragment);
    }

    private void extracted() {
        if (SharedPrefManager_user.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, mainapp.class));
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.page1, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> signup_activity.this.finish()).create().show();
    }
    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "on_Destroy");
        super.onDestroy();
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {

            Log.v(LOG_TAG, "Received notification about network status");
            isNetworkAvailable(context);

        }


        private void isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for  (NetworkInfo networkInfo : info) {
                        if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                Log.v(LOG_TAG, "Now you are connected to Internet!");
                                main1.setVisibility(View.GONE);
                                main2.setVisibility(View.VISIBLE);
                                isConnected = true;
                                //do your processing here ---
                                //if you need to post any data to the server or get status
                                //update from the server
                                extracted();
                            }
                            return;
                        }
                    }
                }
            }
            Log.v(LOG_TAG, "You are not connected to Internet!");
            isConnected = false;
            main1.setVisibility(View.VISIBLE);
            main2.setVisibility(View.GONE);
            view.setText(R.string.P_connect_internet);

        }
    }
}