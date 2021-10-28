package com.example.android1project;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class feedbackFragment extends Fragment {
RatingBar ratingBar;
Button btnsend;
EditText edtext;
TextView btnback;

Dialog dialog;
boolean f=false;
    public feedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feedback, container, false);
        ratingBar=view.findViewById(R.id.feedback_rating);
        btnsend=view.findViewById(R.id.feedback_send);
        btnback=view.findViewById(R.id.feedback_back);
        edtext=view.findViewById(R.id.feedback_text);
        String mailto="mohsenbarat007@gmail.com";



        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rat = String.valueOf(ratingBar.getRating());
                String text=edtext.getText().toString();
                Intent intent=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("mailto:"+mailto));
                intent.putExtra(Intent.EXTRA_SUBJECT,"feedback app");
                intent.putExtra(Intent.EXTRA_TEXT,text+"\n rate to your app is "+rat);
                startActivity(intent);
                f=true;
                edtext.setText(null);
                ratingBar.setRating(1);
                showdialog();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void showdialog() {
        Button btnback;
        dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window=dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations=R.style.DialogAnimation;

        btnback=dialog.findViewById(R.id.dialog_button);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getActivity().finish();
            }
        });

        dialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
}