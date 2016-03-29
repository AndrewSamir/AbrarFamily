package com.example.andrewsamir.abrarfamily;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.andrewsamir.abrarfamily.data.DataDetails;

/**
 * Created by Andrew Samir on 1/30/2016.
 */
public class Data_Show extends ActionBarActivity {
Kash_List kl;
    DataDetails d;
     int  pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



       pos=getIntent().getIntExtra("position",0);

        RelativeLayout linphone= (RelativeLayout) findViewById(R.id.callphone);
        RelativeLayout linbaba= (RelativeLayout) findViewById(R.id.callbaba);
        RelativeLayout linmama= (RelativeLayout) findViewById(R.id.callmama);

        RelativeLayout linother= (RelativeLayout) findViewById(R.id.Linanother);
        RelativeLayout lindesc= (RelativeLayout) findViewById(R.id.Lindesc);

        ImageView imv= (ImageView) findViewById(R.id.imageView2);
        imv.setImageBitmap(StringToBitMap(kl.datad.get(pos).getPhoto()));

        String[] names=kl.datad.get(pos).getName().split(" ");

        TextView name= (TextView) findViewById(R.id.textViewshowname);
        //name.setText(kl.datad.get(pos).getName());
        name.setText(names[0]);
        String last=kl.datad.get(pos).getName().substring(names[0].length());


        TextView lastname= (TextView) findViewById(R.id.textViewlastname);
        lastname.setText(last);


        TextView add= (TextView) findViewById(R.id.textViewshowrakmmanzl);
        add.setText(kl.datad.get(pos).getRakmManzl()+" شارع  "+kl.datad.get(pos).getStreet());

        TextView floor= (TextView) findViewById(R.id.textViewshowfloor);
        floor.setText("الدور  "+kl.datad.get(pos).getFloor());

        TextView homen= (TextView) findViewById(R.id.textViewshowhomen);
        homen.setText(" شقه  "+kl.datad.get(pos).getHomenumber());

        TextView baba= (TextView) findViewById(R.id.textViewshowbaba);
        baba.setText(kl.datad.get(pos).getBaba());

        TextView mama= (TextView) findViewById(R.id.textViewshowmama);
        mama.setText(kl.datad.get(pos).getMama());

        TextView phone= (TextView) findViewById(R.id.textViewshowphone);
        phone.setText(kl.datad.get(pos).getPhone());

        TextView birth= (TextView) findViewById(R.id.textViewshowbirthday);
        birth.setText(kl.datad.get(pos).getBirthday());

        TextView desc= (TextView) findViewById(R.id.textViewshowdesc);
        desc.setText(kl.datad.get(pos).getDescription());

        TextView another= (TextView) findViewById(R.id.textViewshowanotheraddr);
        another.setText(kl.datad.get(pos).getAnotherAdd());

        ImageButton callbaba= (ImageButton) findViewById(R.id.buttoncallbaba);
        ImageButton callmama= (ImageButton) findViewById(R.id.buttoncallmama);
        ImageButton callphone= (ImageButton) findViewById(R.id.buttoncallphone);

        callbaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String   posted_by = kl.datad.get(pos).getBaba();
                String uri = "tel:" + posted_by.trim() ;
                Intent in =new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse(uri));
                startActivity(in);
            }
        });

        callmama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String   posted_by = kl.datad.get(pos).getMama();
                String uri = "tel:" + posted_by.trim() ;
                Intent in =new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse(uri));
                startActivity(in);
            }
        });

        callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String   posted_by;
                if(kl.datad.get(pos).getPhone().length()==8)
                    posted_by = "02"+kl.datad.get(pos).getPhone();
                else
                    posted_by=kl.datad.get(pos).getPhone();
                String uri = "tel:" + posted_by.trim() ;
                Intent in =new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse(uri));
                startActivity(in);
            }
        });

        if(kl.datad.get(pos).getBaba().equals("null"))
            linbaba.setVisibility(View.GONE);

        if(kl.datad.get(pos).getMama().equals("null"))
            linmama.setVisibility(View.GONE);

        if(kl.datad.get(pos).getPhone().equals("null"))
            linphone.setVisibility(View.GONE);

        if(kl.datad.get(pos).getAnotherAdd().equals("null"))
            lindesc.setVisibility(View.GONE);

        if(kl.datad.get(pos).getDescription().equals("null"))
            linother.setVisibility(View.GONE);

        if(kl.datad.get(pos).getBirthday().equals("null"))
            birth.setVisibility(View.GONE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_data, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_edit:
                Intent edit=new Intent(Data_Show.this,Edit_Data.class);
              //  edit.putExtra("position_edit", Integer.parseInt(pos));
                edit.putExtra("position_edit",pos);

                startActivity(edit);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
