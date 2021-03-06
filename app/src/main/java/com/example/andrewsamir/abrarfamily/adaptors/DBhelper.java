package com.example.andrewsamir.abrarfamily.adaptors;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew Samir on 3/8/2016.
 */
public class DBhelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;


    private static final String DATABASE_NAME="DB";
    private static final String TABLE_NAME="Eftkad";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String PHOTO="photo";
    private static final String STREET="street";
    private static final String RAKAM="rakam";
    private static final String SERIAL="serial";

    private static final String CREATE_TABLE_EFTKAD = "CREATE TABLE "
            + TABLE_NAME + "(" + SERIAL + " INTEGER PRIMARY KEY," + NAME
            +STREET+" TEXT , "+RAKAM+" TEXT , "+PHOTO
            + " TEXT )";

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

    public boolean ADD(String name,String rakam,String street,String photo,int serial){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(RAKAM, rakam);
        values.put(PHOTO, photo);
        values.put(STREET, street);
        values.put(SERIAL, serial);



        // insert row
        long user_row = db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        return user_row!=-1;
        }
    public void deleteuser(String name){

        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,NAME+" = "+name,null);
    }
/*
    public String getMeeting(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TBL_MEETING + " WHERE " + MEETING_ID + " = " + id, null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(MEETING_DATE))+','+c.getString(c.getColumnIndex(MEETING_END));
    }*/
}
