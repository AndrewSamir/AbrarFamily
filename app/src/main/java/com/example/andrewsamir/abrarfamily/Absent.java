package com.example.andrewsamir.abrarfamily;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewsamir.abrarfamily.data.DataDetails;
import com.example.andrewsamir.abrarfamily.jsondata.DataInJson;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew Samir on 1/31/2016.
 */
public class Absent extends ActionBarActivity {

    LinearLayout lin;
    CheckBox ch;

    LinearLayout parent;
    ImageView imageView;
    TextView name;

    ArrayList<DataDetails> dataabsent = new ArrayList<>();
    ArrayList<String> senddata=new ArrayList<>();
    DatePicker c;
    String dataabsenttosend,date;
    int i=0;
    HashMap<String,String> hm=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
        SharedPreferences.Editor editor = jsonData.edit();


        TextView fasl= (TextView) findViewById(R.id.textView4);
        fasl.setText( "  فصل القديس  " + jsonData.getString("fasl", null));

        lin=(LinearLayout)findViewById(R.id.linid);



        Gson gson=new Gson();

        if(jsonData.getString("json",null)!=null) {
            DataInJson dd = gson.fromJson(jsonData.getString("json", null), DataInJson.class);
            dataabsent.clear();
            for (int i = 13; i < dd.getFeed().getEntry().size(); ) {

                dataabsent.add(new DataDetails(dd.getFeed().getEntry().get(i).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 1).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 2).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 3).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 4).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 5).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 6).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 7).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 8).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 9).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 10).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 11).getContent().get$t(),
                        dd.getFeed().getEntry().get(i + 12).getContent().get$t()));
                i = i + 13;
            }





            ArrayList<String> np = new ArrayList<>();
            for (DataDetails d : dataabsent) {
                hm.put(d.getName(),d.getSerial());
                np.add(d.getName());
            }


            for (String ns:np){




                ViewGroup.LayoutParams ll=new ActionBar.LayoutParams(250,700);
               // imageView.setLayoutParams(ll);

                ch=new CheckBox(this);

                ch.setId(i);
                ch.setText(ns);

                ch.setPadding(0,10,18,10);
                ch.setGravity(Gravity.CENTER_VERTICAL);
                ch.setTextSize(getResources().getDimension(R.dimen.textsize));
                ch.setOnClickListener(getOn(ch));


                lin.addView(ch);

                i++;
            }
            c=new DatePicker(this);
            c.setId(i);
            lin.addView(c);

        }

        Button done= (Button) findViewById(R.id.buttondone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {

                    int day = c.getDayOfMonth();
                    int month = c.getMonth() + 1;
                    int year = c.getYear();
                    date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);

                    if (senddata.size() > 0) {

                        for (String s : senddata) {
                            dataabsenttosend += " " + hm.get(s) + date;
                        }


                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                postData();

                            }
                        });
                        t.start();

                        Intent gohome = new Intent(Absent.this, MainActivity.class);
                        gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        finish();
                        startActivity(gohome);

                    } else

                        Toast.makeText(Absent.this, "you must choose names", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Absent.this,"please make sure of your Internet connection then try again ",Toast.LENGTH_LONG).show();
            }


        });

       Button cancel= (Button) findViewById(R.id.buttoncancelabsent);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome=new Intent(Absent.this,MainActivity.class);
                finish();
                startActivity(gohome);
            }
        });
    }



    View.OnClickListener getOn( final Button bu){
        return new View.OnClickListener() {
            public void onClick(View v) {

                if(senddata.contains(bu.getText()))
                    senddata.remove(bu.getText());
                else
                senddata.add(bu.getText().toString());
            } };

    }

    public void postData() {

        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


        String fullUrl = "https://docs.google.com/forms/d/1e55NHCdRA6LJ_Yriuyw3NdMOjihY-Jqif5Fp5r-8kg4/formResponse";
        HttpRequest mReq = new HttpRequest();

        String data = "entry_78838420=" + URLEncoder.encode(jsonData.getString("fasl","error")) + "&" +
                "entry_19855689="+URLEncoder.encode(dataabsenttosend.substring(4));
        String response = mReq.sendPost(fullUrl, data);
        Toast.makeText(Absent.this,response,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
