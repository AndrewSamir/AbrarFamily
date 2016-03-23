package com.example.andrewsamir.abrarfamily;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andrewsamir.abrarfamily.jsondata.DataInJson;
import com.google.gson.Gson;

/**
 * Created by Andrew Samir on 2/18/2016.
 */
public class LogIn extends ActionBarActivity {
    EditText usernamee;
    EditText password;
    String dt;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


        if (jsonData.getBoolean("state", false)) {

            Intent gohome = new Intent(LogIn.this, MainActivity.class);
            gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            finish();
            startActivity(gohome);
        }

        usernamee = (EditText) findViewById(R.id.editTextusername);

        password = (EditText) findViewById(R.id.editTextpassword);

        checkBox = (CheckBox) findViewById(R.id.checkBoxuser);

        Button login = (Button) findViewById(R.id.buttonlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOnline()) {


                    RequestQueue queue = Volley.newRequestQueue(LogIn.this);

                    String url = "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/13/public/basic?alt=json";

                    StringRequest str = new StringRequest(url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                    SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = jsonData.edit();
                                    dt = response.toString();


                                   /* editor.putString("json", dt);
                                    editor.commit();*/

                                    Gson gson = new Gson();
                                    Toast.makeText(LogIn.this, usernamee.getText(), Toast.LENGTH_LONG).show();

                                    DataInJson dd = gson.fromJson(dt, DataInJson.class);
                                    for (int i = 0; i < dd.getFeed().getEntry().size(); ) {

                                        String na = usernamee.getText().toString();
                                        String st = dd.getFeed().getEntry().get(i).getContent().get$t();

                                        if (na.equals(st)) {
                                            Toast.makeText(LogIn.this, dd.getFeed().getEntry().get(i).getContent().get$t(), Toast.LENGTH_LONG).show();


                                            editor.putString("name", st);
                                            editor.putString("fasl", dd.getFeed().getEntry().get(i + 1).getContent().get$t());
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("ابيب")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/4/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/2/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("اباكير")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/5/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/23/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("ابوللو")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/6/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/26/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("اباهور")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/7/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/25/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("ابادير")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/8/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/24/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("كيرياكوس")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/9/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/28/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("ونس")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/10/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/29/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("ابانوب")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/11/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/30/public/basic?alt=json");
                                            }
                                            if (dd.getFeed().getEntry().get(i + 1).getContent().get$t().equals("اسطفانوس")) {
                                                editor.putString("KashfURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/12/public/basic?alt=json");
                                                editor.putString("AbsentURL", "https://spreadsheets.google.com/feeds/cells/1rYBqZWwj18ppIbu9ECllT5D8wvIGoc6mOgnn1raH6sU/27/public/basic?alt=json");
                                            }
                                            if (checkBox.isChecked())
                                                editor.putBoolean("state", true);


                                            editor.commit();

                                            Intent gohome = new Intent(LogIn.this, MainActivity.class);
                                            gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                            finish();
                                            startActivity(gohome);


                                            return;
                                        }
                                        i = i + 2;
                                    }
                                    Toast.makeText(LogIn.this, "incorrect username", Toast.LENGTH_LONG).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LogIn.this, error.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                    queue.add(str);
                } else
                    Toast.makeText(LogIn.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();


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
