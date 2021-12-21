package com.example.android1project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
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
    int idint=100;
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

        try {
            String d=SharePrefManager_string.getInstance(context).getString("Date");
            Date dateold = format.parse(d);

            String datenew=format.format(Calendar.getInstance().getTime());
            def= (int) getDateDiff.getDateDiff(dateold,format.parse(datenew));

        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startmyservice(Context context, int def) {
        //AEDR mean of Automatic expiration date reduction
        Log.v(LOG_TAG,"***************************");
        Log.v(LOG_TAG,"run");
        for (item item:itemArrayList_increment){
            int exp= Integer.parseInt(item.getItemexpiration());
            exp-=def;
            item.setItemexpiration(String.valueOf(exp));
            if (exp>0){

                updata_item(item);
            }else if (exp==0){
                if (run_service.flag_notification==1){
                    addNotification(item.getItemname()," expiration date is coming to an end");

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
                updata_item(item);
            }else{
                addNotification(item.getItemname()," expiration date is finish");
                deleteitem(item.getItemid());
            }
            SharePrefManager_string.getInstance(context).saveString("Date", Calendar.getInstance().getTime().toString());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNotification(String itemname, String s) {
        Intent intent = new Intent(context, mainapp.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= 26)
        {
            //When sdk version is larger than26
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
//                     channel.enableLights(true);
//                     channel.enableVibration(true);//
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(context, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("expire")
                    .setContentText(itemname+s)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .build();
            manager.notify(idint++, notification);
        }
        else
        {
            //When sdk version is less than26
            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle("expire")
                    .setContentText(itemname+s)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .build();
            manager.notify(idint++,notification);
        }
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

            @RequiresApi(api = Build.VERSION_CODES.O)
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
                        String itemcost = jsonObject1.getString("itemcost");

                        item item = new item(itemid, itemname, itempicture, itemexpiration, itemcalorie
                                , itemsumn, itemsum, itemtype, itemgrading,String.valueOf(user.getId()),itemcost);
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