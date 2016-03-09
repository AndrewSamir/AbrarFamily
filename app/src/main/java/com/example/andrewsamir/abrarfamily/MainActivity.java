package com.example.andrewsamir.abrarfamily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String dt;
    private static final int CAMERA_REQUEST = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button eftkad= (Button) findViewById(R.id.buttoneftkad);
        eftkad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Eftkad.class);
                startActivity(in);
            }
        });



        Button b= (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Enter_Data.class);
                startActivity(in);
            }
        });

        Button g= (Button) findViewById(R.id.button2);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Kash_List.class);
                startActivity(in);
            }
        });

        Button absent= (Button) findViewById(R.id.buttonabsent);
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abs=new Intent(MainActivity.this,Absent.class);
                startActivity(abs);
            }
        });

        Button absentshow= (Button) findViewById(R.id.buttonAbsentShow);
        absentshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abs=new Intent(MainActivity.this,Absent_list.class);
                startActivity(abs);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_remove:

                SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);
                SharedPreferences.Editor editor = jsonData.edit();


                editor.putString("json", null);
                editor.commit();

                Toast.makeText(MainActivity.this,"All Data have been REMOVED ...",Toast.LENGTH_LONG).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
