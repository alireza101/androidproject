package com.example.android1project;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android1project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerviewadapter_hor extends RecyclerView.Adapter<recyclerviewadapter_hor.viewholder>{

    private ArrayList<type>mtypelist;

    private Context mcontext;

    public recyclerviewadapter_hor(Context mcontext,ArrayList<type>mtypelist) {

        this.mtypelist=mtypelist;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_horizental,parent,false);

        return new viewholder(view);

    }
    int row_index;
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {

        type type=mtypelist.get(position);
        holder.namehor.setText(type.getTypename());
        Picasso.get().load(type.getTypepicture()).into(holder.imagehor);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index= position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            holder.linearLayout.setBackgroundResource(R.drawable.background_btn_white1);
        }
        else
        {
            holder.linearLayout.setBackgroundResource(R.drawable.background_btn_white);
        }

    }

    @Override
    public int getItemCount() {
        return mtypelist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView imagehor;
        TextView namehor;
        LinearLayout linearLayout;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imagehor=itemView.findViewById(R.id.imagehor);
            namehor=itemView.findViewById(R.id.namehor);
            linearLayout=itemView.findViewById(R.id.namecard1);
        }
    }


}

