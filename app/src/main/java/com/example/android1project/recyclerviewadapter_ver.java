package com.example.android1project;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class recyclerviewadapter_ver  extends RecyclerView.Adapter<recyclerviewadapter_ver.myholder> {
    private ArrayList<item> mitemlist;
    private Context mitemcontext;
    int s=0;
    static item itemdetail;

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
        holder.itemexprece.setText(item.getItemexpiration()+"  days exp");
        if (item.getItempicture().contains("/storage/emulated/0/Android/data/com.example.android1project/cache/")){
            File imgFile = new File(item.getItempicture());
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.itemimage.setImageBitmap(myBitmap);
            }
        }else if (!item.getItempicture().isEmpty()) {
                    Picasso.get().load(item.getItempicture()).into(holder.itemimage);
        }


        holder.itemsum_min.setOnClickListener(view -> {
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

        });
        holder.itemdetail.setOnClickListener(view -> {
            Intent intent=new Intent(mitemcontext,profile.class);
            intent.putExtra("profileitem",7);
            itemdetail=item;
            Bundle bundle=new Bundle();
            bundle.putInt("detailkey",holder.getAdapterPosition());
            intent.putExtra("detailbundle",bundle);
            mitemcontext.startActivity(intent);
            ((Activity)mitemcontext).finish();
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
