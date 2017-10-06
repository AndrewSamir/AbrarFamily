package com.example.andrewsamir.abrarfamily;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewsamir.abrarfamily.Activities.HOME;
import com.example.andrewsamir.abrarfamily.data.SendData;
import com.firebase.client.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Andrew Samir on 1/27/2016.
 */
public class Enter_Data extends ActionBarActivity {


    DataSnapshot myChild;


    private EditText fromDateEtxt;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private static final int CAMERA_REQUEST = 1888;


    private static int RESULT_LOAD_IMAGE = 1;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

//    boolean again = false;

    String date = "e";
    String name = null;
    String homeN = null;
    String street = null;
    String floor = null;
    String flat = null;
    String desc = null;
    String another = null;
    String babamob = null;
    String mamamob = null;
    String phone = null;
    String photosend = "e";


    ImageView imv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_data);
        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        TextView fasl = (TextView) findViewById(R.id.textViewfasl);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        fasl.setText("  فصل القديس  " + prefs.getString("nameFasl", null));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        fromDateEtxt = (EditText) findViewById(R.id.setdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                date = dateFormatter.format(newDate.getTime());
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();

            }
        });
        imv = (ImageView) findViewById(R.id.imageViewphoto);

//        Button takephoto= (Button) findViewById(R.id.buttonphoto);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {
                        "Camera", "Gallery"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Enter_Data.this);
                builder.setTitle("Choose Photo From ..");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //mDoneButton.setText(items[item]);
                        if (items[item].equals("Camera")) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        } else if (items[item].equals("Gallery")) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });


        Button buttonSendData = (Button) findViewById(R.id.buttonSendData);
        final EditText ename = (EditText) findViewById(R.id.editTextName);
        final EditText ehomeN = (EditText) findViewById(R.id.editTextRakmManzl);
        final EditText estreet = (EditText) findViewById(R.id.editTextStreet);
        final EditText efloor = (EditText) findViewById(R.id.editTextFloor);
        final EditText ehome = (EditText) findViewById(R.id.editTextHome);
        final EditText edesc = (EditText) findViewById(R.id.editTextAddressDescreption);
        final EditText eanother = (EditText) findViewById(R.id.editTextAnotherAddress);
        final EditText ebaba = (EditText) findViewById(R.id.editTextBabaMob);
        final EditText emama = (EditText) findViewById(R.id.editTextMamaMob);
        final EditText ephone = (EditText) findViewById(R.id.editTextHomePhone);

        buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {

                    try {
                        if (ename.getText().toString().trim().length() == 0)
                            name = "e";
                        else
                            name = ename.getText().toString();

                        if (estreet.getText().toString().trim().length() == 0)
                            street = "e";
                        else
                            street = estreet.getText().toString();

                        if (ehome.getText().toString().trim().length() == 0)
                            flat = "e";
                        else
                            flat = ehome.getText().toString();

                        if (ehomeN.getText().toString().trim().length() == 0)
                            homeN = "e";
                        else
                            homeN = ehomeN.getText().toString();

                        if (efloor.getText().toString().trim().length() == 0)
                            floor = "e";
                        else
                            floor = efloor.getText().toString();

                        if (edesc.getText().toString().trim().length() == 0)
                            desc = "e";
                        else
                            desc = edesc.getText().toString();

                        if (eanother.getText().toString().trim().length() == 0)
                            another = "e";
                        else
                            another = eanother.getText().toString();

                        if (ebaba.getText().toString().trim().length() == 0)
                            babamob = "e";
                        else
                            babamob = ebaba.getText().toString();

                        if (emama.getText().toString().trim().length() == 0)
                            mamamob = "e";

                        else
                            mamamob = emama.getText().toString();

                        if (ephone.getText().toString().trim().length() == 0)
                            phone = "e";
                        else
                            phone = ephone.getText().toString();
                    } finally {
                        // again = true;
                        postData();

                    }


                } else
                    Toast.makeText(Enter_Data.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();

            }

        });

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    public void postData() {

        SendData sendData = new SendData();
        sendData.setName(name);
        sendData.setHomeNo(homeN);
        sendData.setStreet(street);
        sendData.setFloor(floor);
        sendData.setFlat(flat);
        sendData.setDescription(desc);
        sendData.setAnotherAdd(another);
        sendData.setPapa(babamob);
        sendData.setMama(mamamob);
        sendData.setPhone(phone);
        sendData.setBirthdate(date);
        sendData.setImage(photosend);
        sendData.setAttendance("e");
        sendData.setLastEftkad("e");
        sendData.setPlace("e");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // DEMO
        //  DatabaseReference myRef = database.getReference("fsol/BLsc8RMHwtNqQB6C23TC0tIImw92/");

        //will be
        DatabaseReference myRef = database.getReference("fsol/" + mAuth.getCurrentUser().getUid() + "/list");


        myRef.push().setValue(sendData);
        Intent gohome = new Intent(Enter_Data.this, HOME.class);
        startActivity(gohome);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
/*
            ImageView imageView = (ImageView) findViewById(R.id.imgView);*/

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            imv.setImageBitmap(bitmap);
            String imgString = Base64.encodeToString(getBytesFromBitmap(bitmap),
                    Base64.NO_WRAP);

            photosend = imgString;
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            String imgString = Base64.encodeToString(getBytesFromBitmap(photo),
                    Base64.NO_WRAP);
            imv.setImageBitmap(photo);
            photosend = imgString;

        }


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


}
