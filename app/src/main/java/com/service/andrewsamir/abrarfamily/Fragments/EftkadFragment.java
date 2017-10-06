package com.example.andrewsamir.abrarfamily.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.andrewsamir.abrarfamily.Activities.Data_Show;
import com.example.andrewsamir.abrarfamily.R;
import com.example.andrewsamir.abrarfamily.Singleton.SingletonDataShow;
import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.Name;

import java.util.ArrayList;

import static com.example.andrewsamir.abrarfamily.Fragments.KashfFragment.kashfLists;

/**
 * Created by andre on 01-Apr-17.
 */

public class EftkadFragment extends Fragment {

    ListView lv_eftkad;
    NameAdapter na_eftkad;
    ArrayList<Name> np_eftkad = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();
    DBhelper myDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.eftkad, null);

        myDB = new DBhelper(getContext());
        lv_eftkad = (ListView) v.findViewById(R.id.listViewEftkad);

        getEftkadData();

        lv_eftkad.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] items = {
                        "remove"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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


                SingletonDataShow.getInstance().setAnotherAdd(kashfLists.get(pos.get(position)).getAnotherAdd());
                SingletonDataShow.getInstance().setAttendance(kashfLists.get(pos.get(position)).getAttendance());
                SingletonDataShow.getInstance().setBirthdate(kashfLists.get(pos.get(position)).getBirthdate());
                SingletonDataShow.getInstance().setDescription(kashfLists.get(pos.get(position)).getDescription());
                SingletonDataShow.getInstance().setFlat(kashfLists.get(pos.get(position)).getFlat());
                SingletonDataShow.getInstance().setFloor(kashfLists.get(pos.get(position)).getFloor());
                SingletonDataShow.getInstance().setHomeNo(kashfLists.get(pos.get(position)).getHomeNo());
                SingletonDataShow.getInstance().setImage(kashfLists.get(pos.get(position)).getImage());
                SingletonDataShow.getInstance().setLastEftkad(kashfLists.get(pos.get(position)).getLastEftkad());
                SingletonDataShow.getInstance().setMama(kashfLists.get(pos.get(position)).getMama());
                SingletonDataShow.getInstance().setName(kashfLists.get(pos.get(position)).getName());
                SingletonDataShow.getInstance().setPapa(kashfLists.get(pos.get(position)).getPapa());
                SingletonDataShow.getInstance().setPhone(kashfLists.get(pos.get(position)).getPhone());
                SingletonDataShow.getInstance().setPlace(kashfLists.get(pos.get(position)).getPlace());
                SingletonDataShow.getInstance().setStreet(kashfLists.get(pos.get(position)).getStreet());
                SingletonDataShow.getInstance().setKey(kashfLists.get(pos.get(position)).getKey());
                Intent showdata = new Intent(getActivity(), Data_Show.class);
                //  showdata.putExtra("position", pos.get(position));

                startActivity(showdata);

            }
        });


        return v;
    }

    public void getEftkadData() {
        np_eftkad.clear();

        Cursor c = null;
        try {
            c = myDB.getMeetings();


            if (c.moveToFirst()) {
                np_eftkad.clear();

                do {
                    np_eftkad.add(new Name(c.getString(3), c.getString(4), c.getString(5), c.getString(1), c.getString(2), c.getString(7), c.getString(6)));
                    pos.add(Integer.parseInt(c.getString(7)));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        na_eftkad = new NameAdapter(np_eftkad, getActivity());


        lv_eftkad.setAdapter(na_eftkad);

    }
}
