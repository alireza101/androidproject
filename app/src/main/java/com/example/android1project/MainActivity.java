package com.example.android1project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ViewPager msliderviewpager;
    LinearLayout mdotlayout;
    Button skipbtn,nextbtn;
    TextView[] dots;
    viewpageradapter viewpageradapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        Date c = Calendar.getInstance().getTime();
        SharePrefManager_string.getInstance(context).saveString("Date",String.valueOf(c));

        if (SharePrefManager_string.getInstance(context).getString("startac").equals("1")){
            startActivity(new Intent(this, signup_activity.class));
            finish();
        }
        skipbtn=findViewById(R.id.skipbtn);
        nextbtn=findViewById(R.id.nextbtn);

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePrefManager_string.getInstance(context).saveString("startac","1");
                Intent i =new Intent(MainActivity.this, signup_activity.class);
                startActivity(i);
                finish();
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getitem(0)<2) {
                    msliderviewpager.setCurrentItem(getitem(1), true);
                }else {
                    SharePrefManager_string.getInstance(context).saveString("startac","1");
                    Intent i =new Intent(MainActivity.this, signup_activity.class);
                    startActivity(i);
                    finish();

                }
            }
        });

        msliderviewpager= findViewById(R.id.sliderviewpager);
        mdotlayout= findViewById(R.id.indicator_layout);

        viewpageradapter =new viewpageradapter (this);
        msliderviewpager.setAdapter(viewpageradapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupindicator(0);
        }
        msliderviewpager.addOnPageChangeListener(viewlistener);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setupindicator(int position){
        dots=new TextView[3];
        mdotlayout.removeAllViews();

        for (int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.white, getApplicationContext().getTheme()));
            mdotlayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.gold,getApplicationContext().getTheme()));
    }
    ViewPager.OnPageChangeListener viewlistener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int position) {

            setupindicator(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){
        return msliderviewpager.getCurrentItem()+i;
    }
}