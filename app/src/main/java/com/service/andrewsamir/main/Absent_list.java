package com.service.andrewsamir.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.andrewsamir.main.adaptors.DBhelper;
import com.service.andrewsamir.main.data.AbsentData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Andrew Samir on 2/24/2016.
 */
public class Absent_list extends ActionBarActivity {

    String dt_absent;
    //AbsentAdapter na_absent;
    public static ArrayList<AbsentData> datad_Absent = new ArrayList<>();
    ListView lv;
    ArrayList<AbsentData> np_absent = new ArrayList<>();
    ProgressBar progressBar;
    LinearLayout linearLayout;
    TextView t1, t2, t3, t4;

    ArrayList<String> dates = new ArrayList<>();

    DBhelper myDB;
    DataSnapshot myChild;

    LinearLayout linearLayouth;
    LinearLayout LinNames;

    int height,width,textsize,spaceWidth;

    HashMap<Integer, Integer> hashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_absent);
        LinNames = (LinearLayout) findViewById(R.id.showAbsentNames);

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                textsize=22;
                width=70;
                height=70;
                spaceWidth=3;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                textsize=15;
                width=50;
                height=50;
                spaceWidth=1;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                textsize=10;
                width=30;
                height=30;
                spaceWidth=1;
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                toastMsg = "Xlarge screen";
                textsize=35;
                width=150;
                height=100;
                spaceWidth=5;
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }

        if (isOnline()) {

            //////////////////////////////////////////

            final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

            Firebase ref = new Firebase("https://abrar-family.firebaseio.com/absent/" + jsonData.getString("fasl", "default"));
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println(snapshot.getValue());

                    if (true) {
                        // first = true;

                        Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                        String[][] dats = new String[50][100];
                        myDB = new DBhelper(Absent_list.this);
                        LinearLayout lin = (LinearLayout) findViewById(R.id.showAbsentLinear);

                        TextView dateText = new TextView(getApplicationContext());
                        dateText.setText("+");
                        dateText.setTextColor(Color.BLACK);


                        int itr = 0;

                        while (myChildren.iterator().hasNext()) {
                            myChild = myChildren.iterator().next();

                            Log.d("getdata", myChild.getKey() + myChild.getValue().toString());
                            try {
                                JSONObject jsonObject = new JSONObject(myChild.getValue().toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("absent");


                                dates.add(myChild.getKey());

                                hashMap.put(dates.size() - 1, jsonArray.length());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String s = jsonArray.getString(i);

                                    dats[itr][i] = s;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                itr++;
                                Log.d("array" + itr, dats.toString());
                            }
                        }

                        Cursor cursor = myDB.getKashfList();
                        LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                        TextView textView = new TextView(getApplicationContext());
                        textView.setText("الاسم");
                        textView.setTextSize(textsize);
                        textView.setTextColor(Color.BLACK);
                        textView.setTypeface(null, Typeface.BOLD);
                        textView.setWidth(400);
                        textView.setHeight(height);
                        textView.setBackgroundColor(Color.parseColor("#FFFF8D"));

                        textView.setGravity(Gravity.CENTER);

                        /////////////////////////////////////////////////

                        for (int i = 0; i < dates.size(); i++) {
                            TextView textView1 = new TextView(getApplicationContext());
                            Long dateLong = Long.parseLong(dates.get(i));


                            Calendar c = Calendar.getInstance();
                            //Set time in milliseconds
                            c.setTimeInMillis(dateLong);
                            int mMonth = c.get(Calendar.MONTH);
                            int mDay = c.get(Calendar.DAY_OF_MONTH);

                            //  String[] n= dates.get(i).split("-");
                            textView1.setText(mDay + "-" + mMonth);
                            textView1.setTextSize(textsize);

                            textView1.setTextColor(Color.BLACK);

                            textView1.setWidth(width);
                            textView1.setHeight(height);

                            textView1.setGravity(Gravity.CENTER);
                            textView1.setBackgroundColor(Color.parseColor("#8BC34A"));

                            TextView space1 = new TextView(getApplicationContext());

                            space1.setWidth(spaceWidth);
                            space1.setHeight(spaceWidth);

                            linearLayout1.addView(textView1);
                            linearLayout1.addView(space1);

                        }

                        LinNames.addView(textView);


                        linearLayout1.setGravity(Gravity.END);
                        lin.addView(linearLayout1);
                        if (cursor.moveToFirst()) {
                            do {
                                linearLayout1 = new LinearLayout(getApplicationContext());
                                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                                textView = new TextView(getApplicationContext());
                                textView.setText(cursor.getString(1));
                                textView.setTextSize(textsize);
                                textView.setTypeface(null, Typeface.BOLD);

                                textView.setBackgroundColor(Color.parseColor("#8BC34A"));
                                textView.setPadding(0, 0, 20, 0);

                                textView.setTextColor(Color.BLACK);

                                textView.setWidth(400);
                                textView.setHeight(height);

                                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                                //////////////////////////////////////////

                                for (int i = 0; i < dates.size(); i++) {
                                    TextView textView1 = new TextView(getApplicationContext());
                                    textView1.setTextColor(Color.BLACK);

                                    textView1.setWidth(width);
                                    textView1.setHeight(height);

                                    ArrayList<String> list = new ArrayList<String>();
                                    for (int j = 0; j < hashMap.get(i); j++)
                                        list.add(dats[i][j]);
                                    if (list.contains(cursor.getString(0))) {
                                        textView1.setText("+");
                                        textView1.setTextSize(textsize);
                                        textView1.setBackgroundColor(Color.GREEN);
                                    } else {
                                        textView1.setText("-");
                                        textView1.setTextSize(textsize);
                                        textView1.setBackgroundColor(Color.parseColor("#FF3D00"));

                                    }
                                    textView1.setGravity(Gravity.CENTER);

                                    TextView space1 = new TextView(getApplicationContext());

                                    space1.setWidth(spaceWidth);
                                    space1.setHeight(spaceWidth);

                                    linearLayout1.addView(textView1);
                                    linearLayout1.addView(space1);

                                }

                                //////////////////////////////////////////
                                View v1 = new View(getApplicationContext());
                                v1.setLayoutParams(new LinearLayout.LayoutParams(
                                        ActionBar.LayoutParams.MATCH_PARENT,
                                        spaceWidth
                                ));
                                v1.setBackgroundColor(Color.parseColor("#E6EE9C"));

                                linearLayout1.setGravity(Gravity.END);
                                LinNames.addView(v1);
                                LinNames.addView(textView);

                                View v = new View(getApplicationContext());
                                v.setLayoutParams(new LinearLayout.LayoutParams(
                                        ActionBar.LayoutParams.MATCH_PARENT,
                                        spaceWidth
                                ));
                                v.setBackgroundColor(Color.parseColor("#E6EE9C"));


                                lin.addView(v);
                                lin.addView(linearLayout1);

                            } while (cursor.moveToNext());
                        }

                    }
                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.println("The read failed: " + firebaseError.getMessage());
                }
            });


//////////////////////////////////////////////////////////////////////////


        } else

        {
            Toast.makeText(Absent_list.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();

        }

    }




    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
