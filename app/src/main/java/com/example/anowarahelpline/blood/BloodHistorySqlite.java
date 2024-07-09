package com.example.anowarahelpline.blood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BloodHistorySqlite extends SQLiteOpenHelper {

    public  static  final  String DB_NAME = "historyDb";
    public static  final int DB_VERSION = 2;
    public BloodHistorySqlite(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table blood_history_table (id INTEGER primary key autoincrement  ,name TEXT,mobile TEXT,email TEXT,imgUri TEXT,bloodGroup TEXT )");
        Log.d("sqlite","sucess");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists blood_history_table");

    }


    public void insertData(String name,String mobile,String email,String imgUri,String bloodGroup){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues conval = new ContentValues();
        conval.put("name",name);
        conval.put("mobile",mobile);
        conval.put("email",email);
        conval.put("imgUri",imgUri);
        conval.put("bloodGroup",bloodGroup);


        database.insert("blood_history_table",null,conval);


    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from blood_history_table",null);

        return  cursor;
    }

}
