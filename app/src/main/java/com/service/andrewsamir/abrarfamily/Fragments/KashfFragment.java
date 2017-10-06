package com.example.andrewsamir.abrarfamily.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andrewsamir.abrarfamily.AddToCalendar.CalendarHelper;
import com.example.andrewsamir.abrarfamily.Activities.Data_Show;
import com.example.andrewsamir.abrarfamily.Enter_Data;
import com.example.andrewsamir.abrarfamily.R;
import com.example.andrewsamir.abrarfamily.Singleton.SingletonDataShow;
import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.Name;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre on 01-Apr-17.
 */

public class KashfFragment extends Fragment {

    ListView listView;
    DataSnapshot myChild;
    public static ArrayList<Name> kashfLists;
    NameAdapter nameAdapter;
    FloatingActionButton floatingActionButton;
    public static boolean firstTime = true;

    DBhelper myDB;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kashf, null);
        listView = (ListView) v.findViewById(R.id.listViewFragmentKashf);
        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fabFragmentKashf);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Enter_Data.class));
            }
        });

        myDB = new DBhelper(getContext());

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                itemLongClick(position);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SingletonDataShow.getInstance().setAnotherAdd(kashfLists.get(position).getAnotherAdd());

                SingletonDataShow.getInstance().setAttendance(kashfLists.get(position).getAttendance());
                SingletonDataShow.getInstance().setBirthdate(kashfLists.get(position).getBirthdate());
                SingletonDataShow.getInstance().setDescription(kashfLists.get(position).getDescription());
                SingletonDataShow.getInstance().setFlat(kashfLists.get(position).getFlat());
                SingletonDataShow.getInstance().setFloor(kashfLists.get(position).getFloor());
                SingletonDataShow.getInstance().setHomeNo(kashfLists.get(position).getHomeNo());
                SingletonDataShow.getInstance().setImage(kashfLists.get(position).getImage());
                SingletonDataShow.getInstance().setLastEftkad(kashfLists.get(position).getLastEftkad());
                SingletonDataShow.getInstance().setMama(kashfLists.get(position).getMama());
                SingletonDataShow.getInstance().setName(kashfLists.get(position).getName());
                SingletonDataShow.getInstance().setPapa(kashfLists.get(position).getPapa());
                SingletonDataShow.getInstance().setPhone(kashfLists.get(position).getPhone());
                SingletonDataShow.getInstance().setPlace(kashfLists.get(position).getPlace());
                SingletonDataShow.getInstance().setStreet(kashfLists.get(position).getStreet());
                SingletonDataShow.getInstance().setKey(kashfLists.get(position).getKey());

                startActivity(new Intent(getActivity(), Data_Show.class));
            }
        });


        getData();
        return v;
    }

    DatabaseReference myRef;

    private void getData() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.keepSynced(true);
        // Read from the database
        myRef.child("list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                kashfLists = new ArrayList<Name>();
                //=================================================//

                Iterable<DataSnapshot> nameChildren = dataSnapshot.getChildren();

                for (DataSnapshot name : nameChildren) {

                    Name n = name.getValue(Name.class);
                    n.setKey(name.getKey());
                    Log.d("contact:: ", n.getName());

                    kashfLists.add(n);

                    SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);


                    if (!n.getBirthdate().equals("--") && !n.getBirthdate().equals("e") && prefs.getBoolean("createEvents", false))
                        setBirthdate(n);
                }


                //=================================================//


                nameAdapter = new NameAdapter(kashfLists, getActivity());
                listView.setAdapter(nameAdapter);
                if (firstTime) {
                    //  getAbsentData();
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

        myRef.child("data").child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putString("nameFasl", dataSnapshot.getValue().toString());
                editor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void itemLongClick(final int position) {

        final CharSequence[] items = {
                "Eftkad", "Remove"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Photo From ..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                //mDoneButton.setText(items[item]);
                if (items[item].equals("Eftkad")) {

                    addToEftkad(position);

                } else if (items[item].equals("Remove")) {

                    warningRemove(position);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addToEftkad(int position) {

        boolean t = myDB.ADD(kashfLists.get(position).getName(),
                kashfLists.get(position).getFloor(),
                kashfLists.get(position).getStreet(),
                kashfLists.get(position).getImage(),
                kashfLists.get(position).getKey(),
                Integer.toString(position),
                kashfLists.get(position).getLastEftkad(),
                kashfLists.get(position).getAttendance());

        if (t)
            Toast.makeText(getContext(), "تم اضافة " + kashfLists.get(position).getName() + " الى الافتقاد ", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), kashfLists.get(position).getName() + "  موجود فى الافتقاد ", Toast.LENGTH_SHORT).show();

    }

    private void warningRemove(final int position) {

        new AlertDialog.Builder(getContext())
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete " + kashfLists.get(position).getName() + " ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        removeFromFirebase(position);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void removeFromFirebase(int position) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.child("list").child(kashfLists.get(position).getKey()).removeValue();

    }

    private void setBirthdate(Name name) {

        Log.e("birthdate", name.getBirthdate());
        String[] birthArray = name.getBirthdate().split("-");
        Calendar calendar = Calendar.getInstance();
        int day = Integer.parseInt(birthArray[0]);
        int month = Integer.parseInt(birthArray[1]);
        int year;
        if (month > 8)
            year = 2017;
        else
            year = 2018;
        calendar.set(year, month - 1, day, 10, 0, 0);
        long startTime = calendar.getTimeInMillis();
        Log.e("birthdateTimestamp", startTime + "");

        String s = Long.toString(startTime);
        Long tsLong = System.currentTimeMillis() / 1000;

        if (startTime/1000 < tsLong) {

            myRef.child("list").child(name.getKey()).child("birthdateCame").setValue(1);
        }

        Long l = Long.parseLong(s.substring(0, 10));

//        Log.e("addedBirthdateB", myChild.getKey() + startTime);


        if (CalendarHelper.haveCalendarReadWritePermissions(getActivity())) {
            //addNewEvent();
            boolean birthdate_add = myDB.ADD_BIRTHDATE(name.getName(),
                    name.getBirthdate(), l, name.getKey());

            if (birthdate_add) {
                final long oneHour = 1000 * 60 * 60;
                CalendarHelper.MakeNewCalendarEntry(getActivity(), name.getName()
                        , name.getName()
                        , "كنيسة مارمرقس شبرا"
                        , startTime, startTime + oneHour, false, true, 1, 3);

                // should be changed to choose which calendar

                //    Log.e("addedBirthdate", myChild.getKey() + startTime);

            }
        } else {
            CalendarHelper.requestCalendarReadWritePermission(getActivity());
        }


    }

}
