package com.service.andrewsamir.main.adaptors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew Samir on 3/8/2016.
 */
public class DBhelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 20;


    private static final String DATABASE_NAME = "DBase";
    private static final String EFTKAD_TABLE = "Eftkad_table";
    private static final String KASHF_TABLE = "Kashf_table";
    private static final String BIRTHDATE_TABLE = "Birthdate_table";
    public static String EXCEL_TABLE = "Excel_Table";

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


    private static final String Excel_Name = "الاسم";
    private static final String Excel_HomeNo = "رقم_العمارة";
    private static final String Excel_Street = "الشارع";
    private static final String Excel_Floor = "الدور";
    private static final String Excel_Flat = "الشقة";
    private static final String Excel_Desc = "تفاصيل_العنوان";
    private static final String Excel_Another_address = "عنوان_آخر";
    private static final String Excel_Birthdate = "تاريخ_الميلاد";
    private static final String Excel_Mama = "موبايل_ماما";
    private static final String Excel_Papa = "موبايل_بابا";
    private static final String Excel_Home_Phone = "تليفون_أرضي";


    private static final String ADDED = "added";
    private static final String COME = "come";
    private static final String DONE = "done";
    private static final String TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_EFTKAD = "CREATE TABLE "
            + EFTKAD_TABLE + "( " + SERIAL + " TEXT PRIMARY KEY, " + NAME + " TEXT , "
            + STREET + " TEXT , " + RAKAM + " TEXT , " + PHOTO
            + " TEXT ," + LAST_EFTKAD + " TEXT ," + LAST_ATTENDANCE + " TEXT ," + ID + " TEXT )";


    private static final String CREATE_TABLE_EXCEL = "CREATE TABLE "
            + EXCEL_TABLE + "( " + Excel_Name + " TEXT , "
            + Excel_HomeNo + " TEXT , " + Excel_Street + " TEXT," + Excel_Floor + " TEXT," + Excel_Flat +
            " TEXT," + Excel_Desc + " TEXT," +
            Excel_Another_address + " TEXT," + Excel_Birthdate + " TEXT," + Excel_Mama
            + " TEXT," + Excel_Papa + " TEXT," + Excel_Home_Phone + " TEXT )";

    private static final String CREATE_TABLE_KASHF = "CREATE TABLE "
            + KASHF_TABLE + "( " + SERIAL + " INTEGER PRIMARY KEY, " + NAME + " TEXT , "
            + STREET + " TEXT , " + RAKAM + " TEXT , " + PHOTO + " TEXT ,"
            + BIRTHDATE + " TEXT , " + FLOOR + " TEXT , " + FLAT + " TEXT , " + PAPA + " TEXT , "
            + MAMA + " TEXT , " + PHONE + " TEXT , " + DESCRIPTION + " TEXT , " + ANOTHER_ADDRESS + " TEXT , "
            + ID + " TEXT )";

    private static final String CREATE_TABLE_BIRTHDATE = "CREATE TABLE "
            + BIRTHDATE_TABLE + "( " + SERIAL + " Text unique, " + NAME + " TEXT , "
            + BIRTHDATE + " TEXT , " + TIMESTAMP + " LONG , " + COME + " INTEGER , " + DONE + " INTEGER ) ";


    public DBhelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(CREATE_TABLE_EFTKAD);
        db.execSQL(CREATE_TABLE_KASHF);
        db.execSQL(CREATE_TABLE_BIRTHDATE);
        db.execSQL(CREATE_TABLE_EXCEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS " + EFTKAD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KASHF_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIRTHDATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXCEL_TABLE);

    }

    public boolean ADD(String name, String rakam, String street, String photo, String serial, String id, String lastEftkad, String lastAttendance)
    {

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

    public boolean ADD_TO_EXCEL(String Excel_Name_, String Excel_HomeNo_, String Excel_Street_,
                                String Excel_Floor_, String Excel_Flat_, String Excel_Desc_,
                                String Excel_Another_address_, String Excel_Birthdate_,
                                String Excel_Mama_, String Excel_Papa_, String Excel_Home_Phone_)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Excel_Name, Excel_Name_);

        if (Excel_HomeNo_.equals("e"))
            values.put(Excel_HomeNo, "");
        else
            values.put(Excel_HomeNo, Excel_HomeNo_);

        if (Excel_Street_.equals("e"))
            values.put(Excel_Street, "");
        else
            values.put(Excel_Street, Excel_Street_);

        if (Excel_Floor_.equals("e"))
            values.put(Excel_Floor, "");
        else
            values.put(Excel_Floor, Excel_Floor_);

        if (Excel_Flat_.equals("e"))
            values.put(Excel_Flat, "");
        else
            values.put(Excel_Flat, Excel_Flat_);

        if (Excel_Desc_.equals("e"))
            values.put(Excel_Desc, "");
        else
            values.put(Excel_Desc, Excel_Desc_);

        if (Excel_Another_address_.equals("e"))
            values.put(Excel_Another_address, "");
        else
            values.put(Excel_Another_address, Excel_Another_address_);

        if (Excel_Birthdate_.equals("e"))
            values.put(Excel_Birthdate, "");
        else
            values.put(Excel_Birthdate, Excel_Birthdate_);

        if (Excel_Mama_.equals("e"))
            values.put(Excel_Mama, "");
        else
            values.put(Excel_Mama, Excel_Mama_);

        if (Excel_Papa_.equals("e"))
            values.put(Excel_Papa, "");
        else
            values.put(Excel_Papa, Excel_Papa_);

        if (Excel_Home_Phone_.equals("e"))
            values.put(Excel_Home_Phone, "");
        else
            values.put(Excel_Home_Phone, Excel_Home_Phone_);

        // insert row
        long user_row = db.insert(EXCEL_TABLE, null, values);
        if (user_row == -1)
            return false;
        else
            return true;

    }

    public void deleteuser(String id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EFTKAD_TABLE, ID + " = " + id, null);
    }

    public Cursor getMeetings()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + EFTKAD_TABLE, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean ADD_KASHF(String name, String rakam, String street, String photo, int serial, String id,
                             String birthdate, String floor, String flat, String papa, String mama,
                             String phone, String description, String another_addr)
    {

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


    public Cursor getKashfList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + KASHF_TABLE, null);
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(KASHF_TABLE, null, null);
    }

    public void deleteAllFromExcel()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXCEL_TABLE, null, null);
    }

    //////////////////////////////////////////////////////////////
    //birthdate

    public boolean ADD_BIRTHDATE(String name, String birthdate, Long timestamp, String serial)
    {

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

    public Cursor getBirthdate()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + BIRTHDATE_TABLE, null);
    }

    public boolean DoneHappyBirthdate(String id)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        String strSQL = "UPDATE " + BIRTHDATE_TABLE + " SET " + DONE + " = 1  WHERE " + SERIAL + " = '" + id + "'";

        db.execSQL(strSQL);

        return true;
    }

    public void dropAllTabels()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + EFTKAD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KASHF_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BIRTHDATE_TABLE);

    }
}
