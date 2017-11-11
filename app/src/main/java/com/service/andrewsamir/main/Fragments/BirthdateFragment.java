package com.service.andrewsamir.main.Fragments;

import android.content.DialogInterface;
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

import com.service.andrewsamir.main.R;
import com.service.andrewsamir.main.adaptors.BirthdateAdapter;
import com.service.andrewsamir.main.adaptors.DBhelper;
import com.service.andrewsamir.main.data.Birthdate;

import java.util.ArrayList;

/**
 * Created by andre on 01-Apr-17.
 */

public class BirthdateFragment extends Fragment {

    ListView listView;
    BirthdateAdapter na;
    ArrayList<Birthdate> np = new ArrayList<>();
    DBhelper myDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_birthdate, null);

        KashfFragment.firstTime = true;

        myDB = new DBhelper(getActivity());

        listView = (ListView) v.findViewById(R.id.listBirthdateNew);

        Cursor c = null;
        try {
            c = myDB.getBirthdate();


        if (c.moveToFirst()) {
            np.clear();

            do {

                if (c.getInt(5) == 1) ;
                else {
                    Long tsLong = System.currentTimeMillis() / 1000;

                    if (c.getLong(3) < tsLong) {
                        np.add(new com.service.andrewsamir.main.data.Birthdate(c.getString(1), c.getString(2), c.getString(0)));
                    }
                }
            } while (c.moveToNext());
        }


        na = new BirthdateAdapter(np, getActivity());

        listView.setAdapter(na);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                showDialogHappyBirthdate(position);
                return true;
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    private void showDialogHappyBirthdate(final int position) {
        final CharSequence[] items = {
                "Happy Birthday"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    }
}
