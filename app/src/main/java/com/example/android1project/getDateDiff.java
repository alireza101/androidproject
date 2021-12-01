package com.example.android1project;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class getDateDiff {
    public static long getDateDiff( String oldDate, String newDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
