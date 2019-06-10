package com.example.phonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class RecordDB extends ContentProvider {
    private DBOpenHandler dbOpenHandler;
    @Override
    public boolean onCreate() {
        dbOpenHandler = new DBOpenHandler(getContext(), "records", null, 1);
        return true;
    }
    @Override
    public String getType(Uri uri) { return null; }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.insert("records", null, values);
        db.close();
        return uri;
    }
    @Override
    public int delete(Uri uri, String where, String[] whereargs) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int ret = db.delete("records", where, whereargs);
        db.close();
        return ret;
    }
    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereargs) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int num = db.update("records", values, where, whereargs);
        db.close();
        return num;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String where, String[] whereargs, String sortorder) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        return db.query("records", projection, where, whereargs, null, null, sortorder, null);
    }
}
