package com.example.android1project;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerviewadapter_ver  extends RecyclerView.Adapter<recyclerviewadapter_ver.myholder> {
    private ArrayList<item> mitemlist;
    private Context mitemcontext;

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

        item item=mitemlist.get(position);

        holder.itemname.setText(item.getItemname());
        holder.itemsum.setText(item.getItemsum());
        holder.itemexprece.setText(item.getItemexpiration());
        Picasso.with(mitemcontext).load(item.getItempicture()).into(holder.itemimage);

        holder.itemcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mitemcontext,item.getItemname(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mitemlist.size();
    }

    //  @NonNull
  //  @Override
  //  public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   //     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_vertical,parent,false);

   //     return new recyclerviewadapter_ver.viewholder(view);
  //  }

  //  @Override
  //  public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
 /*       Glide.with(mitemcontext)
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
*/
 //   }

 //   @Override
 //   public int getItemCount() {
  //      return mitemname.size();
  //  }

   public class myholder extends RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView itemname,itemsum,itemexprece;
        LinearLayout itemcard;

        public myholder(@NonNull View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.itemimage);
            itemname=itemView.findViewById(R.id.itemname);
            itemsum=itemView.findViewById(R.id.itemsum);
            itemexprece=itemView.findViewById(R.id.itemexprece);
            itemcard=itemView.findViewById(R.id.itemcard);
        }
    }
}
