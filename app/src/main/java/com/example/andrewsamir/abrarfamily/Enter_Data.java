package com.example.andrewsamir.abrarfamily;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

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

     boolean again=false;

    String date = "null";
    String name = null;
    String homeN = null;
    String street = null;
    String floor = null;
    String home = null;
    String desc = null;
    String another = null;
    String babamob = null;
    String mamamob = null;
    String phone = null;
    String photosend = "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";


    private static final int CAMERA_REQUEST = 1888;
    ImageView imv;

    private static int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_data);
        SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

        TextView fasl = (TextView) findViewById(R.id.textViewfasl);
        fasl.setText("  فصل القديس  " + jsonData.getString("fasl", null));

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
                if (isOnline()) {

                    if (ename.getText().toString().trim().length() == 0)
                        name = "null";
                    else
                        name = ename.getText().toString();

                    if (estreet.getText().toString().trim().length() == 0)
                        street = "null";
                    else
                        street = estreet.getText().toString();

                    if (ehome.getText().toString().trim().length() == 0)
                        home = "null";
                    else
                        home = ehome.getText().toString();

                    if (ehomeN.getText().toString().trim().length() == 0)
                        homeN = "0";
                    else
                        homeN = ehomeN.getText().toString();

                    if (efloor.getText().toString().trim().length() == 0)
                        floor = "null";
                    else
                        floor = efloor.getText().toString();

                    if (edesc.getText().toString().trim().length() == 0)
                        desc = "null";
                    else
                        desc = edesc.getText().toString();

                    if (eanother.getText().toString().trim().length() == 0)
                        another = "null";
                    else
                        another = eanother.getText().toString();

                    if (ebaba.getText().toString().trim().length() == 0)
                        babamob = "null";
                    else if (ebaba.getText().toString().length() == 11)
                        babamob = ebaba.getText().toString().substring(0, 4) + " " + ebaba.getText().toString().substring(4, 7) + " " + ebaba.getText().toString().substring(7, 11);
                    else
                        babamob = ebaba.getText().toString();

                    if (emama.getText().toString().trim().length() == 0)
                        mamamob = "null";
                    else if (emama.getText().toString().length() == 11)
                        mamamob = emama.getText().toString().substring(0, 4) + " " + emama.getText().toString().substring(4, 7) + " " + emama.getText().toString().substring(7, 11);

                    else
                        mamamob = emama.getText().toString();

                    if (ephone.getText().toString().trim().length() == 0)
                        phone = "null";
                    else
                        phone = ephone.getText().toString();

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            again=true;
                            postData();

                        }
                    });
                    t.start();

                    Intent gohome = new Intent(Enter_Data.this, MainActivity.class);
                    gohome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    finish();
                    startActivity(gohome);
                } else
                    Toast.makeText(Enter_Data.this, "please make sure of your Internet connection then try again ", Toast.LENGTH_LONG).show();

            }

        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }

    public void postData() {

        final SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);

        final Firebase ref = new Firebase("https://abrar-family.firebaseio.com/");

        Query queryRef = ref.child("fsol").child(jsonData.getString("fasl","default"));
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String id="0";
                Iterable<DataSnapshot> myChildren =  snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {

                    myChild = myChildren.iterator().next();
                    id=myChild.getKey();
                }
                try {
                    long i = Long.parseLong(id);
                   // i = snapshot.getChildrenCount();

                    Toast.makeText(Enter_Data.this, i + 1 + "", Toast.LENGTH_SHORT).show();

                    if(again) {
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("name").setValue(name);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("homeNo").setValue(home);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("street").setValue(street);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("floor").setValue(floor);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("flat").setValue(homeN);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("description").setValue(desc);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("anotherAdd").setValue(another);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("papa").setValue(babamob);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("mama").setValue(mamamob);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("phone").setValue(phone);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("image").setValue(photosend);
                        ref.child("fsol").child(jsonData.getString("fasl","default")).child(i + 1 + "").child("birthdate").setValue(date);

                        again=false;
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });




        /*try {
            String fullUrl = "https://docs.google.com/forms/d/1fU7L5ov51Vozf7UCkMqikmjlcAYzl-HnDNI9FwuuUkA/formResponse";
            HttpRequest mReq = new HttpRequest();

            SharedPreferences jsonData = getApplicationContext().getSharedPreferences("jsonData", MODE_PRIVATE);


            Random rand=new Random();
            int g=rand.nextInt(99999999);
            String data = "entry_1159314701=" + URLEncoder.encode(jsonData.getString("name","error")) + "&" +
                    "entry_264438255=" + URLEncoder.encode(jsonData.getString("fasl","error"))+"&"+
                    "entry_873042066=" + URLEncoder.encode(name) + "&" +
                    "entry_2083578039=" + URLEncoder.encode(homeN)+"&"+
                    "entry_1455227551=" + URLEncoder.encode(street) + "&" +
                    "entry_360866657=" + URLEncoder.encode(floor)+"&"+
                    "entry_192517176=" + URLEncoder.encode(home) + "&" +
                    "entry_1375216006=" + URLEncoder.encode(desc)+"&"+
                    "entry_1756435469=" + URLEncoder.encode(another) + "&" +
                    "entry_683672125=" + URLEncoder.encode(babamob)+"&"+
                    "entry_2019928690=" + URLEncoder.encode(mamamob) + "&" +
                    "entry_910476216=" + URLEncoder.encode(phone)+"&"+
                    "entry_1599247433=" + URLEncoder.encode(photosend)+"&"+
                    "entry_531773961=" + URLEncoder.encode(Integer.toString(g))+"&"+
                    "entry_1283977554="+URLEncoder.encode(date);
            String response = mReq.sendPost(fullUrl, data);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Enter_Data.this,"errrrrror",Toast.LENGTH_LONG).show();
        }*/
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    @Override
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
            if (picturePath.endsWith(".png") || picturePath.endsWith(".jpeg") || picturePath.endsWith(".PNG") || picturePath.endsWith(".JPEG")) {
                imv.setImageBitmap(bitmap);
                String imgString = Base64.encodeToString(getBytesFromBitmap(bitmap),
                        Base64.NO_WRAP);

                photosend = imgString;
            } else if (picturePath.endsWith(".jpg") || picturePath.endsWith(".JPG")) {
                Toast.makeText(Enter_Data.this, "this image cannot be used please choose another one or use the default", Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            String imgString = Base64.encodeToString(getBytesFromBitmap(photo),
                    Base64.NO_WRAP);
            imv.setImageBitmap(photo);
            photosend = imgString;

        }


    }


    private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);

        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
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
