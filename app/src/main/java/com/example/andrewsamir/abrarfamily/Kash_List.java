package com.example.andrewsamir.abrarfamily;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.andrewsamir.abrarfamily.adaptors.DBhelper;
import com.example.andrewsamir.abrarfamily.adaptors.NameAdapter;
import com.example.andrewsamir.abrarfamily.data.DataDetails;
import com.example.andrewsamir.abrarfamily.data.Name;
import com.example.andrewsamir.abrarfamily.jsondata.DataInJson;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew Samir on 1/30/2016.
 */
public class Kash_List extends ActionBarActivity {

    String dt;
    NameAdapter na;
    public static ArrayList<DataDetails> datad = new ArrayList<>();
    ListView lv;
    ProgressBar progressBar;
    SearchView sv;
    ArrayList<Name> np = new ArrayList<>();
    int i = 0;
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    boolean state = false;
    int num = 1;
    int num_eftkad = 1;
    Eftkad eftkad;
    DBhelper db;
    DataSnapshot myChild;


    SharedPreferences settings;
    //JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kashf_list_view);


        db = new DBhelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sv = (SearchView) findViewById(R.id.searchv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {


                if (text.equals("")) {
                    i = 0;
                    iniList();

                } else {
                    state = true;
                    np.clear();
                    i = 0;
                    num = 1;
                    for (DataDetails d : datad) {
                        if (d.getName().contains(text)) {
                            np.add(new Name(d.getName(), d.getPhoto(), d.getRakmManzl(), d.getStreet(), Integer.toString(num)));
                            hashMap.put(i, datad.indexOf(d));
                            i++;
                        }
                        num++;

                    }

                    na = new NameAdapter(np, Kash_List.this);

                    na.notifyDataSetChanged();
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


                }

                return true;
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        lv = (ListView) findViewById(R.id.listViewnames);
        lv.setAdapter(na);


        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
        SharedPreferences.Editor editor = jsonData.edit();


        Gson gson = new Gson();

        if (jsonData.getString("json", null) != null) {

            iniList();


        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (state) {
            sv.setQuery("", false);
            sv.clearFocus();
            iniList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_favorite:
                if (isOnline()) {
                    final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

                    final Firebase ref = new Firebase("https://abrar-family.firebaseio.com/");

                    Query queryRef = ref.child("fsol").child(jsonData.getString("fasl","default"));
                    queryRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            try {

                                Iterable<DataSnapshot> myChildren =  snapshot.getChildren();
                                while (myChildren.iterator().hasNext()) {
                                    int i =0;
                                    myChild = myChildren.iterator().next();


                                        Toast.makeText(Kash_List.this,myChild.child("papa").getValue().toString()+" "+myChild.getKey(),Toast.LENGTH_SHORT).show();


                                        datad.add(new DataDetails(myChild.child("name").getValue().toString(),
                                                myChild.child("homeNo").getValue().toString(),
                                                myChild.child("street").getValue().toString(),
                                                myChild.child("floor").getValue().toString(),
                                                myChild.child("flat").getValue().toString(),
                                                myChild.child("description").getValue().toString(),
                                                myChild.child("anotherAdd").getValue().toString(),
                                                myChild.child("papa").getValue().toString(),
                                                myChild.child("mama").getValue().toString(),
                                                myChild.child("phone").getValue().toString(),
                                                myChild.child("birthdate").getValue().toString(),
                                                myChild.child("photo").getValue().toString(),
                                                "12"));




                            /*            boolean add = db.ADD_KASHF(myChild.child("name").getValue().toString(),
                                                myChild.child("homeNo").getValue().toString(),//rakm manzl
                                                myChild.child("street").getValue().toString(),//street
                                                myChild.child("photo").getValue().toString(),//photo
                                               Integer.parseInt( myChild.getKey()),//serial
                                                myChild.getKey(),//id
                                                myChild.child("birthdate").getValue().toString(),//birthdate
                                                myChild.child("floor").getValue().toString(),//floor
                                                myChild.child("flat").getValue().toString(),//flat
                                                myChild.child("papa").getValue().toString(),//papa
                                                myChild.child("mama").getValue().toString(),//mama
                                                myChild.child("phone").getValue().toString(),//phone
                                                myChild.child("description").getValue().toString(),//description
                                                myChild.child("anotherAdd").getValue().toString());//another address
                                        if (add)
                                            Toast.makeText(Kash_List.this, "You have add this name before", Toast.LENGTH_LONG).show();
                                       *//* DataArray.add(new data_news(myChild.child("Picture").getValue().toString(),
                                                myChild.child("Text").getValue().toString(),
                                                myChild.getKey().toString()));
*/

                                }


                                np.clear();
                                for (DataDetails d : datad) {
                                    np.add(new Name(d.getName(), d.getPhoto(), d.getRakmManzl(), d.getStreet(), Integer.toString(num)));
                                    num++;
                                }

                                na = new NameAdapter(np, Kash_List.this);

                                lv.setAdapter(na);

                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });


                    /*progressBar.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.INVISIBLE);

                    RequestQueue queue = Volley.newRequestQueue(Kash_List.this);
                    SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

                    String url = jsonData.getString("KashfURL", null);

                    StringRequest str = new StringRequest(url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                    SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = jsonData.edit();
                                    dt = response.toString();


                                    editor.putString("json", dt);
                                    editor.commit();

                                    iniList();
                                    lv.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Kash_List.this, error.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                    queue.add(str);
                } else
                    Toast.makeText(Kash_List.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();

*/

                }
                return true;
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_add:
                Intent in = new Intent(Kash_List.this, Enter_Data.class);
                startActivity(in);

            default:
                return true;
        }
    }


    public void iniList() {
        state = false;
        num = 1;
        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);



        Gson gson = new Gson();


        DataInJson dd = gson.fromJson(jsonData.getString("json", null), DataInJson.class);
        datad.clear();
        for (int i = 13; i < dd.getFeed().getEntry().size(); ) {

            datad.add(new DataDetails(dd.getFeed().getEntry().get(i).getContent().get$t(),
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


        //   ArrayList<Name> np=new ArrayList<>();
        np.clear();
        for (DataDetails d : datad) {
            np.add(new Name(d.getName(), d.getPhoto(), d.getRakmManzl(), d.getStreet(), Integer.toString(num)));
            num++;
        }

        na = new NameAdapter(np, Kash_List.this);

        lv.setAdapter(na);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);


                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                Intent showdata = new Intent(Kash_List.this, Data_Show.class);
                if (state)
                    showdata.putExtra("position", hashMap.get(position));
                else
                    showdata.putExtra("position", position);

                startActivity(showdata);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                final CharSequence[] items = {
                        "افتقاد"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Kash_List.this);
                builder.setTitle("اضافه الى ...");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (state) {


                            boolean add = db.ADD(datad.get(hashMap.get(position)).getName(), datad.get(hashMap.get(position)).getRakmManzl(),
                                    datad.get(hashMap.get(position)).getStreet(), datad.get(hashMap.get(position)).getPhoto(),
                                    Integer.parseInt(datad.get(hashMap.get(position)).getSerial()), Integer.toString(hashMap.get(position) + 1));
                            if (!add)
                                Toast.makeText(Kash_List.this, "You have add this name before", Toast.LENGTH_LONG).show();
                        } else {

                            boolean add = db.ADD(datad.get(position).getName(),
                                    datad.get(position).getRakmManzl(),
                                    datad.get(position).getStreet(),
                                    datad.get(position).getPhoto(),
                                    Integer.parseInt(datad.get(position).getSerial()),
                                    Integer.toString(position + 1));

                            //  boolean add = db.ADD("a","s","sa","sa",15);

                            if (!add)
                                Toast.makeText(Kash_List.this, "You have add this name before", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(Kash_List.this, "Done", Toast.LENGTH_LONG).show();

                        }

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


                return true;
            }
        });


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
