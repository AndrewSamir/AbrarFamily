package com.example.andrewsamir.abrarfamily;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.DataDetails;
import com.example.andrewsamir.abrarfamily.data.Name;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 9/24/2016.
 */
public class KashfList extends AppCompatActivity {

    public static ArrayList<DataDetails> datad = new ArrayList<>();
    ListView lv;
    DataSnapshot myChild;
    NameAdapter na;
    ArrayList<Name> np = new ArrayList<>();
    DBhelper myDB;
    int x = 1;
    SharedPreferences jsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kashf_list_view);

        lv = (ListView) findViewById(R.id.listViewnames);
        lv.setAdapter(na);

        myDB = new DBhelper(this);
        jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

        setDataToList();

      //  getDataFromFirebase();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent showData = new Intent(KashfList.this, Data_Show.class);
                Log.d("position",position+""+datad.get(position).getName());
                showData.putExtra("position", position);
                startActivity(showData);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_add:
                Intent in = new Intent(KashfList.this, Enter_Data.class);
                startActivity(in);

            default:
                return true;
        }
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

                    datad.add(new DataDetails(myChild.child("name").getValue().toString(),
                            myChild.child("homeNo").getValue().toString(),
                            myChild.child("street").getValue().toString(),
                            myChild.child("floor").getValue().toString(),
                            myChild.child("flat").getValue().toString(),
                            myChild.child("description").getValue().toString(),
                            myChild.child("anotherAdd").getValue().toString(),
                            myChild.child("papa").getValue().toString(),
                            myChild.child("mama").getValue().toString(),
                            myChild.child("phone").getValue().toString(),
                            myChild.child("birthdate").getValue().toString(),
                            myChild.child("image").getValue().toString(),
                            myChild.getKey()));


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


                }
                setDataToList();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        return true;
    }

    private boolean setDataToList() {

        Cursor c = myDB.getKashfList();

        if (c.moveToFirst()) {
            np.clear();

            do {
                np.add(new Name(c.getString(1), c.getString(4), c.getString(7), c.getString(2), c.getString(13)));
                // pos.add(Integer.parseInt(datePicker.getString(5))-1);
                datad.add(new DataDetails(c.getString(1),
                        c.getString(3),
                        c.getString(2),
                        c.getString(6),
                        c.getString(7),
                        c.getString(11),
                        c.getString(12),
                        c.getString(8),
                        c.getString(9),
                        c.getString(10),
                        c.getString(5),
                        c.getString(4),
                        c.getString(13)));
            } while (c.moveToNext());
        }
        na = new NameAdapter(np, KashfList.this);
        lv.setAdapter(na);
        return true;
    }


}
