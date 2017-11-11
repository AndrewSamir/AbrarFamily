package com.service.andrewsamir.main.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.service.andrewsamir.main.AddToCalendar.CalendarHelper;
import com.service.andrewsamir.main.Activities.Data_Show;
import com.service.andrewsamir.main.Enter_Data;
import com.service.andrewsamir.main.R;
import com.service.andrewsamir.main.Singleton.SingletonDataShow;
import com.service.andrewsamir.main.adaptors.DBhelper;
import com.service.andrewsamir.main.adaptors.KashfAdapter;
import com.service.andrewsamir.main.adaptors.NameAdapter;
import com.service.andrewsamir.main.data.Name;
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

    DataSnapshot myChild;
    public static ArrayList<Name> kashfLists;
    FloatingActionButton floatingActionButton;
    public static boolean firstTime = true;

    DBhelper myDB;
    Dialog dialog;
    DatabaseReference myRef;
    FirebaseDatabase database;


    private RecyclerView recyclerviewFragmentKashf;
    public static KashfAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kashf, null);

        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fabFragmentKashf);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Enter_Data.class));
            }
        });

        recyclerviewFragmentKashf = (RecyclerView) v.findViewById(R.id.recyclerviewFragmentKashf);

        // mAdapter = new KashfAdapter(kashfLists);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewFragmentKashf.setLayoutManager(mLayoutManager);
        recyclerviewFragmentKashf.setItemAnimator(new DefaultItemAnimator());
        // recyclerviewFragmentKashf.setAdapter(mAdapter);

        myDB = new DBhelper(getContext());

        getData();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void getData() {

        database = FirebaseDatabase.getInstance();
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

                    if (!n.getBirthdate().equals("--") && !n.getBirthdate().equals("e"))
                        setBirthdate(n);
                }


                //=================================================//
                mAdapter = new KashfAdapter(kashfLists, getActivity());
                recyclerviewFragmentKashf.setAdapter(mAdapter);
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


        Long l = Long.parseLong(s.substring(0, 10));


        //addNewEvent();
        boolean birthdate_add = myDB.ADD_BIRTHDATE(name.getName(),
                name.getBirthdate(), l, name.getKey().substring(1));

        //  if (CalendarHelper.haveCalendarReadWritePermissions(getActivity())) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            // CalendarHelper.requestCalendarReadWritePermission(getActivity());

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CALENDAR,
                            Manifest.permission.WRITE_CALENDAR},
                    0);

        } else {

            SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

            if (birthdate_add && prefs.getBoolean("createEvents", false)) {
                final long oneHour = 1000 * 60 * 60;
                CalendarHelper.MakeNewCalendarEntry(getActivity(), name.getName()
                        , name.getName()
                        , "كنيسة مارمرقس شبرا"
                        , startTime, startTime + oneHour, false, true, 1, 3);
            }
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        inflater.inflate(R.menu.home, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    try {
                        mAdapter.filter("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  listView.clearTextFilter();
                } else {
                    try {
                        mAdapter.filter(newText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (permissions[0].equals(Manifest.permission.READ_CALENDAR)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                proceedWithSdCard();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_new_member:
                // write your code here
                startActivity(new Intent(getActivity(), Enter_Data.class));
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mAdapter.filter("");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        listView.clearTextFilter();
    }


}
