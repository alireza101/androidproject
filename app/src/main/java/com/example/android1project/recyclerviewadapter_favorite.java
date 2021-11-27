package com.example.android1project;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class recyclerviewadapter_favorite extends RecyclerView.Adapter<recyclerviewadapter_favorite.myholder> {
    private ArrayList<item> mitemlist;
    private Context mitemcontext;

    public recyclerviewadapter_favorite(Context mitemcontext , ArrayList<item> mitemexprecine ) {
        this.mitemlist = mitemexprecine;
        this.mitemcontext = mitemcontext;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mitemcontext).inflate(R.layout.layout_listitem_favorite,parent,false);
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        if (position%2==1) {
            holder.itemmian.setBackgroundResource(R.color.black3);
        }
        item item=mitemlist.get(position);

        holder.itemname.setText(item.getItemname());
        holder.itemsum.setText(item.getItemsum()+" "+item.getItemsnname());
        holder.itemexprece.setText(item.getItemexpiration()+" days");
        holder.item_calorie.setText(item.getItemcalorie()+" calorie");
        Picasso.get().load(item.getItempicture()).into(holder.itemimage);


        holder.itemmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
    @Override
    public int getItemCount() {
        return mitemlist.size();
    }

   public class myholder extends RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView itemname,itemsum,itemexprece,item_calorie;
        RelativeLayout itemmian;

        public myholder(@NonNull View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.favorite_image);
            itemname=itemView.findViewById(R.id.favorite_name);
            itemsum=itemView.findViewById(R.id.favorite_sum);
            itemexprece=itemView.findViewById(R.id.favorite_exp);
            itemmian=itemView.findViewById(R.id.favorite_background);
            item_calorie=itemView.findViewById(R.id.favorite_calorie);
        }
    }


}
