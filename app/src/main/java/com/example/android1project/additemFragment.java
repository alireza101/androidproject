package com.example.android1project;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class additemFragment extends Fragment {
    Button btnclose, btnadd;
    EditText edM, edD, edname, edsum;
    ImageView imageview, add, minus;
    TextView edsumn;
    boolean fsum=false;
    RadioGroup radioGroup;
    RadioButton radioButton;

    String name,em,ed,sum,grading;
    int calorie;
    public additemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_additem, container, false);


        btnclose = view.findViewById(R.id.additem_close);
        btnadd = view.findViewById(R.id.additem_add);
        edname = view.findViewById(R.id.additem_name);
        imageview = view.findViewById(R.id.additem_image);
        edM = view.findViewById(R.id.additem_M);
        edD = view.findViewById(R.id.additem_D);
        edsum = view.findViewById(R.id.additem_sum);
        add = view.findViewById(R.id.additem_sum_add);
        minus = view.findViewById(R.id.additem_sum_minus);
        edsumn=view.findViewById(R.id.additem_sum_name);
        radioGroup=view.findViewById(R.id.additem_grading);





        String[] a = getArguments().getStringArray("dataitempnet");
        calorie=Integer.parseInt(a[3]);
        edname.setText(a[0]);
        Picasso.get().load(a[1]).into(imageview);
        float day = Float.parseFloat(a[2]);
        edM.setText(String.valueOf((int) (day / 30.415)));
        edD.setText(String.valueOf((int) (Math.round(day % 30.415))));
        if (a[4].equals("g")){
            fsum=false;
        }else {
            fsum=true;
        }
        edsum.setText("100");
        edsumn.setText(a[4]);
        grading="Household";





        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                radioButton=getView().findViewById(i);
                float s= Float.parseFloat(a[2]);
                if (radioButton.getText().equals("Household")){
                    s=s-(s/15);
                    edM.setText(String.valueOf((int) (s / 30.415)));
                    edD.setText(String.valueOf((int) (Math.round(s % 30.415))));
                    a[3]= String.valueOf(calorie-(calorie/15));
                    grading="Household";

                }else {
                    edM.setText(String.valueOf((int) (s / 30.415)));
                    edD.setText(String.valueOf((int) (Math.round(s % 30.415))));
                    a[3]= String.valueOf(calorie+(calorie/15));
                    grading="industrial";
                }
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name =edname.getText().toString();
                em   =edM.getText().toString();
                ed   =edD.getText().toString();
                sum  =edsum.getText().toString();

                int m = Integer.parseInt(em);
                int d = Integer.parseInt(ed);

                if (TextUtils.isEmpty(name)){
                    edname.setError("Please enter the name");
                    edname.requestFocus();
                    return;
                }if (TextUtils.isEmpty(em)){
                    edM.setError("Please enter the month exp");
                    edM.requestFocus();
                    return;
                }if (TextUtils.isEmpty(ed)){
                    edD.setError("Please enter the day exp");
                    edD.requestFocus();
                    return;
                }if (TextUtils.isEmpty(sum)){
                    edsum.setError("Please enter the sum");
                    edsum.requestFocus();
                    return;
                }




                int exp = (int) ((m * 30.415) + (d));

                String[] b = {name,a[1],String.valueOf(exp),a[3],a[4],sum,a[6],grading};
                registeradditem(b);

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(edsum.getText().toString());

                if (sum > 100) {
                    sum=sum-100;
                } else {
                    if(!fsum){
                        edsum.setError("Not less than 100 g");
                        edsum.requestFocus();
                    }else {
                        edsum.setError("Not less than 100 ml");
                        edsum.requestFocus();
                    }
                }
                edsum.setText(sum + "");

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(edsum.getText().toString());
                sum=sum+100;
                edsum.setText(sum + "");

            }
        });

        return view;
    }
/********************************************************************************************************************************/
    private void registeradditem(String[] a) {
        user user = SharedPrefManager.getInstance(getActivity()).getUser();
        class Registeritem extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("itempicture", a[1]);
                params.put("itemname", a[0]);
                params.put("itemtype", a[6]);
                params.put("itemexpiration", a[2]);
                params.put("itemsum", a[5]);
                params.put("itemcalorie", a[3]);
                params.put("itemsumn", a[4]);
                params.put("itemgrading", a[7]);
                params.put("itemuser", String.valueOf(user.getId()));

                //returing the response
                return requestHandler.sendPostRequest(config.additem_save, params);
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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message") + "..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            }
        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();
    }

}