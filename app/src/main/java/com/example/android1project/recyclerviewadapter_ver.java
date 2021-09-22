package com.example.android1project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerviewadapter_ver extends RecyclerView.Adapter<recyclerviewadapter_ver.viewholder> {
    private ArrayList<String> mitemname=new ArrayList<>();
    private ArrayList<String>mitemimage=new ArrayList<>();
    private ArrayList<String>mitemsum=new ArrayList<>();
    private ArrayList<String>mitemexprecine=new ArrayList<>();
    private Context mitemcontext;

    public recyclerviewadapter_ver(Context mitemcontext , ArrayList<String> mitemname, ArrayList<String> mitemimage, ArrayList<String> mitemsum, ArrayList<String> mitemexprecine ) {
        this.mitemname = mitemname;
        this.mitemimage = mitemimage;
        this.mitemsum = mitemsum;
        this.mitemexprecine = mitemexprecine;
        this.mitemcontext = mitemcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_vertical,parent,false);

        return new recyclerviewadapter_ver.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(mitemcontext)
                .asBitmap()
                .load(mitemimage.get(position))
                .into(holder.itemimage);
        holder.itemname.setText(mitemname.get(position));
        holder.itemsum.setText(mitemsum.get(position));
        holder.itemexprece.setText(mitemexprecine.get(position));
        holder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mitemcontext, "click to the "+ mitemname.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mitemname.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView itemimage;
        TextView itemname,itemsum,itemexprece;
        LinearLayout itemcard;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.itemimage);
            itemname=itemView.findViewById(R.id.itemname);
            itemsum=itemView.findViewById(R.id.itemsum);
            itemexprece=itemView.findViewById(R.id.itemexprece);
            itemcard=itemView.findViewById(R.id.itemcard);
        }
    }
}
