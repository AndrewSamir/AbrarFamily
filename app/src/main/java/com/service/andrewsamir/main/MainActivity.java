package com.service.andrewsamir.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.service.andrewsamir.main.Activities.LogIn;
import com.service.andrewsamir.main.AddToCalendar.CalendarHelper;
import com.service.andrewsamir.main.Services.GetData;
import com.service.andrewsamir.main.adaptors.DBhelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    DBhelper myDB;
    DataSnapshot myChild;
    SharedPreferences jsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"صليلى فى الجيش ",Toast.LENGTH_SHORT).show();

        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        myDB = new DBhelper(this);
        jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
        getDataFromFirebase();

        Intent service = new Intent(this, GetData.class);
       // startService(service);


        Button eftkad = (Button) findViewById(R.id.buttoneftkad);
        eftkad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Eftkad.class);
                startActivity(in);
            }
        });


        Button buttonEnterData = (Button) findViewById(R.id.button);
        buttonEnterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Enter_Data.class);
                startActivity(in);
            }
        });

        Button buttonKashfList = (Button) findViewById(R.id.button2);
        buttonKashfList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, KashfList.class);
                progressBar.setVisibility(View.VISIBLE);
                startActivity(in);
            }
        });

        Button buttonBirthdate = (Button) findViewById(R.id.buttonBirthdate);
        buttonBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Birthdate.class);
                startActivity(in);
            }
        });

        Button buttonAbsent = (Button) findViewById(R.id.buttonabsent);
        buttonAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(MainActivity.this);
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
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        long startTime = calendar.getTimeInMillis();
                        String date = Long.toString(startTime);
                        String dateShow = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);

                        String dayOfWeek = DateFormat.format("EE", new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth() - 1)).toString();

                        if (dayOfWeek.equals("Fri")) {
                            Intent abs = new Intent(MainActivity.this, Absent.class);
                            abs.putExtra("dateShow", dateShow);
                            abs.putExtra("date", date);

                            startActivity(abs);
                        } else {
                            Toast.makeText(MainActivity.this, "you must choose friday ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.show();
                /*Intent abs=new Intent(MainActivity.this,Absent.class);
                startActivity(abs);*/
            }
        });

        Button absentshow = (Button) findViewById(R.id.buttonAbsentShow);
        absentshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abs = new Intent(MainActivity.this, Absent_list.class);
                startActivity(abs);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_remove:

                SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
                SharedPreferences.Editor editor = jsonData.edit();


                editor.putString("logged", null);
                editor.commit();

                myDB.deleteAll();

                Intent intent=new Intent(MainActivity.this,LogIn.class);
                finish();
                startActivity(intent);


                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        // getDataFromFirebase();
        progressBar.setVisibility(View.GONE);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        // getDataFromFirebase();
        progressBar.setVisibility(View.GONE);
        super.onResume();
    }

    private boolean getDataFromFirebase() {

        progressBar.setVisibility(View.VISIBLE);
        Firebase ref = new Firebase("https://abrar-family.firebaseio.com/fsol/" + jsonData.getString("fasl", "default"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                myDB.deleteAll();
                System.out.println(snapshot.getValue());


                Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    int i = 0;
                    myChild = myChildren.iterator().next();


                    try {

                        boolean add = myDB.ADD_KASHF(myChild.child("name").getValue().toString(),
                                myChild.child("homeNo").getValue().toString(),
                                myChild.child("street").getValue().toString(),
                                myChild.child("image").getValue().toString(),
                                Integer.parseInt(myChild.getKey()),
                                myChild.getKey(),
                                myChild.child("birthdate").getValue().toString(),
                                myChild.child("floor").getValue().toString(),
                                myChild.child("flat").getValue().toString(),
                                myChild.child("papa").getValue().toString(),
                                myChild.child("mama").getValue().toString(),
                                myChild.child("phone").getValue().toString(),
                                myChild.child("description").getValue().toString(),
                                myChild.child("anotherAdd").getValue().toString()
                        );

                        String[] birthArray = myChild.child("birthdate").getValue().toString().split("-");
                        Calendar calendar = Calendar.getInstance();
                        int day = Integer.parseInt(birthArray[0]);
                        int month = Integer.parseInt(birthArray[1]);
                        int year;
                        if (month > 8)
                            year = 2017;
                        else
                            year = 2018;
                        calendar.set(year, month-1, day,10,0,0);
                        long startTime = calendar.getTimeInMillis();
                        Log.e("birthdateTimestamp",startTime+"");

                        String s = Long.toString(startTime);

                        Long l =Long.parseLong(s.substring(0,10));

                        Log.e("addedBirthdateB",myChild.getKey()+startTime);

                        boolean birthdate_add=myDB.ADD_BIRTHDATE(myChild.child("name").getValue().toString(),
                                myChild.child("birthdate").getValue().toString(),l,myChild.getKey());

                        if(birthdate_add) {

                            if (CalendarHelper.haveCalendarReadWritePermissions(MainActivity.this)) {
                                //addNewEvent();
                                final long oneHour = 1000 * 60 * 60;
                                CalendarHelper.MakeNewCalendarEntry(MainActivity.this, myChild.child("name").getValue().toString()
                                        , myChild.child("name").getValue().toString()
                                        , "Somewhere"
                                        , startTime, startTime + oneHour, false, true, 1, 3);
                                Log.e("addedBirthdate",myChild.getKey()+startTime);

                            } else {
                                CalendarHelper.requestCalendarReadWritePermission(MainActivity.this);
                            }
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return true;
    }


}
