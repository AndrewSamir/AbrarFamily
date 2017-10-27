package com.service.andrewsamir.abrarfamily.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.text.format.DateFormat;
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

import com.service.andrewsamir.abrarfamily.Activities.SetAbsentActivity;
import com.service.andrewsamir.abrarfamily.AddToCalendar.CalendarHelper;
import com.service.andrewsamir.abrarfamily.Activities.Data_Show;
import com.service.andrewsamir.abrarfamily.Enter_Data;
import com.service.andrewsamir.abrarfamily.R;
import com.service.andrewsamir.abrarfamily.Singleton.SingletonDataShow;
import com.service.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.service.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.service.andrewsamir.abrarfamily.data.Name;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by andre on 01-Apr-17.
 */

public class KashfFragment extends Fragment {

    static ListView listView;
    DataSnapshot myChild;
    public static ArrayList<Name> kashfLists;
    static NameAdapter nameAdapter;
    FloatingActionButton floatingActionButton;
    public static boolean firstTime = true;

    DBhelper myDB;
    Dialog dialog;
    DatabaseReference myRef;
    FirebaseDatabase database;

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

                    SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);


                    if (!n.getBirthdate().equals("--") && !n.getBirthdate().equals("e"))
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
                getString(R.string.add_to_eftkad), getString(R.string.add_eftkad_date), getString(R.string.remove)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // builder.setTitle("Choose Photo From ..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                //mDoneButton.setText(items[item]);
                if (items[item].equals(getString(R.string.add_to_eftkad))) {

                    addToEftkad(position);

                } else if (items[item].equals(getString(R.string.add_eftkad_date))) {

                    createDialog(kashfLists.get(position).getKey());
                } else if (items[item].equals(getString(R.string.remove))) {

                    warningRemove(position);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addToEftkad(int position) {

        boolean t = myDB.ADD(kashfLists.get(position).getName(),
                kashfLists.get(position).getHomeNo(),
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


        Long l = Long.parseLong(s.substring(0, 10));

//        Log.e("addedBirthdateB", myChild.getKey() + startTime);


        //addNewEvent();
        boolean birthdate_add = myDB.ADD_BIRTHDATE(name.getName(),
                name.getBirthdate(), l, name.getKey().substring(1));

        if (CalendarHelper.haveCalendarReadWritePermissions(getActivity())) {
            SharedPreferences prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

            if (birthdate_add && prefs.getBoolean("createEvents", false)) {
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

    private void createDialog(final String uuid) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_data_paker);
        dialog.setTitle("تسجيل تاريخ اخر إفتقاد ");

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

                myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/list/" + uuid + "/lastEftkad");
                myRef.setValue(dateShow);
                dialog.dismiss();

            }
        });
        dialog.show();
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
                    nameAdapter.filter("");
                    listView.clearTextFilter();
                } else {
                    nameAdapter.filter(newText);
                }
                return true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        nameAdapter.filter("");
        listView.clearTextFilter();
    }


}
