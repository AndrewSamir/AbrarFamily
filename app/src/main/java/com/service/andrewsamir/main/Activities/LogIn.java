package com.service.andrewsamir.main.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.service.andrewsamir.main.AddToCalendar.CalendarHelper;
import com.service.andrewsamir.main.MainActivity;
import com.service.andrewsamir.main.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Andrew Samir on 2/18/2016.
 */
public class LogIn extends ActionBarActivity {

    static Firebase myFirebaseRef;

    EditText usernamee;
    EditText password;
    CheckBox chLoginCreatseBirthdateEvents;


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
        chLoginCreatseBirthdateEvents = (CheckBox) findViewById(R.id.chLoginCreatseBirthdateEvents);

        Button login = (Button) findViewById(R.id.buttonlogin);


        final SharedPreferences.Editor editor = jsonData.edit();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chLoginCreatseBirthdateEvents.isChecked()) {

                    SharedPreferences.Editor editor = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                    editor.putBoolean("createEvents", true);
                    editor.apply();

                    if (CalendarHelper.haveCalendarReadWritePermissions(LogIn.this)) {
                        SignIn();
                    } else {
                        CalendarHelper.requestCalendarReadWritePermission(LogIn.this);
                        SignIn();
                    }
                } else {
                    SignIn();
                }

            }
        });
    }

    private void SignIn() {
        if (isValid()) {

            if (isOnline()) {
                //  if (true) {
                // Get a reference to our posts
                signIn(usernamee.getText().toString(), password.getText().toString());

            } else
                Toast.makeText(LogIn.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void signIn(String mail, String pass) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(mail + "@abrarfamily.com", pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Incorrect Mail Or Password",
                                    Toast.LENGTH_SHORT).show();
                            password.setText("");

                        } else {

                            startActivity(new Intent(LogIn.this, HOME.class));
                        }

                        // ...
                    }
                });
    }

    private boolean isValid() {

        Boolean vaild = true;

        if (password.getText().toString().length() == 0) {

            password.setError("Please Enter Your Password");
            vaild = false;
        }
        if (usernamee.getText().toString().length() == 0) {

            usernamee.setError("Please Enter Your Password");
            vaild = false;
        }
        return vaild;
    }


}
