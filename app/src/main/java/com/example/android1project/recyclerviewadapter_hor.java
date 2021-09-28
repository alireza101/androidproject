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
import com.example.android1project.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerviewadapter_hor extends RecyclerView.Adapter<recyclerviewadapter_hor.viewholder>{
    private ArrayList<String> mnamehor=new ArrayList<>();
    private ArrayList<Integer>mimagehor=new ArrayList<>();
    private Context mcontext;

    public recyclerviewadapter_hor(Context mcontext,ArrayList<String> mnamehor, ArrayList<Integer> mimagehor) {
        this.mnamehor = mnamehor;
        this.mimagehor = mimagehor;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_horizental,parent,false);

        return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(mcontext)
                .asBitmap()
                .load(mimagehor.get(position))
                .into(holder.imagehor);
        holder.namehor.setText(mnamehor.get(position));
//        holder.namecard1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mcontext, "click to the "+ mnamehor.get(position), Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return mnamehor.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView imagehor;
        TextView namehor;
 //       LinearLayout namecard1;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imagehor=itemView.findViewById(R.id.imagehor);
            namehor=itemView.findViewById(R.id.namehor);
//            namecard1=itemView.findViewById(R.id.namecard1);
        }
    }


}

