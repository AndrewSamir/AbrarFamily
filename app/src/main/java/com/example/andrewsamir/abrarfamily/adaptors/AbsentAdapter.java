package com.example.andrewsamir.abrarfamily.adaptors;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andrewsamir.abrarfamily.R;
import com.example.andrewsamir.abrarfamily.data.AbsentData;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 2/24/2016.
 */
public class AbsentAdapter extends BaseAdapter {

    ArrayList<AbsentData> list;
    Activity activity;
    LayoutInflater inflater;

    public AbsentAdapter(ArrayList<AbsentData> list, Activity activity) {
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

        convertView =inflater.inflate(R.layout.absent_tottal,null);
        TextView name= (TextView) convertView.findViewById(R.id.textViewAbsentName);
        TextView abs_1= (TextView) convertView.findViewById(R.id.textViewAbsent_1);
        TextView abs_2= (TextView) convertView.findViewById(R.id.textViewAbsent_2);
        TextView abs_3= (TextView) convertView.findViewById(R.id.textViewAbsent_3);
        TextView abs_4= (TextView) convertView.findViewById(R.id.textViewAbsent_4);


        AbsentData ad=list.get(position);

        name.setText(ad.getName());
        abs_1.setText(ad.getFirst());
        abs_2.setText(ad.getSecond());
        abs_3.setText(ad.getThird());
        abs_4.setText(ad.getFourth());



        return convertView;
    }
}
