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

    private static final int DATABASE_VERSION = 2;


    private static final String DATABASE_NAME="DBase";
    private static final String TABLE_NAME="Eftkad_table";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String PHOTO="photo";
    private static final String STREET="street";
    private static final String RAKAM="rakam";
    private static final String SERIAL="serial";

    private static final String CREATE_TABLE_EFTKAD = "CREATE TABLE "
            + TABLE_NAME + "( " + SERIAL + " INTEGER PRIMARY KEY, " + NAME+" TEXT , "
            +STREET+" TEXT , "+RAKAM+" TEXT , "+PHOTO
            + " TEXT ,"+ ID +" TEXT )";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_EFTKAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP IF EXISTS "+TABLE_NAME);
    }

    public boolean ADD(String name,String rakam,String street,String photo,int serial,String id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(RAKAM, rakam);
        values.put(PHOTO, photo);
        values.put(STREET, street);
        values.put(SERIAL, serial);
        values.put(ID, id);

        // insert row
        long user_row=db.insert(TABLE_NAME, null, values);
    if(user_row==-1)
        return  false;
    else
        return true;

        }
    public void deleteuser(String id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+" = "+id,null);
    }
    public Cursor getMeetings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    }
    /*
    public String getMeeting(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TBL_MEETING + " WHERE " + MEETING_ID + " = " + id, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(MEETING_DATE))+','+c.getString(c.getColumnIndex(MEETING_END));
    }*/
}
