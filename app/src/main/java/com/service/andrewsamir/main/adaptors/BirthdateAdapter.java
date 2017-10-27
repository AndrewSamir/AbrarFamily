package com.service.andrewsamir.abrarfamily.adaptors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.service.andrewsamir.abrarfamily.R;
import com.service.andrewsamir.abrarfamily.data.Birthdate;

import java.util.ArrayList;

/**
 * Created by andre on 20-Oct-16.
 */

public class BirthdateAdapter extends BaseAdapter {

    ArrayList<Birthdate> list;
    Activity activity;
    LayoutInflater inflater;

    public BirthdateAdapter(ArrayList<Birthdate> list, Activity activity) {

        this.list = list;
        this.activity = activity;
        inflater=activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =inflater.inflate(R.layout.birthdate_item,null);

        TextView Name= (TextView) convertView.findViewById(R.id.textViewBirthdateName);
//         iv=(ImageView) convertView.findViewById(R.id.imageView);

        TextView Date=(TextView) convertView.findViewById(R.id.textViewBirthdateDate);


        Birthdate x=list.get(position);



        Name.setText(x.getName());
        Date.setText(x.getBirthdate());





        return convertView;

    }
}
