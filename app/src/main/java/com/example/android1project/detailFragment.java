package com.example.android1project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class detailFragment extends Fragment {
    TextView ddelete, dname_type, dnsum, dgrading, dexp;
    EditText dcalorie, dsum, dcost, dname;
    ImageView dpicture, dimagetype;
    Button button_back;
    CheckBox dedit;
    int i;
    DatePickerDialog picker;
    FloatingActionButton floatingActionButton;
    String picturePath;
    Bitmap selectedimage;
    String pathname = "",pathpic="";
    public detailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        item item = recyclerviewadapter_ver.itemdetail;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            i = bundle.getInt("detailkey");
        }
        button_back = view.findViewById(R.id.detail_back);
        ddelete = view.findViewById(R.id.delete_item);
        dedit = view.findViewById(R.id.edit_item);
        dpicture = view.findViewById(R.id.detail_picture);
        dname = view.findViewById(R.id.detail_name);
        dimagetype = view.findViewById(R.id.detail_image_type);
        dname_type = view.findViewById(R.id.detail_name_type);
        dcalorie = view.findViewById(R.id.detail_calorie);
        dsum = view.findViewById(R.id.detail_sum);
        dnsum = view.findViewById(R.id.detail_nsum);
        dexp = view.findViewById(R.id.detail_exp);
        dgrading = view.findViewById(R.id.detail_grading);
        dcost = view.findViewById(R.id.detail_cost);
        floatingActionButton = view.findViewById(R.id.detail_floatingactionbutton);

        for (type type : mainapp.typeArrayList) {
            if (item.getItemtype().equals(type.getTypeid())) {
                Picasso.get().load(type.getTypepicture()).into(dimagetype);
                dname_type.setText(type.getTypename());
            }
        }
        floatingActionButton.setOnClickListener(view1 -> {
            if (custom_item_Fragment.checkAndRequestPermissions(getActivity())) {
                chooseImage(getActivity());

            }
        });
        dedit.setOnCheckedChangeListener((compoundButton, b) -> {
            if (dedit.isChecked()) {
                button_back.setText("save");
                dsum.setFocusableInTouchMode(true);
                dsum.setBackgroundResource(R.drawable.background_btn_white1);
                dcalorie.setFocusableInTouchMode(true);
                dcalorie.setBackgroundResource(R.drawable.background_btn_white1);
                dexp.setFocusableInTouchMode(true);
                dexp.setBackgroundResource(R.drawable.background_btn_white1);
                dcost.setFocusableInTouchMode(true);
                dcost.setBackgroundResource(R.drawable.background_btn_white1);
                dname.setFocusableInTouchMode(true);
                dname.setBackgroundResource(R.drawable.background_btn_white1);
                floatingActionButton.setVisibility(View.VISIBLE);

            } else {
                dsum.setFocusable(false);
                dsum.setBackgroundResource(R.drawable.background_btn_white);
                dname.setFocusable(false);
                dname.setBackgroundResource(R.color.white);
                dcalorie.setFocusable(false);
                dcalorie.setBackgroundResource(R.drawable.background_btn_white);
                dexp.setFocusable(false);
                dexp.setBackgroundResource(R.drawable.background_btn_white);
                dcost.setFocusable(false);
                dcost.setBackgroundResource(R.drawable.background_btn_white);
                button_back.setText("back");
                floatingActionButton.setVisibility(View.GONE);

            }
        });

//        MaterialDatePicker.Builder builder=MaterialDatePicker.Builder.datePicker();
//        builder.setTheme(R.style.MaterialCalendarTheme);
//
//        builder.setTitleText("SELECT A DATE");
//        MaterialDatePicker materialDatePicker=builder.build();


        dexp.setOnClickListener(view1 -> {
            if (dexp.isFocusable()) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
//                                Calendar calendar=null;
//                                calendar.set(year,monthOfYear,dayOfMonth);
                                Date date1 = Calendar.getInstance().getTime();
                                long exp = getDateDiff.getDateDiff(date1, date);
                                item.setItemexpiration(String.valueOf(exp));
                                dexp.setText(exp + "");
                            }
                        }, year, month, day);
                picker.show();
            }
        });
//        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
//            dexp.setText("Select Date: "+materialDatePicker.getHeaderText());
//        });


        button_back.setOnClickListener(view1 -> {
            if (dedit.isChecked()) {
                String name = dname.getText().toString();
                String sum = dsum.getText().toString();
                String calorie = dcalorie.getText().toString();
                String exp = dexp.getText().toString();

                if (TextUtils.isEmpty(sum)) {
                    dsum.setError("Please enter the sum");
                    dsum.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(calorie)) {
                    dcalorie.setError("Please enter the month calorie");
                    dcalorie.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(exp)) {
                    dexp.setError("Please enter the day exp");
                    dexp.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    dname.setError("Please enter the name");
                    dname.requestFocus();
                    return;
                }

                item.setItempicture(pathpic);
                item.setItemsum(sum);
                item.setItemcalorie(calorie);
                item.setItemexpiration(exp);
                item.setItemcost(dcost.getText().toString());
                item.setItemname(dname.getText().toString());
                costFragment.costarray=SharedPrefManeger_item.getInstance(getActivity()).getArrayList_cost("cost");
                for (cost cost:costFragment.costarray){
                    if (cost.getItemid().equals(item.getItemid())){
                        costFragment.costarray.remove(cost);
                        costFragment.costarray.add(new cost(item.getItemname(),item.getItemcost(),item.getItemid(),String.valueOf(Calendar.getInstance().getTimeInMillis())));
                    }
                }
                SharedPrefManeger_item.getInstance(getActivity()).saveArrayList_cost(costFragment.costarray,"cost");
                updata_item(item);
            }
            getActivity().finish();
            startActivity(new Intent(getActivity(), mainapp.class));

        });
        ddelete.setOnClickListener(view1 -> {
            deleteitem(item.getItemid());
            getActivity().finish();
            homeFragment.itemArrayList_filter.remove(i);
            for (int j = 0; homeFragment.itemArrayList.size() > j; j++) {
                item item1 = homeFragment.itemArrayList.get(j);
                if (item1.getItemid().equals(item.getItemid())) {
                    homeFragment.itemArrayList.remove(j);
                    homeFragment.adapter.notifyDataSetChanged();
                    break;
                }
            }
        });
        if (item.getItempicture().contains("/storage/emulated/0/Android/data/com.example.android1project/cache/")) {
            File imgFile = new File(item.getItempicture());
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                dpicture.setImageBitmap(myBitmap);
            }
        } else {
            if (!item.getItempicture().equals("")) {
                Picasso.get().load(item.getItempicture()).into(dpicture);
            }
        }
        dname.setText(item.getItemname());
        dcalorie.setText(item.getItemcalorie());
        dnsum.setText(item.getItemsnname());
        dsum.setText(item.getItemsum());
        dexp.setText(item.getItemexpiration());
        dgrading.setText(item.getItemgrading());
        dcost.setText(item.getItemcost());
        return view;
    }

    public void chooseImage(Context context) {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery"}; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                }
            }
        });
        builder.show();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == getActivity().RESULT_OK && data != null) {
                        selectedimage = (Bitmap) data.getExtras().get("data");
                        dpicture.setImageBitmap(selectedimage);
                        createDirectoryAndSaveFile(selectedimage,"image");
                    }
                    break;
                case 1:
                    if (resultCode == getActivity().RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);
                                selectedimage= BitmapFactory.decodeFile(picturePath);
                                dpicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                createDirectoryAndSaveFile(selectedimage,"image");
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
    File file;

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File direct = new File(getActivity().getExternalCacheDir() + "/");
        pathname = direct.toString();
        fileName=fileName+".jpeg";
        if (!direct.exists()) {
            File wallpaperDirectory = new File(pathname);
            wallpaperDirectory.mkdirs();
        }
        int i=1;
        file = new File(pathname, fileName);
        while (file.exists()){
            if (file.exists()) {
                i++;
                file = new File(pathname,i+ fileName);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            pathpic=file.toString();
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //***********************************************************************************************
    private void updata_item(item item) {
        class update_item extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("itemid", item.getItemid());
                params.put("itemsum", item.getItemsum());
                params.put("itemcalorie", item.getItemcalorie());
                params.put("itemexpiration", item.getItemexpiration());
                params.put("itemcost", item.getItemcost());
                params.put("itemname", item.getItemname());
                params.put("itempicture", item.getItempicture());
                return requestHandler.sendPostRequest(config.update_item, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < homeFragment.itemArrayList.size(); i++) {
                        item item1 = homeFragment.itemArrayList.get(i);
                        if (Integer.parseInt(item1.getItemexpiration()) > Integer.parseInt(item.getItemexpiration())) {
                            homeFragment.itemArrayList.add(i, item);
                            break;

                        }
                    }
                    for (int i = 0; i < homeFragment.itemArrayList_filter.size(); i++) {
                        item item1 = homeFragment.itemArrayList_filter.get(i);
                        if (Integer.parseInt(item1.getItemexpiration()) > Integer.parseInt(item.getItemexpiration())) {
                            homeFragment.itemArrayList_filter.add(i, item);
                            break;

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        update_item update_item = new update_item();
        update_item.execute();
    }

    public void deleteitem(String id) {

        class deleteitem extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();
                params.put("getitemid", id);
                return requestHandler.sendPostRequest(config.delete, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        deleteitem deleteitem = new deleteitem();
        deleteitem.execute();
    }
}