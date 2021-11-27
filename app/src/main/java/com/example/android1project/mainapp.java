package com.example.android1project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Observable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class mainapp extends AppCompatActivity {
    static ArrayList<item> itemArrayList = new ArrayList<>();

    static ArrayList<type> typeArrayList = new ArrayList<>();

    Fragment fragment = null;
    MeowBottomNavigation bottomNavigation;
    FloatingActionButton fab_item, fab_custom, fab_history;
    FloatingActionsMenu fab;
    RelativeLayout main_2;
    LinearLayout main_1;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainapp);
        registertype();
        registeritem();
        fab_custom = findViewById(R.id.add_fam_customitem);
        fab_item = findViewById(R.id.add_fam_additem);
        fab_history = findViewById(R.id.add_fam_historyitem);
        fab = findViewById(R.id.add_fam);

        main_2 = findViewById(R.id.main_2);
        main_1 = findViewById(R.id.main_1);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_profie));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_add_circle));

        intent = new Intent(getApplication(), profile.class);

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:

                        fragment = new profileFragment();
                        loadFragment(fragment);
                        fab.collapse();
                        break;
                    case 3:
                        main_2.setVisibility(View.VISIBLE);
                        main_1.setVisibility(View.GONE);
                        fab.collapse();
                        break;
                    case 2:

                        fragment = new homeFragment();
                        loadFragment(fragment);
                        fab.collapse();
                        break;

                }


            }
        });


        fab_item.setOnClickListener(view1 -> {
            intent.putExtra("profileitem", 4);
            startActivity(intent);
            fab.collapse();

        });
        fab_history.setOnClickListener(view1 -> {
            intent.putExtra("profileitem", 6);
            startActivity(intent);
            fab.collapse();

        });
        fab_custom.setOnClickListener(view1 -> {
            intent.putExtra("profileitem", 5);
            startActivity(intent);
            fab.collapse();

        });

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, signup_activity.class));
        }


        bottomNavigation.show(2, true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "you click "+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "you reselect "+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadFragment(Fragment fragment) {
        main_2.setVisibility(View.GONE);
        main_1.setVisibility(View.VISIBLE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                }).create().show();
    }
    private void registeritem() {
        user user = SharedPrefManager.getInstance(getApplication()).getUser();
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
                itemArrayList.clear();
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
                        itemArrayList.add(item);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplication(), "load item", Toast.LENGTH_SHORT).show();

//                progressDialog.dismiss();
//                recyclerviewadapter_ver adapter = new recyclerviewadapter_ver(getActivity(), itemArrayList);
//                rcmain.setLayoutManager(new LinearLayoutManager(getActivity()));
//                rcmain.setAdapter(adapter);

            }


        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();

    }

    //***********************************************************************************************************************************************
    private void registertype() {


        //if it passes all the validations

        class Registertype extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();


                //returing the response
                return requestHandler.sendPostRequest(config.selecttype, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                typeArrayList.clear();

                try {
                    JSONObject jsonObjecttype = new JSONObject(s);
                    JSONArray jsonArraytype = jsonObjecttype.getJSONArray("type");
                    for (int i = 0; i < jsonArraytype.length(); i++) {
                        JSONObject jsonObjecttype1 = jsonArraytype.getJSONObject(i);
                        String typeid = jsonObjecttype1.getString("typeid");
                        String typename = jsonObjecttype1.getString("typename");
                        String typepicture = jsonObjecttype1.getString("typepicture");
                        String typepicture1 = jsonObjecttype1.getString("typepicture1");
                        type type = new type(typeid, typename, typepicture, typepicture1);
                        typeArrayList.add(type);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                progressDialog.dismiss();
////                IData iData = (IData) getActivity();
////                iData.sendata();
//                recyclerviewadapter_hor adapte1 = new recyclerviewadapter_hor(getActivity(), typeArrayList);
//                rcmainhor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//                rcmainhor.setAdapter(adapte1);
            }
        }
        //executing the async task
        Registertype ru = new Registertype();
        ru.execute();
    }
    //    @Override
//    public void sendata() {
//        fragment = new homeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("loaddata", false);
//        fragment.setArguments(bundle);
//
//        loadFragment(fragment);
//    }
}
