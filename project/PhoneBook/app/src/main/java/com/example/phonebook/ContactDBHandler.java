package com.example.phonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDBHandler extends SQLiteOpenHelper {
    private int version;
    public ContactDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts(number varchar(11) primary key, name varchar(50)," +
                "attribution varchar(50), birthday date, whitelist int default 0)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newv) { db.execSQL("");}
}
