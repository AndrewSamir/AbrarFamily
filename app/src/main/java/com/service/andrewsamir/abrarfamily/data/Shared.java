package com.example.andrewsamir.abrarfamily.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Andrew Samir on 1/30/2016.
 */
public class Shared extends Activity{

    SharedPreferences kashf_data = getApplicationContext().getSharedPreferences("kashf_data", MODE_PRIVATE);
    SharedPreferences.Editor editor = kashf_data.edit();

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
