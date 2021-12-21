package com.example.android1project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class costFragment extends Fragment {
    static ArrayList<cost> costarray = new ArrayList<>();

    public costFragment() {
        // Required empty public constructor
    }

    TextView tv_daily, tv_weekly, tv_monthly, tv_yearly, tv_back, tv_date;
    long daily, weekly, monthly, yearly;
    long sum_daily, sum_weekly, sum_monthly, sum_yearly = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cost, container, false);
        costarray = SharedPrefManeger_item.getInstance(getActivity()).getArrayList_cost("cost");
        tv_daily = view.findViewById(R.id.cost_daily_cost);
        tv_weekly = view.findViewById(R.id.cost_weekly_cost);
        tv_monthly = view.findViewById(R.id.cost_monthly_cost);
        tv_yearly = view.findViewById(R.id.cost_yearly_cost);
        tv_back = view.findViewById(R.id.cost_back);
        tv_date = view.findViewById(R.id.cost_date);
        Date date1 = new Date(Calendar.getInstance().getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");

        tv_date.setText("Today is:" + format.format(date1));
        daily = getCalculatedDate.getCalculatedDatee(-1);
        weekly = getCalculatedDate.getCalculatedDatee(-7);
        monthly = getCalculatedDate.getCalculatedDatee(-30);
        yearly = getCalculatedDate.getCalculatedDatee(-365);
        if (costarray != null) {
            for (cost cost : costarray) {
                if (Long.parseLong(cost.getCostdate()) > daily) {
                    sum_daily += Long.parseLong(cost.getCostcost());
                }
                if (Long.parseLong(cost.getCostdate()) > weekly) {
                    sum_weekly += Long.parseLong(cost.getCostcost());
                }
                if (Long.parseLong(cost.getCostdate()) > monthly) {
                    sum_monthly += Long.parseLong(cost.getCostcost());
                }
                if (Long.parseLong(cost.getCostdate()) > yearly) {
                    sum_yearly += Long.parseLong(cost.getCostcost());
                }
            }
        }
        tv_daily.setText("$" + sum_daily);
        tv_weekly.setText("$" + sum_weekly);
        tv_monthly.setText("$" + sum_monthly);
        tv_yearly.setText("$" + sum_yearly);
        tv_back.setOnClickListener(view1 -> getActivity().finish());

//        SimpleDateFormat format = new SimpleDateFormat("yy/M/d");
//        Calendar today = Calendar.getInstance();
//        int day = today.get(Calendar.DAY_OF_MONTH);
//        int month = today.get(Calendar.MONTH);
//        int year = today.get(Calendar.YEAR);
//        Calendar calendar = Calendar.getInstance();
//        for (cost cost : costarray) {
//
//            try {
//                Date c1 = format.parse(cost.getCostdate());
//                if (c1 != null){
//                    calendar.setTime(c1);
//                    int day1 = calendar.get(Calendar.DAY_OF_MONTH);
//                    int month1 = calendar.get(Calendar.MONTH);
//                    int year1 = calendar.get(Calendar.YEAR);
//
//                    if ()
//
//                }
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//        }


        return view;
    }
}