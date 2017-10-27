package com.service.andrewsamir.abrarfamily;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.service.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.service.andrewsamir.abrarfamily.data.DataDetails;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    DBhelper myDB;
    boolean set = false;
    DataSnapshot myChild;

    ArrayList<DataDetails> dataabsent = new ArrayList<>();
    ArrayList<String> senddata = new ArrayList<>();
    ArrayList<String> recieveddata = new ArrayList<>();
    DatePicker datePicker;
    String dataabsenttosend, date;
    int i = 0;
    boolean first = false;
    HashMap<String, String> hm = new HashMap<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absent);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");

        TextView dateText = (TextView) findViewById(R.id.textviewDate);
        dateText.setText(intent.getStringExtra("dateShow"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
        SharedPreferences.Editor editor = jsonData.edit();


        Firebase ref = new Firebase("https://abrar-family.firebaseio.com/absent/" + jsonData.getString("fasl", "default"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());

                if (!first) {
                    first = true;

                    Iterable<DataSnapshot> myChildren = snapshot.getChildren();
                    while (myChildren.iterator().hasNext()) {
                        myChild = myChildren.iterator().next();

                        if (myChild.getKey().equals(date)) {
                            set = true;
                            Log.d("getdata", myChild.getValue().toString());
                            try {
                                JSONObject jsonObject = new JSONObject(myChild.getValue().toString());
                                JSONArray jsonArray = jsonObject.getJSONArray("absent");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    String s = jsonArray.getString(i);
                                    recieveddata.add(s);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {


                                myDB = new DBhelper(Absent.this);

                                lin = (LinearLayout) findViewById(R.id.linid);
                                Cursor cursor = myDB.getKashfList();

                                if (cursor.moveToFirst()) {
                                    do {


                                        LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                                        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                                        linearLayout1.setWeightSum(5);

                                        TextView textView = new TextView(getApplicationContext());
                                        textView.setText(cursor.getString(1));
                                        textView.setTextColor(Color.BLACK);
                                        linearLayout1.addView(textView);

                                        CheckBox cb = new CheckBox(getApplicationContext());
                                        cb.setId(cursor.getInt(0));
                                        cb.setOnClickListener(getOn(cb));
                                        if (recieveddata.contains(Integer.toString(cursor.getInt(0)))) {
                                            cb.setChecked(true);
                                            senddata.add(Integer.toString(cursor.getInt(0)));
                                        }

                                        linearLayout1.addView(cb);

                                        linearLayout1.setGravity(Gravity.RIGHT);
                                        lin.addView(linearLayout1);
                                    } while (cursor.moveToNext());

                                }
                            }
                        }
                    }
                    if (!set) {
                        myDB = new DBhelper(Absent.this);

                        lin = (LinearLayout) findViewById(R.id.linid);
                        Cursor cursor = myDB.getKashfList();

                        if (cursor.moveToFirst()) {
                            do {


                                LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                                linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
                                linearLayout1.setWeightSum(5);

                                TextView textView = new TextView(getApplicationContext());
                                textView.setText(cursor.getString(1));
                                textView.setTextColor(Color.BLACK);
                                linearLayout1.addView(textView);

                                CheckBox cb = new CheckBox(getApplicationContext());
                                cb.setId(cursor.getInt(0));
                                cb.setOnClickListener(getOn(cb));
                                linearLayout1.addView(cb);

                                linearLayout1.setGravity(Gravity.RIGHT);
                                lin.addView(linearLayout1);
                            } while (cursor.moveToNext());

                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        TextView fasl = (TextView) findViewById(R.id.textView4);
        fasl.setText("  فصل القديس  " + jsonData.getString("fasl", null));


        Button done = (Button) findViewById(R.id.buttondone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                for (String s : senddata) {
                    jsonArray.put(s);
                }
                try {
                    JSONObject absentObject = new JSONObject();
                    absentObject.put("absent", jsonArray);
                    Log.d("senddata", absentObject.toString());
                    dataabsenttosend = absentObject.toString();
                    postData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        });

        Button cancel = (Button) findViewById(R.id.buttoncancelabsent);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(Absent.this, MainActivity.class);
                finish();
                startActivity(gohome);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    View.OnClickListener getOn(final Button bu) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if (senddata.contains(Integer.toString(bu.getId())))
                    senddata.remove(Integer.toString(bu.getId()));
                else
                    senddata.add(Integer.toString(bu.getId()));
            }
        };

    }

    public void postData() {

        final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

        final Firebase ref = new Firebase("https://abrar-family.firebaseio.com/");

        ref.child("absent").child(jsonData.getString("fasl", "default")).child(date).setValue(dataabsenttosend);

        Intent intent = new Intent(Absent.this, MainActivity.class);
        startActivity(intent);

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  /*  public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Absent Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }*/

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client.connect();
        //AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    */}
}
