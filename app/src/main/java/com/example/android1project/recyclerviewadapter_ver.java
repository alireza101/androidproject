package com.example.android1project;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class recyclerviewadapter_ver  extends RecyclerView.Adapter<recyclerviewadapter_ver.myholder> {
    private ArrayList<item> mitemlist;
    private Context mitemcontext;
    int s=0;
    Dialog dialog;

    public recyclerviewadapter_ver(Context mitemcontext ,ArrayList<item> mitemexprecine ) {
        this.mitemlist = mitemexprecine;
        this.mitemcontext = mitemcontext;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mitemcontext).inflate(R.layout.layout_listitem_vertical,parent,false);
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        if (position%2==1) {
            holder.item_background.setBackgroundResource(R.color.black3);
        }
        item item=mitemlist.get(position);

        s= Integer.parseInt(item.getItemsum());
        holder.itemname.setText(item.getItemname());
        holder.itemsum.setText(item.getItemsum()+" "+item.getItemsnname());
        holder.itemexprece.setText(item.getItemexpiration()+" days");
        if (item.getItempicture().contains("/storage/emulated/0/Android/data/com.example.android1project/cache/")){
            File imgFile = new File(item.getItempicture());
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.itemimage.setImageBitmap(myBitmap);
            }
        }else if (item.getItempicture().isEmpty()){}
        else
            {
                Picasso.get().load(item.getItempicture()).into(holder.itemimage);
            }


        holder.itemsum_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s= Integer.parseInt(item.getItemsum());
                if (s>100){
                    s-=100;
                    holder.itemsum.setText(s+" "+item.getItemsnname());
                    item.setItemsum(s+"");
                    registeritem(String.valueOf(item.getItemsum()),String.valueOf(item.getItemid()));
                    homeFragment.itemArrayList_filter.set(holder.getAdapterPosition(),item);
                }else {
                    Toast.makeText(mitemcontext, "Not less than 100"+item.getItemsnname(), Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        holder.itemdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog(item,holder.getAdapterPosition());

            }
        });



    }
    @Override
    public int getItemCount() {
        return mitemlist.size();
    }

   public class myholder extends RecyclerView.ViewHolder{
        ImageView itemimage,itemdetail,itemsum_min;
        TextView itemname,itemsum,itemexprece;
        RelativeLayout item_background;

        public myholder(@NonNull View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.itemimage);
            itemname=itemView.findViewById(R.id.itemname);
            itemsum=itemView.findViewById(R.id.itemsum);
            itemexprece=itemView.findViewById(R.id.itemexprece);
            itemdetail=itemView.findViewById(R.id.item_show_detail);
            itemsum_min=itemView.findViewById(R.id.itemsum_minus);
            item_background=itemView.findViewById(R.id.item_background);
        }
    }
//**********************************************************************************************************************
    TextView ddelete,dname,dname_type,dnsum,dgrading;
    EditText dcalorie,dsum,dexp;
    ImageView dpicture,dimagetype;
    Button button_back;
    CheckBox dedit;
private void showdialog(item item ,int i) {

    dialog=new Dialog(mitemcontext);
    dialog.setContentView(R.layout.alert_dialog_item_detail);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    Window window=dialog.getWindow();
    window.setGravity(Gravity.CENTER);
    window.getAttributes().windowAnimations=R.style.DialogAnimation;

    button_back = dialog.findViewById(R.id.detail_back);
    ddelete = dialog.findViewById(R.id.delete_item);
    dedit = dialog.findViewById(R.id.edit_item);
    dpicture = dialog.findViewById(R.id.detail_picture);
    dname = dialog.findViewById(R.id.detail_name);
    dimagetype = dialog.findViewById(R.id.detail_image_type);
    dname_type = dialog.findViewById(R.id.detail_name_type);
    dcalorie = dialog.findViewById(R.id.detail_calorie);
    dsum = dialog.findViewById(R.id.detail_sum);
    dnsum = dialog.findViewById(R.id.detail_nsum);
    dexp = dialog.findViewById(R.id.detail_exp);
    dgrading=dialog.findViewById(R.id.detail_grading);
    for (type type:mainapp.typeArrayList){
        if (item.getItemtype().equals(type.getTypeid())){
        Picasso.get().load(type.getTypepicture()).into(dimagetype);
        dname_type.setText(type.getTypename());
        }
    }
    dedit.setOnCheckedChangeListener((compoundButton, b) -> {
        if (dedit.isChecked()){
            button_back.setText("save");
            dsum.setFocusableInTouchMode(true);
            dsum.setBackgroundResource(R.drawable.background_btn_white1);
            dcalorie.setFocusableInTouchMode(true);
            dcalorie.setBackgroundResource(R.drawable.background_btn_white1);
            dexp.setFocusableInTouchMode(true);
            dexp.setBackgroundResource(R.drawable.background_btn_white1);
        }else {
            dsum.setFocusable(false);
            dsum.setBackgroundResource(R.drawable.background_btn_white);
            dcalorie.setFocusable(false);
            dcalorie.setBackgroundResource(R.drawable.background_btn_white);
            dexp.setFocusable(false);
            dexp.setBackgroundResource(R.drawable.background_btn_white);
            button_back.setText("back");

        }
    });
    button_back.setOnClickListener(view -> {
        if (!dedit.isChecked()) {
            dialog.dismiss();
        }else {
            String sum=dsum.getText().toString();
            String calorie=dcalorie.getText().toString();
            String exp=dexp.getText().toString();

            if (TextUtils.isEmpty(sum)){
                dsum.setError("Please enter the sum");
                dsum.requestFocus();
                return;
            }if (TextUtils.isEmpty(calorie)){
                dcalorie.setError("Please enter the month calorie");
                dcalorie.requestFocus();
                return;
            }if (TextUtils.isEmpty(exp)){
                dexp.setError("Please enter the day exp");
                dexp.requestFocus();
                return;
            }
            homeFragment.itemArrayList.remove(item);
            homeFragment.itemArrayList_filter.remove(item);
            item.setItemsum(sum);
            item.setItemcalorie(calorie);
            item.setItemexpiration(exp);

            updata_item(item);
            dialog.dismiss();
        }
    });
    ddelete.setOnClickListener(view -> {
        deleteitem(item.getItemid());
        dialog.dismiss();
        homeFragment.itemArrayList_filter.remove(i);
        for (int j=0;homeFragment.itemArrayList.size()>j;j++){
            item item1=homeFragment.itemArrayList.get(j);
            if (item1.getItemid().equals(item.getItemid())){
                homeFragment.itemArrayList.remove(j);
                homeFragment.adapter.notifyDataSetChanged();
                break;
            }
        }
    });
    if (item.getItempicture().contains("/storage/emulated/0/Android/data/com.example.android1project/cache/")){
        File imgFile = new File(item.getItempicture());
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            dpicture.setImageBitmap(myBitmap);
        }
    }else{
        Picasso.get().load(item.getItempicture()).into(dpicture);
    }
    dname.setText(item.getItemname());
    dcalorie.setText(item.getItemcalorie());
    dnsum.setText(item.getItemsnname());
    dsum.setText(item.getItemsum());
    dexp.setText(item.getItemexpiration());
    dgrading.setText(item.getItemgrading());

    dialog.setCancelable(true);
    window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT);
    dialog.show();

}

    private void updata_item(item item) {
        class update_item extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("itemid", item.getItemid());
                params.put("itemsum", item.getItemsum());
                params.put("itemcalorie", item.getItemcalorie());
                params.put("itemexpiration", item.getItemexpiration());
                return requestHandler.sendPostRequest(config.update_item, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(mitemcontext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    for (int i=0;i<homeFragment.itemArrayList.size();i++){
                        item item1=homeFragment.itemArrayList.get(i);
                        if (Integer.parseInt(item1.getItemexpiration())>Integer.parseInt(item.getItemexpiration())){
                            homeFragment.itemArrayList.add(i,item);
                            break;

                        }
                    }for (int i=0;i<homeFragment.itemArrayList_filter.size();i++){
                        item item1=homeFragment.itemArrayList_filter.get(i);
                        if (Integer.parseInt(item1.getItemexpiration())>Integer.parseInt(item.getItemexpiration())){
                            homeFragment.itemArrayList_filter.add(i,item);
                            break;

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        update_item update_item = new update_item();
        update_item.execute();
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
                    Toast.makeText(mitemcontext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        deleteitem deleteitem = new deleteitem();
        deleteitem.execute();
    }



    private void registeritem(String sum, String id) {
        class Registertype extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("itemsum",sum);
                params.put("itemid",id);

                //returing the response
                return requestHandler.sendPostRequest(config.updateitem_sum, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        //executing the async task
        Registertype ru = new Registertype();
        ru.execute();
    }
}
