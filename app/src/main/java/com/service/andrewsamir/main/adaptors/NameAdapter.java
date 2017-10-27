package com.service.andrewsamir.abrarfamily.adaptors;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.service.andrewsamir.abrarfamily.R;
import com.service.andrewsamir.abrarfamily.data.Name;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Andrew Samir on 1/28/2016.
 */
public class NameAdapter extends BaseAdapter {

    ArrayList<Name> list;
    ArrayList<Name> searchList;

    Activity activity;
    LayoutInflater inflater;

    ImageView iv;

    public NameAdapter(ArrayList<Name> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();

        this.searchList = new ArrayList<>();
        this.searchList.addAll(list);
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
        else {
            try {
                Date date = new Date(Long.parseLong(x.getAttendance())); // *1000 is to convert seconds to milliseconds
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
                String formattedDate = sdf.format(date);
                tvLastAttendance.setText(formattedDate);
            } catch (Exception e) {
                e.printStackTrace();
                tvLastAttendance.setText("-");
            }
        }
        Log.d("lastImage", x.getKey());
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

    public void updateLastEftkadDate(int position, String lastEftkad) {
        list.get(position).setLastEftkad(lastEftkad);
        notifyDataSetChanged();
    }

    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(searchList);
        } else {
            for (Name s : searchList) {
                if (s.getName().contains(charText)) {
                    list.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }
}
