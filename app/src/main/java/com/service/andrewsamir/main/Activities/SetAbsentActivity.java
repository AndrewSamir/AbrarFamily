package com.service.andrewsamir.abrarfamily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.service.andrewsamir.abrarfamily.R;
import com.service.andrewsamir.abrarfamily.adaptors.SetAbsentAdapter;
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

public class SetAbsentActivity extends AppCompatActivity {


    DataSnapshot myChild;
    public static ArrayList<Name> kashfLists;
    public static ArrayList<String> nameList;
    private ArrayList<String> keysList;

    ListView listView;
    SetAbsentAdapter setAbsentAdapter;
    String dateShow;


    Iterable<DataSnapshot> nameChildren;
    JSONArray jsonArray;

    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_absent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listViewSetAbsentActivity);
        save = (Button) findViewById(R.id.buttonSaveSetAbsent);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        jsonArray = new JSONArray();
        Intent intent = getIntent();
        dateShow = intent.getStringExtra("date");
        getData(dateShow);

    }

    private void getData(final String date) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                kashfLists = new ArrayList<Name>();
                nameList = new ArrayList<>();
                keysList = new ArrayList<String>();
                int number = 0;
                Iterable<DataSnapshot> myChildren = dataSnapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    myChild = myChildren.iterator().next();
                    if (myChild.getKey().equals("data")) {

                        try {
                            JSONObject jsonObject = new JSONObject(myChild.child("absent").child(date).getValue().toString());
                            Log.d("gottenAbsent", jsonObject.toString());
                            jsonArray = jsonObject.getJSONArray("absent");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (myChild.getKey().equals("list")) {
                        nameChildren = dataSnapshot.child("list").getChildren();
                    }
                }
                initAbsentNames();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


    private void initAbsentNames() {


        for (int i = 0; i < jsonArray.length(); i++) {

            String string = null;
            try {
                string = jsonArray.getString(i);
                keysList.add(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //kashfLists.get(nameList.indexOf(string)).setStreet("1");
        }

        for (DataSnapshot name : nameChildren) {

            Name n = name.getValue(Name.class);
            n.setKey(name.getKey());

            if (keysList.contains(n.getKey()))
                n.setStreet("1");
            kashfLists.add(n);
        }
        nameList.add(myChild.getKey());

        setAbsentAdapter = new SetAbsentAdapter(kashfLists, SetAbsentActivity.this);
        listView.setAdapter(setAbsentAdapter);
    }

    private void save() {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Name name : kashfLists) {
                if (name.getStreet().equals("1"))
                    jsonArray.put(name.getKey());
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("absent", jsonArray);

            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/data/absent/"
                    + dateShow);

            myRef.setValue(jsonObject.toString());
            super.onBackPressed();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
