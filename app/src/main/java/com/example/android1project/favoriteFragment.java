package com.example.android1project;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashMap;

public class favoriteFragment extends Fragment {
    static ArrayList<item> itemArrayList_favorite = new ArrayList<>();

    RecyclerView recyclerView;
    TextView saveitem_back;
    recyclerviewadapter_favorite adapter_favorite;
    LottieAnimationView loview;
    public favoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        itemArrayList_favorite = SharedPrefManeger_item.getInstance(getActivity()).getArrayList("favorite");
//        Toast.makeText(getActivity(), itemArrayList_favorite.size()+"", Toast.LENGTH_SHORT).show();
        recyclerView = view.findViewById(R.id.add_faverite);
        saveitem_back = view.findViewById(R.id.saveitem_back);
        loview=view.findViewById(R.id.add_not_found);
        loview.setVisibility(View.VISIBLE);
        if (itemArrayList_favorite != null) {
            loview.setVisibility(View.GONE);
             adapter_favorite= new recyclerviewadapter_favorite(getActivity(), itemArrayList_favorite);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter_favorite);
        }

        saveitem_back.setOnClickListener(view1 -> getActivity().finish());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "for add to list press long click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                item item = itemArrayList_favorite.get(position);
                homeFragment.itemArrayList.add(item);
                homeFragment.itemArrayList_filter.add(item);
                registeritem(item);
                Toast.makeText(getActivity(), "item is added", Toast.LENGTH_SHORT).show();
            }
        }));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                itemArrayList_favorite.remove(viewHolder.getAdapterPosition());
                adapter_favorite.notifyItemRemoved(viewHolder.getAdapterPosition());
                SharedPrefManeger_item.getInstance(getActivity()).saveArrayList(itemArrayList_favorite, "favorite");
                Toast.makeText(getActivity(), "item favorite is delete", Toast.LENGTH_SHORT).show();
                if (itemArrayList_favorite.size()==0){
                    loview.setVisibility(View.VISIBLE);
                }
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    private void registeritem(item item) {
        class Registertype extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("itemsum", item.getItemsum());
                params.put("itempicture", item.getItempicture());
                params.put("itemname", item.getItemname());
                params.put("itemtype", item.getItemtype());
                params.put("itemexpiration", item.getItemexpiration());
                params.put("itemuser", item.getItemuser());
                params.put("itemcalorie", item.getItemcalorie());
                params.put("itemsumn", item.getItemsnname());
                params.put("itemgrading", item.getItemgrading());

                //returing the response
                return requestHandler.sendPostRequest(config.additem_save, params);
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
}