package com.example.android1project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class detail_item extends AppCompatActivity {
    ImageView imageView, delete_item;
    TextView textname, texttype, textkalery, textsum, textexp, textenargy;
    Button btn_backdetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        imageView = findViewById(R.id.detail_picture);
        textname = findViewById(R.id.detail_name);
        texttype = findViewById(R.id.detail_type);
        textkalery = findViewById(R.id.detail_kalery);
        textsum = findViewById(R.id.detail_sum);
        textexp = findViewById(R.id.detail_exp);
        textenargy = findViewById(R.id.detail_enargy);
        btn_backdetail = findViewById(R.id.btnback_detail);
        delete_item = findViewById(R.id.delete_item);

        Intent intent = getIntent();
        String[] getdata = intent.getStringArrayExtra("item");
        String textid = getdata[0];
        textname.setText(getdata[1]);
        textkalery.setText("0 KG");
        textsum.setText(getdata[3]);
        textexp.setText(getdata[5]);
        textenargy.setText("0 %");
        Picasso.get().load(getdata[2]).into(imageView);

        for (type type:homeFragment.typeArrayList){
            if (type.getTypeid().equals(getdata[4])){
                texttype.setText(type.getTypename());
            }
        }
        btn_backdetail.setOnClickListener(view -> {
            startActivity(new Intent(detail_item.this, mainapp.class));
            finish();
        });

        delete_item.setOnClickListener(view -> {
            deleteitem(textid);

        });


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
                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), mainapp.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        deleteitem deleteitem = new deleteitem();
        deleteitem.execute();
    }
}