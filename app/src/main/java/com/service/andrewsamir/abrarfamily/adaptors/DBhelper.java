package com.example.andrewsamir.abrarfamily.adaptors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew Samir on 3/8/2016.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 12;


    private static final String DATABASE_NAME = "DBase";
    private static final String EFTKAD_TABLE = "Eftkad_table";
    private static final String KASHF_TABLE = "Kashf_table";
    private static final String BIRTHDATE_TABLE = "Birthdate_table";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHOTO = "photo";
    private static final String STREET = "street";
    private static final String RAKAM = "rakam";
    private static final String SERIAL = "serial";
    private static final String LAST_EFTKAD = "last_eftkad";
    private static final String LAST_ATTENDANCE = "last_attendance";


    private static final String BIRTHDATE = "birthdate";
    private static final String FLOOR = "floor";
    private static final String FLAT = "flat";
    private static final String PAPA = "papa";
    private static final String MAMA = "mama";
    private static final String PHONE = "phone";
    private static final String DESCRIPTION = "description";
    private static final String ANOTHER_ADDRESS = "another_address";


    private static final String ADDED = "added";
    private static final String COME = "come";
    private static final String DONE = "done";
    private static final String TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_EFTKAD = "CREATE TABLE "
            + EFTKAD_TABLE + "( " + SERIAL + " TEXT PRIMARY KEY, " + NAME + " TEXT , "
            + STREET + " TEXT , " + RAKAM + " TEXT , " + PHOTO
            + " TEXT ," + LAST_EFTKAD + " TEXT ," + LAST_ATTENDANCE + " TEXT ," + ID + " TEXT )";

    private static final String CREATE_TABLE_KASHF = "CREATE TABLE "
            + KASHF_TABLE + "( " + SERIAL + " INTEGER PRIMARY KEY, " + NAME + " TEXT , "
            + STREET + " TEXT , " + RAKAM + " TEXT , " + PHOTO + " TEXT ,"
            + BIRTHDATE + " TEXT , " + FLOOR + " TEXT , " + FLAT + " TEXT , " + PAPA + " TEXT , "
            + MAMA + " TEXT , " + PHONE + " TEXT , " + DESCRIPTION + " TEXT , " + ANOTHER_ADDRESS + " TEXT , "
            + ID + " TEXT )";

    private static final String CREATE_TABLE_BIRTHDATE = "CREATE TABLE "
            + BIRTHDATE_TABLE + "( " + SERIAL + " Text PRIMARY KEY, " + NAME + " TEXT , "
            + BIRTHDATE + " TEXT , " + TIMESTAMP + " LONG , " + COME + " INTEGER , " + DONE + " INTEGER ) ";


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_EFTKAD);
        db.execSQL(CREATE_TABLE_KASHF);
        db.execSQL(CREATE_TABLE_BIRTHDATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + EFTKAD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KASHF_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIRTHDATE_TABLE);

    }

    public boolean ADD(String name, String rakam, String street, String photo, String serial, String id, String lastEftkad, String lastAttendance) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(RAKAM, rakam);
        values.put(PHOTO, photo);
        values.put(STREET, street);
        values.put(SERIAL, serial);
        values.put(ID, id);
        values.put(LAST_EFTKAD, lastEftkad);
        values.put(LAST_ATTENDANCE, lastAttendance);

        // insert row
        long user_row = db.insert(EFTKAD_TABLE, null, values);
        if (user_row == -1)
            return false;
        else
            return true;

    }

    public void deleteuser(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EFTKAD_TABLE, ID + " = " + id, null);
    }

    public Cursor getMeetings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + EFTKAD_TABLE, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean ADD_KASHF(String name, String rakam, String street, String photo, int serial, String id,
                             String birthdate, String floor, String flat, String papa, String mama,
                             String phone, String description, String another_addr) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(RAKAM, rakam);
        values.put(PHOTO, photo);
        values.put(STREET, street);
        values.put(SERIAL, serial);
        values.put(ID, id);
        values.put(BIRTHDATE, birthdate);
        values.put(FLOOR, floor);
        values.put(FLAT, flat);
        values.put(PAPA, papa);
        values.put(MAMA, mama);
        values.put(PHONE, phone);
        values.put(DESCRIPTION, description);
        values.put(ANOTHER_ADDRESS, another_addr);

        // insert row
        long user_row = db.insert(KASHF_TABLE, null, values);
        if (user_row == -1)
            return false;
        else
            return true;

    }


    public Cursor getKashfList() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + KASHF_TABLE, null);
    }

    public void deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(KASHF_TABLE, null, null);
    }

    //////////////////////////////////////////////////////////////
    //birthdate

    public boolean ADD_BIRTHDATE(String name, String birthdate, Long timestamp, String serial) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(BIRTHDATE, birthdate);
        values.put(TIMESTAMP, timestamp);
        values.put(SERIAL, serial);

        // insert row
        long user_row = db.insert(BIRTHDATE_TABLE, null, values);
        if (user_row == -1)
            return false;
        else
            return true;

    }

    public Cursor getBirthdate() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + BIRTHDATE_TABLE, null);
    }

    public boolean DoneHappyBirthdate(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String strSQL = "UPDATE " + BIRTHDATE_TABLE + " SET " + DONE + " = 1  WHERE " + SERIAL + " = " + id;

        db.execSQL(strSQL);

        return true;
    }
}