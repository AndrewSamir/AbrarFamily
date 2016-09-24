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
public class NameAdapter extends BaseAdapter
{

    ArrayList<Name> list;
    Activity activity;
    LayoutInflater inflater;

    ImageView iv;

    Eftkad eftkad=new Eftkad();

    public NameAdapter(ArrayList<Name> list, Activity activity) {
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

        convertView =inflater.inflate(R.layout.names_list,null);

        TextView First= (TextView) convertView.findViewById(R.id.textViewshowname);
//         iv=(ImageView) convertView.findViewById(R.id.imageView);

        TextView enwan=(TextView) convertView.findViewById(R.id.textViewshowaddr);
        TextView num=(TextView) convertView.findViewById(R.id.textViewnumber);


        Name x=list.get(position);



        First.setText(x.getName());
        num.setText(x.getNumber());

        if(x.getRakm().equals("null")||x.getStreet().equals("null"))
            enwan.setVisibility(View.INVISIBLE);


        else
        enwan.setText(x.getRakm()+" شارع "+x.getStreet());


        NamesAndView container=new NamesAndView();
        container.name=x;
        container.view=convertView;

        ImageLoader loaders=new ImageLoader();
        loaders.execute(container);

        return convertView;
    }


    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


    class NamesAndView{

        public Name name;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<NamesAndView,Void,NamesAndView>{


        @Override
        protected NamesAndView doInBackground(NamesAndView... params) {

            NamesAndView container=params[0];
            Name name=container.name;
            try {


                Bitmap bitmap=StringToBitMap(name.getPhoto());

                container.bitmap=bitmap;
                return container;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(NamesAndView namesAndView) {


            ImageView im= (ImageView) namesAndView.view.findViewById(R.id.imageView);

            im.setImageBitmap(namesAndView.bitmap);

        }
    }
}
