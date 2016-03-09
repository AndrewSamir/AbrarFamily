package com.example.andrewsamir.abrarfamily;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.Name;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 2/17/2016.
 */
public class Eftkad extends ActionBarActivity {

    ListView lv_eftkad;
    NameAdapter na_eftkad;
   public static ArrayList<Name> np_eftkad = new ArrayList<>();

    JSONArray arr;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eftkad);

        settings = this.getSharedPreferences("jsonDataEftkad", Context.MODE_PRIVATE);
        np_eftkad.clear();
        String st=settings.getString("set", null);
        try {

            arr=new JSONArray(st);
            for(int i = 0; i < arr .length(); i++) {
                JSONObject jsonObject = arr.getJSONObject(i);


                np_eftkad.add(new Name(jsonObject.get("name").toString(),
                        jsonObject.get("photo").toString(),
                        jsonObject.get("rakam").toString(),
                        jsonObject.get("street").toString(),
                        jsonObject.get("int").toString()));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        lv_eftkad =(ListView) findViewById(R.id.listViewEftkad);

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
                        np_eftkad.remove(position);
                        na_eftkad.notifyDataSetChanged();
                        arr=new JSONArray();
                        for(Name name:np_eftkad){
                            JSONObject data=new JSONObject();
                            int x=1;
                            try {
                                data.put("name",name.getName());
                                data.put("photo",name.getPhoto());
                                data.put("rakam",name.getRakm());
                                data.put("street",name.getStreet());
                                data.put("int",Integer.toString(x++));

                                arr.put(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            SharedPreferences.Editor editor = settings.edit();
                            try {
                                editor.putString("set", arr.toString());
                                editor.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



                return true;
            }
        });


    }
}
