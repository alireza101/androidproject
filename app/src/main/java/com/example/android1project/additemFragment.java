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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class additemFragment extends Fragment {
    Fragment fragment;
    Button btnclose, btnadd;
    EditText edM, edD, edname, edsum;
    ImageView imageview, add, minus;

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

        String[] a = getArguments().getStringArray("dataitempnet");
        edname.setText(a[1]);
        Picasso.with(getActivity()).load(a[0]).into(imageview);
        float day = Float.parseFloat(a[2]);
        edM.setText(String.valueOf((int) (day / 30.415)));
        edD.setText(String.valueOf((int) (Math.round(day % 30.415))));
        edsum.setText("1");

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
                String name=edname.getText().toString();
                String em=edM.getText().toString();
                String ed=edD.getText().toString();
                String sum=edsum.getText().toString();
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
                int m = Integer.parseInt(em);
                int d = Integer.parseInt(ed);
                int exp = (int) ((m * 30.415) + (d));
                String[] b = {a[0], name, String.valueOf(exp), sum, a[3]};
                registeradditem(b);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(edsum.getText().toString());
                if (sum > 1) {
                    sum--;
                } else {
                    edsum.setError("Not less than 1");
                    edsum.requestFocus();
                }
                edsum.setText(sum + "");

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = Integer.parseInt(edsum.getText().toString());
                sum++;
                edsum.setText(sum + "");

            }
        });

        return view;
    }

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
                params.put("itempicture", a[0]);
                params.put("itemname", a[1]);
                params.put("itemtype", a[4]);
                params.put("itemexpiration", a[2]);
                params.put("itemsum", a[3]);
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

            }
        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();
    }
    private void loadfragment(Fragment fragment) {

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_profile, fragment)

                .commit();
    }
}