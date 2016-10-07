package com.example.andrewsamir.abrarfamily;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.data.DataDetails;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import static com.example.andrewsamir.abrarfamily.KashfList.datad;


public class MainActivity extends AppCompatActivity {

    String dt;
    ProgressBar progressBar;
    DBhelper myDB;
    DataSnapshot myChild;
    SharedPreferences jsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBarMain);

        myDB = new DBhelper(this);
        jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
        getDataFromFirebase();


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
                        String date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);

                        String dayOfWeek = DateFormat.format("EE", new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth() - 1)).toString();

                        if (dayOfWeek.equals("Fri")) {
                            Intent abs = new Intent(MainActivity.this, Absent.class);
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


                editor.putString("json", null);
                editor.commit();

                Toast.makeText(MainActivity.this, "All Data have been REMOVED ...", Toast.LENGTH_LONG).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        getDataFromFirebase();
        progressBar.setVisibility(View.GONE);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        getDataFromFirebase();
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
