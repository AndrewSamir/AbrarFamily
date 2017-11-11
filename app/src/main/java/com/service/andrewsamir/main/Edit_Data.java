package com.service.andrewsamir.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.service.andrewsamir.main.Activities.HOME;
import com.service.andrewsamir.main.Singleton.SingletonDataShow;
import com.service.andrewsamir.main.data.SendData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.van.fanyu.library.Compresser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.Manifest;

import gun0912.tedbottompicker.TedBottomPicker;

/**
 * Created by Andrew Samir on 2/11/2016.
 */
public class Edit_Data extends ActionBarActivity {

    private EditText fromDateEtxt;


    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private static final int CAMERA_REQUEST = 1888;
    ImageView imv;


    private static int RESULT_LOAD_IMAGE = 1;

    String date = "e";
    String name = "e";
    String homeN = "e";
    String street = "e";
    String floor = "e";
    String flat = "e";
    String desc = "e";
    String another = "e";
    String Serial;
    String babamob = "e";
    String mamamob = "e";
    String phone = "e";
    String photosend = "e";
    String lastEftkad = "e";
    String attendance = "e";
    String place = "e";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_data);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //   pos = getIntent().getIntExtra("position_edit", 0);

        // Serial = kashfList.datad.get(pos).getSerial();


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

        TextView fasl = (TextView) findViewById(R.id.textViewfasl);

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        fasl.setText("  فصل  " + prefs.getString("nameFasl", null));

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
        imv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(Edit_Data.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //File write logic here
                    picImage();

                } else {
                    ActivityCompat.requestPermissions(Edit_Data.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                }
            }

        });


        if (!SingletonDataShow.getInstance().getName().equals("e"))

            ename.setText(SingletonDataShow.getInstance().getName());


        if (!SingletonDataShow.getInstance().getHomeNo().equals("e"))

            ehomeN.setText(SingletonDataShow.getInstance().getHomeNo());


        if (!SingletonDataShow.getInstance().getStreet().equals("e"))

            estreet.setText(SingletonDataShow.getInstance().getStreet());


        if (!SingletonDataShow.getInstance().getFloor().equals("e"))

            efloor.setText(SingletonDataShow.getInstance().getFloor());


        if (!SingletonDataShow.getInstance().getFlat().equals("e"))

            ehome.setText(SingletonDataShow.getInstance().getFlat());


        if (!SingletonDataShow.getInstance().getDescription().equals("e"))

            edesc.setText(SingletonDataShow.getInstance().getDescription());


        if (!SingletonDataShow.getInstance().getAnotherAdd().equals("e"))

            eanother.setText(SingletonDataShow.getInstance().getAnotherAdd());


        if (!SingletonDataShow.getInstance().getPapa().equals("e"))

            ebaba.setText(SingletonDataShow.getInstance().getPapa());


        if (!SingletonDataShow.getInstance().getMama().equals("e"))

            emama.setText(SingletonDataShow.getInstance().getMama());


        if (!SingletonDataShow.getInstance().getPhone().equals("e"))

            ephone.setText(SingletonDataShow.getInstance().getPhone());

        if (!SingletonDataShow.getInstance().getBirthdate().equals("e")) {
            fromDateEtxt.setText(SingletonDataShow.getInstance().getBirthdate());
            date = SingletonDataShow.getInstance().getBirthdate();
        }
        if (!SingletonDataShow.getInstance().getImage().equals("e")) {
            imv.setImageBitmap(StringToBitMap(SingletonDataShow.getInstance().getImage()));
            if (SingletonDataShow.getInstance().getImage().length() > 0)
                photosend = SingletonDataShow.getInstance().getImage();
            else
                photosend = "e";
        }

        if (!SingletonDataShow.getInstance().getLastEftkad().equals("e"))

            lastEftkad = SingletonDataShow.getInstance().getLastEftkad();

        if (!SingletonDataShow.getInstance().getAttendance().equals("e"))

            attendance = SingletonDataShow.getInstance().getAttendance();

        if (!SingletonDataShow.getInstance().getPlace().equals("e")) {
            place = SingletonDataShow.getInstance().getPlace();

        }


        buttonSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
                    postData();
                    finish();
                }


            }

        });

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
        sendData.setAttendance(attendance);
        sendData.setLastEftkad(lastEftkad);
        sendData.setPlace(place);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // DEMO
        //  DatabaseReference myRef = database.getReference("fsol/BLsc8RMHwtNqQB6C23TC0tIImw92/");

        //will be
        DatabaseReference myRef = database.getReference("fsol/"
                + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/list/"
                + SingletonDataShow.getInstance().getKey()
        );

        myRef.setValue(sendData);
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    private void picImage() {
        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(Edit_Data.this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        compressedFile(uri);
                    }
                })
                //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                .setPeekHeight(1200)
                .setTitle(getString(R.string.choose_image))
                .create();

        bottomSheetDialogFragment.show(getSupportFragmentManager());


    }

    private void compressedFile(final Uri uri) {
        // imageUri=uri;
        try {
            Compresser compresser = new Compresser(50, uri.getPath());
            compresser.doCompress(new Compresser.CompleteListener() {
                @Override
                public void onSuccess(String newPath) {
                    //Log.e("from", "comprees");
                    imv.setImageURI(Uri.parse(newPath));
                    Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
                    photosend = encodeTobase64(bitmap);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //resume tasks needing this permission
            picImage();
        }
    }
}
