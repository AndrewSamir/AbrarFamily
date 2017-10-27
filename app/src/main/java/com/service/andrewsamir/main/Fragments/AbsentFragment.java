package com.service.andrewsamir.abrarfamily.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.andrewsamir.abrarfamily.Activities.HOME;
import com.service.andrewsamir.abrarfamily.R;
import com.service.andrewsamir.abrarfamily.Activities.SetAbsentActivity;
import com.service.andrewsamir.abrarfamily.data.Name;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by andre on 01-Apr-17.
 */

public class AbsentFragment extends Fragment {

    public static int height, width, textsize, spaceWidth;
    public static LinearLayout lin;
    public static DataSnapshot myChild;
    public static LinearLayout LinNames;
    public static View v;
    Boolean fTime = true;

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_absent, null);

        lin = (LinearLayout) v.findViewById(R.id.showAbsentLinearFragmentAbsent);
        LinNames = (LinearLayout) v.findViewById(R.id.showAbsentNamesFragmentAbsent);

        PageListener pageListener = new PageListener();
        HOME.mViewPager.setOnPageChangeListener(pageListener);


        screenSize();


        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fabFragmentAbsent);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
        return v;

    }

    public void getAbsentData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //DEMO
        // DatabaseReference myRef = database.getReference("fsol/BLsc8RMHwtNqQB6C23TC0tIImw92/data/absent");

        //will be
        DatabaseReference myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/data/absent");
        myRef.keepSynced(true);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();
                String[][] dats = new String[50][100];

                TextView dateText = new TextView(getActivity());
                dateText.setText("+");
                dateText.setTextColor(Color.BLACK);

                lin.removeAllViews();
                LinNames.removeAllViews();

                int itr = 0;
                HashMap<Integer, Integer> hashMap = new HashMap<>();
                ArrayList<String> dates = new ArrayList<>();


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

                //  Cursor cursor = myDB.getKashfList();
                LinearLayout linearLayout1 = new LinearLayout(getActivity());
                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView = new TextView(getActivity());
                textView.setText("الاسم");
                textView.setTextSize(textsize);
                textView.setTextColor(Color.BLACK);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setWidth(400);
                textView.setHeight(height);
                textView.setBackgroundColor(getResources().getColor(R.color.absent_name_background));

                textView.setGravity(Gravity.CENTER);


                for (int i = 0; i < dates.size(); i++) {
                    TextView textView1 = new TextView(getActivity());

                    Long dateLong = Long.parseLong(dates.get(i));


                    Calendar c = Calendar.getInstance();
                    //Set time in milliseconds
                    c.setTimeInMillis(dateLong);
                    int mMonth = c.get(Calendar.MONTH) + 1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH);


                    //   String[] n = dates.get(i).split("-");
                    textView1.setText(mDay + "-" + mMonth);
                    textView1.setTextSize(textsize);

                    textView1.setTextColor(Color.BLACK);

                    textView1.setWidth(width);
                    textView1.setHeight(height);

                    textView1.setGravity(Gravity.CENTER);
                    textView1.setBackgroundColor(getResources().getColor(R.color.absent_date));

                    TextView space1 = new TextView(getActivity());

                    space1.setWidth(spaceWidth);
                    space1.setHeight(spaceWidth);

                    linearLayout1.addView(textView1);
                    linearLayout1.addView(space1);

                }
                LinNames.addView(textView);


                linearLayout1.setGravity(Gravity.END);
                lin.addView(linearLayout1);

                try {
                    for (Name name : KashfFragment.kashfLists) {
                        linearLayout1 = new LinearLayout(getActivity());
                        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                        textView = new TextView(getActivity());
                        textView.setText(name.getName());
                        textView.setTextSize(textsize);
                        textView.setTypeface(null, Typeface.BOLD);
                        textView.setMaxLines(1);
                        textView.setBackgroundColor(getResources().getColor(R.color.absent_names_background));
                        textView.setPadding(0, 0, 40, 0);

                        textView.setTextColor(Color.BLACK);

                        textView.setWidth(400);
                        textView.setHeight(height);

                        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

                        //////////////////////////////////////////

                        for (int i = 0; i < dates.size(); i++) {
                            TextView textView1 = new TextView(getActivity());
                            textView1.setTextColor(Color.BLACK);

                            textView1.setWidth(width);
                            textView1.setHeight(height);

                            ArrayList<String> list = new ArrayList<String>();
                            for (int j = 0; j < hashMap.get(i); j++)
                                list.add(dats[i][j]);
                            if (list.contains(name.getKey())) {
                                textView1.setText("+");
                                textView1.setTextSize(textsize);
                                textView1.setBackgroundColor(getResources().getColor(R.color.absent_plus));
                            } else {
                                textView1.setText("-");
                                textView1.setTextSize(textsize);
                                textView1.setTextColor(Color.parseColor("#FFFFFF"));
                                textView1.setBackgroundColor(getResources().getColor(R.color.absent_minus));

                            }
                            textView1.setGravity(Gravity.CENTER);

                            TextView space1 = new TextView(getActivity());

                            space1.setWidth(spaceWidth);
                            space1.setHeight(spaceWidth);

                            linearLayout1.addView(textView1);
                            linearLayout1.addView(space1);

                        }

                        //////////////////////////////////////////
                        View v1 = new View(getActivity());
                        v1.setLayoutParams(new LinearLayout.LayoutParams(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                spaceWidth
                        ));
                        v1.setBackgroundColor(Color.parseColor("#E6EE9C"));

                        linearLayout1.setGravity(Gravity.END);
                        LinNames.addView(v1);
                        LinNames.addView(textView);

                        View v = new View(getActivity());
                        v.setLayoutParams(new LinearLayout.LayoutParams(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                spaceWidth
                        ));
                        v.setBackgroundColor(Color.parseColor("#E6EE9C"));

                        lin.addView(v);
                        lin.addView(linearLayout1);

                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                //firstTime = false;
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {

            KashfFragment.nameAdapter.filter("");
            KashfFragment.listView.clearTextFilter();

            if (position == 1) {
                getAbsentData();
                fTime = false;
            } else if (position == 2) {

                FragmentManager fm = getFragmentManager();

                //if you added fragment via layout xml
                EftkadFragment fragment = (EftkadFragment) fm.findFragmentByTag("android:switcher:" + R.id.containerTT + ":" + position);
                fragment.getEftkadData();
            }

        }
    }

    private void screenSize() {

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        String toastMsg;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                textsize = 22;
                width = 70;
                height = 70;
                spaceWidth = 3;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                textsize = 15;
                width = 50;
                height = 50;
                spaceWidth = 1;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                textsize = 10;
                width = 30;
                height = 30;
                spaceWidth = 1;
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                toastMsg = "Xlarge screen";
                textsize = 35;
                width = 150;
                height = 100;
                spaceWidth = 5;
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        height = 80;
        width = 150;
    }

    private void createDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_data_paker);
        dialog.setTitle("تسجيل الغياب ليوم ...");

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker2);

        Button cancel = (Button) dialog.findViewById(R.id.cancelAbsent);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Button continueButton = (Button) dialog.findViewById(R.id.conToAbsent);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, 0, 0, 0);
                long startTime = calendar.getTimeInMillis();
                String date = Long.toString(startTime);
                date = date.substring(0, date.length() - 3);
                date = date + "000";
                String dateShow = Integer.toString(day) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);

                String dayOfWeek = DateFormat.format("EE", new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth() - 1)).toString();

                if (dayOfWeek.equals("Fri")) {
                    Intent abs = new Intent(getActivity(), SetAbsentActivity.class);
                    abs.putExtra("dateShow", dateShow);
                    abs.putExtra("date", date);
                    dialog.dismiss();
                    startActivity(abs);
                } else {
                    Toast.makeText(getActivity(), "You Must Choose FRIDAY ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }


}
