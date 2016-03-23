package com.example.andrewsamir.abrarfamily;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andrewsamir.abrarfamily.adaptors.AbsentAdapter;
import com.example.andrewsamir.abrarfamily.data.AbsentData;
import com.example.andrewsamir.abrarfamily.jsondata.DataInJson;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Andrew Samir on 2/24/2016.
 */
public class Absent_list extends ActionBarActivity {

    String dt_absent;
    AbsentAdapter na_absent;
    public static ArrayList<AbsentData> datad_Absent = new ArrayList<>();
    ListView lv;
    DataInJson dd;
    ArrayList<AbsentData> np_absent = new ArrayList<>();
    ProgressBar progressBar;
    LinearLayout linearLayout;
    TextView t1, t2, t3, t4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_absent);

        lv = (ListView) findViewById(R.id.listViewAbsent);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        linearLayout = (LinearLayout) findViewById(R.id.linAbsent);

        t1 = (TextView) findViewById(R.id.textViewAbsent_1V);
        t2 = (TextView) findViewById(R.id.textViewAbsent_2V);
        t3 = (TextView) findViewById(R.id.textViewAbsent_3V);
        t4 = (TextView) findViewById(R.id.textViewAbsent_4V);

        if (isOnline()) {
            progressBar.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);

            RequestQueue queue = Volley.newRequestQueue(Absent_list.this);
            SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


            String url = jsonData.getString("AbsentURL", null);

            StringRequest str = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = new Gson();

                            dd = gson.fromJson(response, DataInJson.class);


                            int x = firstAbsent();

                            // new getfirst().execute();
                            t4.setText(dd.getFeed().getEntry().get(x - 39).getContent().get$t().substring(0, dd.getFeed().getEntry().get(x - 39).getContent().get$t().length() - 5));

                            t1.setText(dd.getFeed().getEntry().get(x - 36).getContent().get$t().substring(0, dd.getFeed().getEntry().get(x - 36).getContent().get$t().length() - 5));

                            t2.setText(dd.getFeed().getEntry().get(x - 37).getContent().get$t().substring(0, dd.getFeed().getEntry().get(x - 37).getContent().get$t().length() - 5));

                            t3.setText(dd.getFeed().getEntry().get(x - 38).getContent().get$t().substring(0, dd.getFeed().getEntry().get(x - 38).getContent().get$t().length() - 5));


                            ArrayList<AbsentData> n = new ArrayList<>();

                            for (int i = 37; i < dd.getFeed().getEntry().size(); ) {

                                if (dd.getFeed().getEntry().get(i).getContent().get$t().equals(""))
                                    break;

                                n.add(new AbsentData(dd.getFeed().getEntry().get(i).getContent().get$t(),
                                        dd.getFeed().getEntry().get(x).getContent().get$t(),
                                        dd.getFeed().getEntry().get(x - 1).getContent().get$t(),
                                        dd.getFeed().getEntry().get(x - 2).getContent().get$t(),
                                        dd.getFeed().getEntry().get(x - 3).getContent().get$t()
                                ));

                                x = x + 36;

                                i = i + 36;
                            }

                            AbsentAdapter na = new AbsentAdapter(n, Absent_list.this);

                            lv.setAdapter(na);


                            linearLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }


                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Absent_list.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
            queue.add(str);
        } else

        {
            Toast.makeText(Absent_list.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();

        }

    }


    public int firstAbsent() {

        for (int i = 70; i > 41; i--) {

            if (!dd.getFeed().getEntry().get(i).getContent().get$t().equals("."))
                return i;
        }

        return 41;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class getfirst extends AsyncTask<Void, Integer, Integer> {


        @Override
        protected Integer doInBackground(Void... params) {

            for (int i = 70; i == 41; i--) {

                Toast.makeText(Absent_list.this, "test", Toast.LENGTH_SHORT).show();

                if (!dd.getFeed().getEntry().get(i).getContent().get$t().equals("."))
                    return i;
            }

            return 41;

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            // x=integer;
        }
    }

}
