package com.service.andrewsamir.main.Services;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.service.andrewsamir.main.AddToCalendar.CalendarHelper;
import com.service.andrewsamir.main.adaptors.DBhelper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;

/**
 * Created by andre on 24-Oct-16.
 */

public class GetData  extends Service {

    SharedPreferences jsonData;
    DBhelper myDB;
    DataSnapshot myChild;
    Activity activity;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO do something useful
        //HFLAG = true;
        //smsHandler.sendEmptyMessageDelayed(DISPLAY_DATA, 1000);

        jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
        myDB = new DBhelper(this);
       // activity=

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO for communication return IBinder implementation
        return null;
    }

    private boolean getDataFromFirebase() {

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
                        if (month > 9)
                            year = 2016;
                        else
                            year = 2017;
                        calendar.set(year, month-1, day,10,0,0);
                        long startTime = calendar.getTimeInMillis();
                        Log.e("birthdateTimestamp",startTime+"");

                        String s = Long.toString(startTime);

                        Long l =Long.parseLong(s.substring(0,10));

                        Log.e("addedBirthdateB",myChild.getKey()+startTime);

                        boolean birthdate_add=myDB.ADD_BIRTHDATE(myChild.child("name").getValue().toString(),
                                myChild.child("birthdate").getValue().toString(),l,myChild.getKey());

                        if(birthdate_add) {

                            if (CalendarHelper.haveCalendarReadWritePermissions(activity)) {
                                //addNewEvent();
                                final long oneHour = 1000 * 60 * 60;
                                CalendarHelper.MakeNewCalendarEntry(activity, myChild.child("name").getValue().toString()
                                        , myChild.child("name").getValue().toString()
                                        , "Somewhere"
                                        , startTime, startTime + oneHour, false, true, 1, 3);
                                Log.e("addedBirthdate",myChild.getKey()+startTime);

                            } else {
                                CalendarHelper.requestCalendarReadWritePermission(activity);
                            }
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return true;
    }
}