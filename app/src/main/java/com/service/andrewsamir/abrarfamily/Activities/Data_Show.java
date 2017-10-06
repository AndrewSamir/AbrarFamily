package com.example.andrewsamir.abrarfamily.Activities;

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

import com.example.andrewsamir.abrarfamily.Edit_Data;
import com.example.andrewsamir.abrarfamily.R;
import com.example.andrewsamir.abrarfamily.Singleton.SingletonDataShow;

/**
 * Created by Andrew Samir on 1/30/2016.
 */
public class Data_Show extends ActionBarActivity {
    // KashfList kashfList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RelativeLayout linphone = (RelativeLayout) findViewById(R.id.callphone);
        RelativeLayout linbaba = (RelativeLayout) findViewById(R.id.callbaba);
        RelativeLayout linmama = (RelativeLayout) findViewById(R.id.callmama);

        RelativeLayout linother = (RelativeLayout) findViewById(R.id.Linanother);
        RelativeLayout lindesc = (RelativeLayout) findViewById(R.id.Lindesc);

        ImageView imv = (ImageView) findViewById(R.id.imageView2);
        // imv.setImageBitmap(StringToBitMap(kashfList.datad.get(pos).getPhoto()));
        imv.setImageBitmap(StringToBitMap(SingletonDataShow.getInstance().getImage()));

        String[] names = SingletonDataShow.getInstance().getName().split(" ");

        TextView name = (TextView) findViewById(R.id.textViewshowname);
        //name.setText(kashfList.datad.get(pos).getName());
        name.setText(names[0]);
        String last = SingletonDataShow.getInstance().getName().substring(names[0].length());


        TextView lastname = (TextView) findViewById(R.id.textViewlastname);
        lastname.setText(last);


        TextView add = (TextView) findViewById(R.id.textViewshowrakmmanzl);
        add.setText(SingletonDataShow.getInstance().getHomeNo() + " شارع  " + SingletonDataShow.getInstance().getStreet());

        TextView floor = (TextView) findViewById(R.id.textViewshowfloor);
        floor.setText("الدور  " + SingletonDataShow.getInstance().getFloor());

        TextView homen = (TextView) findViewById(R.id.textViewshowhomen);
        homen.setText(" شقه  " + SingletonDataShow.getInstance().getHomeNo());

        TextView baba = (TextView) findViewById(R.id.textViewshowbaba);
        baba.setText(SingletonDataShow.getInstance().getPapa());

        TextView mama = (TextView) findViewById(R.id.textViewshowmama);
        mama.setText(SingletonDataShow.getInstance().getMama());

        TextView phone = (TextView) findViewById(R.id.textViewshowphone);
        phone.setText(SingletonDataShow.getInstance().getPhone());

        TextView birth = (TextView) findViewById(R.id.textViewshowbirthday);
        birth.setText(SingletonDataShow.getInstance().getBirthdate());

        TextView desc = (TextView) findViewById(R.id.textViewshowdesc);
        desc.setText(SingletonDataShow.getInstance().getDescription());

        TextView another = (TextView) findViewById(R.id.textViewshowanotheraddr);
        another.setText(SingletonDataShow.getInstance().getAnotherAdd());

        ImageButton callbaba = (ImageButton) findViewById(R.id.buttoncallbaba);
        ImageButton callmama = (ImageButton) findViewById(R.id.buttoncallmama);
        ImageButton callphone = (ImageButton) findViewById(R.id.buttoncallphone);

        callbaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posted_by = SingletonDataShow.getInstance().getPapa();
                String uri = "tel:" + posted_by.trim();
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse(uri));
                startActivity(in);
            }
        });

        callmama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posted_by = SingletonDataShow.getInstance().getMama();
                String uri = "tel:" + posted_by.trim();
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse(uri));
                startActivity(in);
            }
        });

        callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String posted_by;
                if (SingletonDataShow.getInstance().getPhone().length() == 8)
                    posted_by = "02" + SingletonDataShow.getInstance().getPhone();
                else
                    posted_by = SingletonDataShow.getInstance().getPhone();
                String uri = "tel:" + posted_by.trim();
                Intent in = new Intent(Intent.ACTION_DIAL);
                in.setData(Uri.parse(uri));
                startActivity(in);
            }
        });

        if (SingletonDataShow.getInstance().getPapa().equals("e"))
            linbaba.setVisibility(View.GONE);

        if (SingletonDataShow.getInstance().getMama().equals("e"))
            linmama.setVisibility(View.GONE);

        if (SingletonDataShow.getInstance().getPhone().equals("e"))
            linphone.setVisibility(View.GONE);

        if (SingletonDataShow.getInstance().getAnotherAdd().equals("e"))
            lindesc.setVisibility(View.GONE);

        if (SingletonDataShow.getInstance().getDescription().equals("e"))
            linother.setVisibility(View.GONE);

        if (SingletonDataShow.getInstance().getBirthdate().equals("e"))
            birth.setVisibility(View.GONE);
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
                Intent edit = new Intent(Data_Show.this, Edit_Data.class);
                //  edit.putExtra("position_edit", Integer.parseInt(pos));


                startActivity(edit);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
