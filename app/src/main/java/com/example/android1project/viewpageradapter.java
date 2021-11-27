package com.example.android1project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

public class viewpageradapter extends PagerAdapter {

    Context context;
    int images[] = {
            R.raw.refrigerator,
            R.raw.kitchen,
            R.raw.dateclock
//            "https://res.cloudinary.com/dlgnk4lmq/image/upload/v1631765973/android1pro/firstactivity/page1.jpg",
//            "https://res.cloudinary.com/dlgnk4lmq/image/upload/v1631765973/android1pro/firstactivity/page2.jpg",
//            "https://res.cloudinary.com/dlgnk4lmq/image/upload/v1631765973/android1pro/firstactivity/page3.jpg"
    };

    int headings[] = {
            R.string.heading_page1,
            R.string.heading_page2,
            R.string.heading_page3

    };

    int description[] = {
            R.string.page1,
            R.string.page2,
            R.string.page3
    };

    public viewpageradapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slider_layout,container,false);
        LottieAnimationView slidetitleimage=(LottieAnimationView) view.findViewById(R.id.titleimage);
        TextView slideheading=(TextView) view.findViewById(R.id.texttitle);
        TextView slidedescription=(TextView) view.findViewById(R.id.textdeccription);

//        Picasso.get().load(images[position]).into(slidetitleimage);
        slidetitleimage.setAnimation(images[position]);

        slideheading.setText(headings[position]);
        slidedescription.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
