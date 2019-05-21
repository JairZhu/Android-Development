package com.example.grandwordremember;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHandler extends SQLiteOpenHelper {
    int version;
    public DBOpenHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE words(word varchar(64) primary key, level int default 0, test_count int default 0, correct_count int default 0,last_test_time timestamp)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) { db.execSQL(""); }
}
