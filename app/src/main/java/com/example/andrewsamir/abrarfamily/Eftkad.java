package com.example.andrewsamir.abrarfamily;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.DataDetails;
import com.example.andrewsamir.abrarfamily.data.Name;
import com.example.andrewsamir.abrarfamily.jsondata.DataInJson;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 2/17/2016.
 */
public class Eftkad extends ActionBarActivity {

    ListView lv_eftkad;
    NameAdapter na_eftkad;
   public static ArrayList<Name> np_eftkad = new ArrayList<>();
    public ArrayList<Integer> pos=new ArrayList<>();
    DBhelper myDB;
    Kash_List kashList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eftkad);

        np_eftkad.clear();
        myDB=new DBhelper(this);

        lv_eftkad =(ListView) findViewById(R.id.listViewEftkad);


        Cursor c=myDB.getMeetings();

        if(c.moveToFirst()){
            np_eftkad.clear();

            do{
                np_eftkad.add(new Name(c.getString(1), c.getString(4), c.getString(3), c.getString(2), c.getString(5)));
                pos.add(Integer.parseInt(c.getString(5))-1);
            }while (c.moveToNext());
        }
        na_eftkad = new NameAdapter(np_eftkad, Eftkad.this);


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
                        myDB.deleteuser(np_eftkad.get(position).getNumber());
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
        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


        Gson gson = new Gson();


        DataInJson dd = gson.fromJson(jsonData.getString("json", null), DataInJson.class);
        kashList.datad.clear();
        for (int i = 13; i < dd.getFeed().getEntry().size(); ) {

            kashList.datad.add(new DataDetails(dd.getFeed().getEntry().get(i).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 1).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 2).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 3).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 4).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 5).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 6).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 7).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 8).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 9).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 10).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 11).getContent().get$t(),
                    dd.getFeed().getEntry().get(i + 12).getContent().get$t()));
            i = i + 13;
        }

    }
        }
