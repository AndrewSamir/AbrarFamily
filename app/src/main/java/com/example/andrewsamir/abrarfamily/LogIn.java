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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


/**
 * Created by Andrew Samir on 2/18/2016.
 */
public class LogIn extends ActionBarActivity {

    static Firebase myFirebaseRef;

    EditText usernamee;
    EditText password;
    String dt;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase(getString(R.string.MyFirebase_Database));


        final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


        if (jsonData.getBoolean("logged", false)) {

            Intent gohome = new Intent(LogIn.this, MainActivity.class);
            gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            finish();
            startActivity(gohome);
        }

        usernamee = (EditText) findViewById(R.id.editTextusername);

        password = (EditText) findViewById(R.id.editTextpassword);

        checkBox = (CheckBox) findViewById(R.id.checkBoxuser);

        Button login = (Button) findViewById(R.id.buttonlogin);


        final SharedPreferences.Editor editor = jsonData.edit();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isOnline()) {

                    // Get a reference to our posts
                    Firebase ref = new Firebase("https://abrar-family.firebaseio.com/");

                    // Attach an listener to read the data at our posts reference
                    Query queryRef = ref.child("khodam");
                    queryRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {


                            try {
                                editor.putString("name", usernamee.getText().toString());
                                editor.putString("fasl", snapshot.child(usernamee.getText().toString()).getValue().toString());
                                editor.commit();
                                Toast.makeText(LogIn.this, snapshot.child(usernamee.getText().toString()).getValue().toString(), Toast.LENGTH_LONG).show();
                                if (checkBox.isChecked()) {
                                    editor.putBoolean("logged", true).commit();
                                    Toast.makeText(LogIn.this, jsonData.getBoolean("logged", false) + "", Toast.LENGTH_SHORT).show();

                                }
                                Intent gohome = new Intent(LogIn.this, MainActivity.class);
                                gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                                finish();
                                startActivity(gohome);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(LogIn.this, "Invalid UserName", Toast.LENGTH_LONG).show();
                                usernamee.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });

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
