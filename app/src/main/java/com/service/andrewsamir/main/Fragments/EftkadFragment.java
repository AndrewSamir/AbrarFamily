package com.service.andrewsamir.main.Fragments;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.andrewsamir.main.Activities.Data_Show;
import com.service.andrewsamir.main.R;
import com.service.andrewsamir.main.Singleton.SingletonDataShow;
import com.service.andrewsamir.main.adaptors.DBhelper;
import com.service.andrewsamir.main.adaptors.NameAdapter;
import com.service.andrewsamir.main.data.Name;

import java.util.ArrayList;
import java.util.Calendar;

import static com.service.andrewsamir.main.Fragments.KashfFragment.kashfLists;

/**
 * Created by andre on 01-Apr-17.
 */

public class EftkadFragment extends Fragment {

    ListView lv_eftkad;
    NameAdapter na_eftkad;
    ArrayList<Name> np_eftkad = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();
    DBhelper myDB;
    Dialog dialog;
    DatabaseReference myRef;
    FirebaseDatabase database;

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
                        getString(R.string.add_eftkad_date),
                        getString(R.string.remove_from_eftkad_list)
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        /////////////////////////////////////

                        if (items[item].equals(getString(R.string.remove_from_eftkad_list))) {

                            removeFromEftkadList(position);

                        } else if (items[item].equals(getString(R.string.add_eftkad_date))) {

                            createDialog(kashfLists.get(pos.get(position)).getKey(), position);

                        }

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

                SingletonDataShow.getInstance().setAnotherAdd(np_eftkad.get(position).getAnotherAdd());
                SingletonDataShow.getInstance().setAttendance(np_eftkad.get(position).getAttendance());
                SingletonDataShow.getInstance().setBirthdate(np_eftkad.get(position).getBirthdate());
                SingletonDataShow.getInstance().setDescription(np_eftkad.get(position).getDescription());
                SingletonDataShow.getInstance().setFlat(np_eftkad.get(position).getFlat());
                SingletonDataShow.getInstance().setFloor(np_eftkad.get(position).getFloor());
                SingletonDataShow.getInstance().setHomeNo(np_eftkad.get(position).getHomeNo());
                SingletonDataShow.getInstance().setImage(np_eftkad.get(position).getImage());
                SingletonDataShow.getInstance().setLastEftkad(np_eftkad.get(position).getLastEftkad());
                SingletonDataShow.getInstance().setMama(np_eftkad.get(position).getMama());
                SingletonDataShow.getInstance().setName(np_eftkad.get(position).getName());
                SingletonDataShow.getInstance().setPapa(np_eftkad.get(position).getPapa());
                SingletonDataShow.getInstance().setPhone(np_eftkad.get(position).getPhone());
                SingletonDataShow.getInstance().setPlace(np_eftkad.get(position).getPlace());
                SingletonDataShow.getInstance().setStreet(np_eftkad.get(position).getStreet());
                SingletonDataShow.getInstance().setKey(np_eftkad.get(position).getKey());
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
                pos.clear();

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

    private void removeFromEftkadList(int position) {

        myDB.deleteuser(np_eftkad.get(position).getKey());
        np_eftkad.remove(position);
        pos.remove(position);

        na_eftkad.notifyDataSetChanged();

    }

    private void createDialog(final String uuid, final int position) {

        database = FirebaseDatabase.getInstance();

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
                na_eftkad.updateLastEftkadDate(position, dateShow);
                removeFromEftkadList(position);
                dialog.dismiss();

            }
        });
        dialog.show();
    }


}
