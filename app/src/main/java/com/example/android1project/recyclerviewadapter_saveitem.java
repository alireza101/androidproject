package com.example.android1project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class recyclerviewadapter_saveitem extends RecyclerView.Adapter<recyclerviewadapter_saveitem.viewholder> {
    private final ArrayList<type> mtypelist;

    private final Context mcontext;

    public recyclerviewadapter_saveitem(Context mcontext,ArrayList<type> mtypelist) {

        this.mtypelist=mtypelist;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_saveitem,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder , int position) {
        type type=mtypelist.get(position);
        if (type.getTypepicture1()!="null") {
            holder.typename.setText(type.getTypename());
            Picasso.with(mcontext).load(type.getTypepicture1()).into(holder.typeimage);
        }
    }

    @Override
    public int getItemCount() {
        return mtypelist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView typeimage;
        TextView typename;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            typeimage=itemView.findViewById(R.id.saveitem_image);
            typename=itemView.findViewById(R.id.saveitem_name);
        }
    }

}
