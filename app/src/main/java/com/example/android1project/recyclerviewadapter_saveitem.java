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
    private final ArrayList<item> mitemlist;

    private final Context mcontext;

    public recyclerviewadapter_saveitem(Context mcontext, ArrayList<item> mitemlist) {

        this.mitemlist = mitemlist;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_saveitem_item, parent, false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        item item = mitemlist.get(position);

        holder.itemname.setText(item.getItemname());
        holder.itemname.setTextSize(24);
        holder.itemexp.setText(item.getItemexpiration()+" days exp");
        Picasso.with(mcontext).load(item.getItempicture()).into(holder.itemimage);
        holder.imageshow.setImageResource(R.drawable.add);
        holder.imageshow.setTag(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return mitemlist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView itemimage, imageshow;
        TextView itemname,itemexp;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            itemimage = itemView.findViewById(R.id.saveitem_image);
            imageshow = itemView.findViewById(R.id.saveitem_showitem);
            itemname = itemView.findViewById(R.id.saveitem_name);
            itemexp=itemView.findViewById(R.id.saveitem_exp);
            itemname.setAllCaps(false);

        }
    }


}
