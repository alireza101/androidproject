package com.example.android1project;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class custom_item_Fragment extends Fragment  {
    private ArrayList<type> typeArrayList_custom = new ArrayList<>();


    ImageView imageview, add, minus;
    TextView edsumn,saveitem_back,edexp;

    Button btnclose, btnadd;
    EditText  edname, edsum,edcalorie,edcost;
    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout linearLayoutg;
    String name,exp,sum,calorie,grading,typeid,cost;
    FloatingActionButton floatingActionButton;
    String pathpic="";
    RecyclerView recyclerView;
    CheckBox checkBox;
    Boolean ATF=false;
    String picturePath;
    Bitmap selectedimage;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String pathname = "";
    public custom_item_Fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_custom_item, container, false);

        btnclose = view.findViewById(R.id.additem_close);
        btnadd = view.findViewById(R.id.additem_add);
        edname = view.findViewById(R.id.additem_name);
        imageview = view.findViewById(R.id.additem_image);
        edexp = view.findViewById(R.id.additem_exp);
        edsum = view.findViewById(R.id.additem_sum);
        add = view.findViewById(R.id.additem_sum_add);
        minus = view.findViewById(R.id.additem_sum_minus);
        edsumn=view.findViewById(R.id.additem_sum_name);
        radioGroup=view.findViewById(R.id.additem_grading);
        edcalorie=view.findViewById(R.id.additem_calorie_s);
        linearLayoutg=view.findViewById(R.id.additem_grading_layout);
        floatingActionButton=view.findViewById(R.id.additem_floatingactionbutton);
        recyclerView=view.findViewById(R.id.additem_recyclerview);
        checkBox=view.findViewById(R.id.additem_favorite);
        saveitem_back=view.findViewById(R.id.saveitem_back);
        edcost=view.findViewById(R.id.additem_cost);

        edsum.setText("100");
        grading="industrial";
        typeArrayList_custom.clear();
        for (int i = 0; i < mainapp.typeArrayList.size(); i++) {
            type type = mainapp.typeArrayList.get(i);
            if (!type.getTypename().equals("All")) {
                typeArrayList_custom.add(type);
            }
        }
        recyclerviewadapter_hor adapter = new recyclerviewadapter_hor(getActivity(), typeArrayList_custom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        typeid="2";
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                typeid=typeArrayList_custom.get(position).getTypeid();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        floatingActionButton.setOnClickListener(view1 -> {
            if (checkAndRequestPermissions(getActivity())) {
                chooseImage(getActivity());

            }});
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> ATF= b);


        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            radioButton=radioGroup.findViewById(i);
            if (radioButton.getText().equals("Household")){
                grading="Household";
            }else {
                grading="industrial";
            }
        });
        edexp.setOnClickListener(view1 -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            DatePickerDialog picker = new DatePickerDialog(getActivity(),
                    (view2, year1, monthOfYear, dayOfMonth) -> {
                        Date date = new Date(year1 -1900,monthOfYear,dayOfMonth);
//                                Calendar calendar=null;
//                                calendar.set(year,monthOfYear,dayOfMonth);
                        Date date1=Calendar.getInstance().getTime();
                        long exp1=getDateDiff.getDateDiff(date1,date);
                        edexp.setText(exp1+"");
                    }, year, month, day);
            picker.show();
        });
        btnadd.setOnClickListener(view15 -> {
            name =edname.getText().toString();
            exp   =edexp.getText().toString();

            sum  =edsum.getText().toString();
            calorie=edcalorie.getText().toString();
            cost=edcost.getText().toString();
            if (TextUtils.isEmpty(edcost.getText().toString())){
                cost="0";
            }
            if (TextUtils.isEmpty(name)){
                edname.setError("Please enter the name");
                edname.requestFocus();
                return;
            }if (TextUtils.isEmpty(exp)){
                edexp.setError("Please enter the exp");
                edexp.requestFocus();
                return;
            }if (TextUtils.isEmpty(sum)){
                edsum.setError("Please enter the sum");
                edsum.requestFocus();
                return;
            }if (TextUtils.isEmpty(calorie)){
                edcalorie.setError("Please enter the calorie");
                edcalorie.requestFocus();
                return;
            }



            user user= SharedPrefManager_user.getInstance(getActivity()).getUser();
            item item=new item("",name,pathpic,exp,calorie,""
                    ,sum,typeid,grading,String.valueOf(user.getId()),cost);
            if (ATF){
                favoriteFragment.itemArrayList_favorite.add(item);
                SharedPrefManeger_item.getInstance(getActivity()).saveArrayList(favoriteFragment.itemArrayList_favorite,"favorite");
                Toast.makeText(getActivity(), "the item add to favorite", Toast.LENGTH_SHORT).show();
            }
            registeradditem(item);
            homeFragment.itemArrayList_filter.add(item);
            homeFragment.itemArrayList.add(item);
            getActivity().finish();
            startActivity(new Intent(getActivity(),mainapp.class));
            homeFragment.adapter.notifyDataSetChanged();

        });
        minus.setOnClickListener(view12 -> {
            String sum1  =edsum.getText().toString();

            if (TextUtils.isEmpty(sum1)){
                edsum.setError("Please enter the sum");
                edsum.requestFocus();
                return;
            }
            int sum = Integer.parseInt(edsum.getText().toString());

            if (sum > 199) {
                sum=sum-100;
            } else {
                edsum.setError("Not less than 100 g");
                edsum.requestFocus();
            }
            edsum.setText(sum+"");

        });
        add.setOnClickListener(view13 -> {
            String sum1  =edsum.getText().toString();

            if (TextUtils.isEmpty(sum1)){
                edsum.setError("Please enter the sum");
                edsum.requestFocus();
                return;
            }
            int sum = Integer.parseInt(edsum.getText().toString());
            sum=sum+100;
            edsum.setText(sum+"" );

        });
        saveitem_back.setOnClickListener(view14 -> getActivity().finish());

        return view;
    }


//    void openFolder(String location) {
//        // location = "/sdcard/my_folder";
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri mydir = Uri.parse(location);
//        intent.setDataAndType(mydir, "*/*");
//        startActivity(intent);
//    }

    // function to let's the user to choose image from camera or gallery
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

    // function to check permission
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                }else {
                    chooseImage(getActivity());
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == getActivity().RESULT_OK && data != null) {
                        selectedimage = (Bitmap) data.getExtras().get("data");
                        imageview.setImageBitmap(selectedimage);
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
                                imageview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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





//***************************************************************************************************************************

    void registeradditem(item item) {

        class Registeritem extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "connecting...", "please wait", false, false);


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("itempicture",item.getItempicture());
                params.put("itemname",item.getItemname());
                params.put("itemtype", item.getItemtype());
                params.put("itemexpiration", item.getItemexpiration());
                params.put("itemsum", item.getItemsum());
                params.put("itemcalorie",item.getItemcalorie());
                params.put("itemsumn", item.getItemsnname());
                params.put("itemgrading", item.getItemgrading());
                params.put("itemuser", item.getItemuser());
                params.put("itemcost", item.getItemcost());

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
                //hiding the progressbar after completion
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("error")) {
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        String id=jsonObject.getString("item");
                        item.setItemid(id);
                        for (int i=0;i<homeFragment.itemArrayList.size();i++){
                            item item1=homeFragment.itemArrayList.get(i);
                            if (Integer.parseInt(item1.getItemexpiration())>Integer.parseInt(item.getItemexpiration())){
                                homeFragment.itemArrayList.add(i,item);
                                break;

                            }
                        }
                        costFragment.costarray=SharedPrefManeger_item.getInstance(getActivity()).getArrayList_cost("cost");
                        costFragment.costarray.add(new cost(item.getItemname(),item.getItemcost(),item.getItemid(),String.valueOf(Calendar.getInstance().getTimeInMillis())));
                        SharedPrefManeger_item.getInstance(getActivity()).saveArrayList_cost(costFragment.costarray,"cost");
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("message") + "..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();

                }
            }
        }
        //executing the async task
        Registeritem ru = new Registeritem();
        ru.execute();
    }

}