package com.example.phonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHandler extends SQLiteOpenHelper {
    private int version;

    public DBOpenHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.version = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table records(id int primary key, number varchar(11), name varchar(50)," +
                "attribution varchar(50), status int, calltime datetime, duration time)");
    }

    //status(通话状态): 0-呼入，1-呼出，2-未接听
    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newv) {
        db.execSQL("");
    }
}
