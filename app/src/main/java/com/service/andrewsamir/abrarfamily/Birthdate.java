package com.example.andrewsamir.abrarfamily;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andrewsamir.abrarfamily.adaptors.BirthdateAdapter;
import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.DataDetails;
import com.example.andrewsamir.abrarfamily.data.Name;

import java.util.ArrayList;

public class Birthdate extends AppCompatActivity {

    ListView listView;
    BirthdateAdapter na;
    ArrayList<com.example.andrewsamir.abrarfamily.data.Birthdate> np = new ArrayList<>();
    DBhelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdate);

        myDB = new DBhelper(this);

        listView = (ListView) findViewById(R.id.listBirthdate);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {
                        "Happy Birthday"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Birthdate.this);
                builder.setTitle("we have wished him/her a");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //mDoneButton.setText(items[item]);
                        if (items[item].equals("Happy Birthday")) {
                            myDB.DoneHappyBirthdate(np.get(position).getId());
                            np.remove(position);
                            na.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                return true;

            }
        });


        Cursor c = myDB.getBirthdate();

        if (c.moveToFirst()) {
            np.clear();

            do {

                if (c.getInt(5) == 1) ;
                else {
                    Long tsLong = System.currentTimeMillis() / 1000;

                    Log.e("getlongda", c.getLong(3) + "");
                    Log.e("getlongnw", tsLong + "");
                    if (c.getLong(3) < tsLong) {
                        np.add(new com.example.andrewsamir.abrarfamily.data.Birthdate(c.getString(1), c.getString(2),c.getInt(0)));
                    }
                }
            } while (c.moveToNext());
        }


        na = new BirthdateAdapter(np, Birthdate.this);

        listView.setAdapter(na);
    }
}
