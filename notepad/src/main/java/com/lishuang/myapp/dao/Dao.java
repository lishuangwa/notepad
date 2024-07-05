package com.lishuang.myapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao extends SQLiteOpenHelper {
    public static final String TABLE1 = "users";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";


    public static final String TABLE2 = "notes";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String CONTENT = "content";
    public static final String IMG = "img";


    public Dao(Context context) {
        super(context, "my_notepad.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE " + TABLE1 + "( " + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " VARCHAR(30) ," +
                PASSWORD + " ARCHAR(30) )";
        db.execSQL(sql1);
        String sql2 = "CREATE TABLE " + TABLE2 + "( " + ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " VARCHAR(30) ," +
                DATE + " VARCHAR(30) ," +
                CONTENT + " VARCHAR(30) , " +
                IMG + " VARCHAR(1204)  )";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    public Cursor findById(Context context, Long id) {
//        SQLiteDatabase db = new UserDao(context).getReadableDatabase();
//        return db.rawQuery("select * from " + UserDao.TABLE + " where _id =?", new String[]{id + ""});
//    }
}
