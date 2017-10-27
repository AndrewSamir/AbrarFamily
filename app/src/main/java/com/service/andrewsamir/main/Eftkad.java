package com.service.andrewsamir.abrarfamily;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.service.andrewsamir.abrarfamily.Activities.Data_Show;
import com.service.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.service.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.service.andrewsamir.abrarfamily.data.Name;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 2/17/2016.
 */
public class Eftkad extends ActionBarActivity {

    ListView lv_eftkad;
    NameAdapter na_eftkad;
    public static ArrayList<Name> np_eftkad = new ArrayList<>();
    public ArrayList<Integer> pos = new ArrayList<>();
    DBhelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eftkad);

        np_eftkad.clear();
        myDB = new DBhelper(this);

        lv_eftkad = (ListView) findViewById(R.id.listViewEftkad);


       // Cursor c = myDB.getMeetings();

       /* if (c.moveToFirst()) {
            np_eftkad.clear();

            do {
               *//* np_eftkad.add(new Name(c.getString(1), c.getString(4), c.getString(3), c.getString(2),"wfd"));*//*
                pos.add(Integer.parseInt(c.getString(5)));
            } while (c.moveToNext());
        }
        na_eftkad = new NameAdapter(np_eftkad, Eftkad.this);
*/

        lv_eftkad.setAdapter(na_eftkad);

        lv_eftkad.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {
                        "remove"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Eftkad.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        /////////////////////////////////////
                        myDB.deleteuser(np_eftkad.get(position).getKey());
                        np_eftkad.remove(position);
                        pos.remove(position);

                        na_eftkad.notifyDataSetChanged();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true;
            }
        });

        lv_eftkad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showdata = new Intent(Eftkad.this, Data_Show.class);
                showdata.putExtra("position", pos.get(position));

                startActivity(showdata);

            }
        });

    }
}
