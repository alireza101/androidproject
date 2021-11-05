package com.example.android1project;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class recyclerviewadapter_ver  extends RecyclerView.Adapter<recyclerviewadapter_ver.myholder> {
    private ArrayList<item> mitemlist;
    private Context mitemcontext;
    int s=0;

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
        int b=position;
        if (b%2==1) {
            holder.item_background.setBackgroundResource(R.color.black3);
        }
        item item=mitemlist.get(position);

        s= Integer.parseInt(item.getItemsum());
        holder.itemname.setText(item.getItemname());
        holder.itemsum.setText(s+"");
        holder.itemexprece.setText(item.getItemexpiration()+"days");
        Picasso.with(mitemcontext).load(item.getItempicture()).into(holder.itemimage);

        holder.itemsum_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s= Integer.parseInt(item.getItemsum());
                if (s>1){
                    s--;
                    holder.itemsum.setText(s+"");
                    item.setItemsum(s+"");
                    registeritem(String.valueOf(item.getItemsum()),String.valueOf(item.getItemid()));
                    homeFragment.itemArrayList.set(holder.getAdapterPosition(),item);
                }else {
                    Toast.makeText(mitemcontext, "Not less than 1", Toast.LENGTH_SHORT).show();
                }
                
            }
        });



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

            }
        }
        //executing the async task
        Registertype ru = new Registertype();
        ru.execute();
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
}
