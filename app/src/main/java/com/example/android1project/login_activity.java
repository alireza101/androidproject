package com.example.android1project;



import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login_activity extends AppCompatActivity {
    EditText txtuser,txtpassword;
    TextView signup,login;
    Button btnlogin;
    int flaglogin=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //choice which one login or signup
        signup=findViewById(R.id.singup);
        login=findViewById(R.id.login);
        //enter txt
        txtuser=findViewById(R.id.txtusername);
        txtpassword=findViewById(R.id.txtpassword);
        //button
        btnlogin=findViewById(R.id.btnlogin);
        //visible
        txtuser.setVisibility(View.GONE);
        txtpassword.setVisibility(View.GONE);
        //click
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                signup.setBackgroundColor(Color.parseColor("#CACACA"));
                txtuser.setVisibility(View.VISIBLE);
                txtpassword.setVisibility(View.VISIBLE);
                flaglogin=1;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login.setBackgroundColor(Color.parseColor("#CACACA"));
                signup.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                txtuser.setVisibility(View.VISIBLE);
                txtpassword.setVisibility(View.VISIBLE);
                flaglogin=2;
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(login_activity.this,mainapp.class);
                startActivity(i);
                finish();
            }
        });



    }
}