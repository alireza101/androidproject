package com.example.android1project;

import java.util.Calendar;

public class getCalculatedDate {
    public static long getCalculatedDatee(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTimeInMillis();
    }
}
