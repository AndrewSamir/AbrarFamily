package com.example.andrewsamir.abrarfamily;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kashf_list_view);

        lv = (ListView) findViewById(R.id.listViewnames);
        lv.setAdapter(na);

        myDB = new DBhelper(this);
        final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


        // Get a reference to our posts
        Firebase ref = new Firebase("https://abrar-family.firebaseio.com/fsol/"+jsonData.getString("fasl","default"));

// Attach an listener to read the data at our posts reference

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
                            "12"));


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


                Cursor c = myDB.getKashfList();

                if (c.moveToFirst()) {
                    np.clear();

                    do {
                        np.add(new Name(c.getString(1), c.getString(4), c.getString(7), c.getString(2), c.getString(13)));
                        // pos.add(Integer.parseInt(c.getString(5))-1);
                    } while (c.moveToNext());
                }
                na = new NameAdapter(np, KashfList.this);


                lv.setAdapter(na);



                /*np.clear();
                for (DataDetails d : datad) {
                    np.add(new Name(d.getName(), d.getPhoto(), d.getRakmManzl(), d.getStreet(), "11"));
                }

                na = new NameAdapter(np, KashfList.this);

                lv.setAdapter(na);*/
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }
}
