package com.example.andrewsamir.abrarfamily.adaptors;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andrewsamir.abrarfamily.R;
import com.example.andrewsamir.abrarfamily.data.Name;

import java.util.ArrayList;

/**
 * Created by andre on 04-Apr-17.
 */

public class SetAbsentAdapter extends BaseAdapter {

    ArrayList<Name> list;
    Activity activity;
    LayoutInflater inflater;

    public SetAbsentAdapter(ArrayList<Name> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.item_set_absent, null);

        TextView Name = (TextView) view.findViewById(R.id.textViewNameItemSetAbsent);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewItemSetAbsent);
        final CardView cardView = (CardView) view.findViewById(R.id.cardViewItemSetAbsent);
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabItemSetAbsent);

        TextView NameFirst = (TextView) view.findViewById(R.id.textViewNameItemSetAbsentFirst);
        ImageView imageViewFirst = (ImageView) view.findViewById(R.id.imageViewItemSetAbsentFirst);
        final CardView cardViewFirst = (CardView) view.findViewById(R.id.cardViewItemSetAbsentFirst);


        final Name x = list.get(i);

        cardViewFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x.setStreet("1");
                cardView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                cardViewFirst.setVisibility(View.GONE);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x.setStreet("0");
                cardView.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                cardViewFirst.setVisibility(View.VISIBLE);
            }
        });

        if (x.getStreet().equals("1")) {
            cardView.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            cardViewFirst.setVisibility(View.GONE);
        }
        Name.setText(x.getName());
        NameFirst.setText((x.getName()));


        if (!x.getImage().equals("e")) {
            try {
                Bitmap bitmap = StringToBitMap(x.getImage());

                imageViewFirst.setImageBitmap(bitmap);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return view;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
