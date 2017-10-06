package com.example.andrewsamir.abrarfamily.adaptors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrewsamir.abrarfamily.Eftkad;
import com.example.andrewsamir.abrarfamily.R;
import com.example.andrewsamir.abrarfamily.data.Name;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 1/28/2016.
 */
public class NameAdapter extends BaseAdapter {

    ArrayList<Name> list;
    Activity activity;
    LayoutInflater inflater;

    ImageView iv;

    public NameAdapter(ArrayList<Name> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
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

        convertView = inflater.inflate(R.layout.names_list, null);


        // intiate views
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewshowname);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.textViewshowaddr);
        TextView num = (TextView) convertView.findViewById(R.id.textViewnumber);
        TextView tvLastEftkad = (TextView) convertView.findViewById(R.id.tvLastEftkad);
        TextView tvLastAttendance = (TextView) convertView.findViewById(R.id.tvLastAttendance);


        Name x = list.get(position);


        tvName.setText(x.getName());
        num.setText(position + 1 + "");

        if (x.getHomeNo().equals("e") || x.getStreet().equals("e"))
            tvAddress.setVisibility(View.INVISIBLE);
        else
            tvAddress.setText(x.getHomeNo() + " شارع " + x.getStreet());

        if (x.getLastEftkad().equals("e"))
            tvLastEftkad.setText("-");
        else
            tvLastEftkad.setText(x.getLastEftkad());

        if (x.getAttendance().equals("e"))
            tvLastAttendance.setText("-");
        else
            tvLastAttendance.setText(x.getAttendance());

        if (!x.getImage().equals("e")) {
            try {
                Bitmap bitmap = StringToBitMap(x.getImage());
                de.hdodenhof.circleimageview.CircleImageView circleImageView = (de.hdodenhof.circleimageview.CircleImageView) convertView.findViewById(R.id.ivProfile);

                circleImageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


   /*     NamesAndView container = new NamesAndView();
        container.name = x;
        container.view = convertView;

        ImageLoader loaders = new ImageLoader();
        loaders.execute(container);*/

        return convertView;
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


    class NamesAndView {

        public Name name;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<NamesAndView, Void, NamesAndView> {


        @Override
        protected NamesAndView doInBackground(NamesAndView... params) {

            NamesAndView container = params[0];
            Name name = container.name;
            try {


                Bitmap bitmap = StringToBitMap(name.getImage());

                container.bitmap = bitmap;
                return container;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(NamesAndView namesAndView) {


            de.hdodenhof.circleimageview.CircleImageView circleImageView = (de.hdodenhof.circleimageview.CircleImageView) namesAndView.view.findViewById(R.id.ivProfile);

            circleImageView.setImageBitmap(namesAndView.bitmap);

        }
    }
}
