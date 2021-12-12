package com.example.android1project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

public class check_internet_broadcast extends BroadcastReceiver {
     ArrayList<item> itemArrayList_increment = new ArrayList<>();

    private static final String LOG_TAG = "CheckNetworkStatus";
    private boolean isConnected = false;
    int def=0;
    int id=100;
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(LOG_TAG,"onrecive");
        this.context=context;
        isNetworkAvailable(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void extracted() {
        SimpleDateFormat format=new SimpleDateFormat("yy/M/d");

//        try {
//            String d=SharePrefManager_string.getInstance(context).getString("Date");
//            Date dateold = format.parse(d);
//
//            String datenew=format.format(Calendar.getInstance().getTime());
//            def= (int) getDateDiff.getDateDiff(dateold,format.parse(datenew));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        def=1;
        if (def>0) {
            if (isConnected){
                registeritem(context);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
                            isConnected = true;
                            extracted();
                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!");
        isConnected = false;

        return false;
    }

    private void startmyservice(Context context, int def) {
        //AEDR mean of Automatic expiration date reduction
        Log.v(LOG_TAG,"***************************");
        Log.v(LOG_TAG,"run");
        addNotification();
        for (item item:itemArrayList_increment){
            int exp= Integer.parseInt(item.getItemexpiration());
            if (exp>0){
                item.setItemexpiration(String.valueOf(exp-def));

                updata_item(item);
            }else if (exp==0){
                if (run_service.flag_notification==1){

//                    String CHANNEL_ID="MYCHANNEL";
//                    Intent intent = new Intent(context, mainapp.class);
//                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                    NotificationCompat.Builder b = new NotificationCompat.Builder(context);
//
//                    b.setAutoCancel(true)
//                            .setDefaults(Notification.DEFAULT_ALL)
//                            .setWhen(System.currentTimeMillis())
//                            .setSmallIcon(R.drawable.minus)
//                            .setTicker("Hearty365")
//                            .setContentTitle("Default notification")
//                            .setContentText(item.getItemname()+" expiration date is coming to an end")
//                            .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
//                            .setContentIntent(contentIntent)
//                            .setChannelId(CHANNEL_ID)
//                            .setContentInfo("Info");
//
//
//                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(id, b.build());
//                    Notification notification =new NotificationCompat.Builder(context)
//                            .setSmallIcon(R.drawable.minus)
//                            .setContentTitle(item.getItemname()+" expiration date is coming to an end")
//                            .setPriority(NotificationManager.IMPORTANCE_LOW)
//                            .setCategory(Notification.CATEGORY_SERVICE)
//                            .setChannelId("110")
//                            .build();
//
//                    NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
//                    mNotificationManager.notify(++id, notification);
                }
                item.setItemexpiration(String.valueOf(exp-def));
                updata_item(item);
            }else{
                deleteitem(item.getItemid());
            }
            SharePrefManager_string.getInstance(context).saveString("Date", Calendar.getInstance().getTime().toString());
        }
        this.def=0;
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.minus) //set icon for notification
                        .setContentTitle("Notifications Example") //set title of notification
                        .setContentText("This is a notification message")//this is notification message
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification




        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    private void registeritem(Context context) {
        Log.v("register","run");
        user user = SharedPrefManager_user.getInstance(context).getUser();
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
                itemArrayList_increment.clear();
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

                        item item = new item(itemid, itemname, itempicture, itemexpiration, itemcalorie, itemsumn, itemsum, itemtype, itemgrading,String.valueOf(user.getId()));
                        itemArrayList_increment.add(item);

                    }
                    if (itemArrayList_increment.size()>0) {
                        startmyservice(context,def);
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
    private void updata_item(item item) {
        class update_item extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("itemid", item.getItemid());
                params.put("itemsum", item.getItemsum());
                params.put("itemcalorie", item.getItemcalorie());
                params.put("itemexpiration", item.getItemexpiration());
                return requestHandler.sendPostRequest(config.update_item, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        update_item update_item = new update_item();
        update_item.execute();
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        deleteitem deleteitem = new deleteitem();
        deleteitem.execute();
    }
}