package com.example.grandwordremember;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TestDB extends ContentProvider {
    private DBOpenHandler dbOpenHandler;
    @Override
    public boolean onCreate() {
        this.dbOpenHandler = new DBOpenHandler(getContext(), "words", null, 1);
        return true;
    }
    @Override
    public String getType(Uri uri) { return null; }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.insert("words", null, contentValues);
        db.close();
        return uri;
    }
    @Override
    public int delete(Uri uri, String where, String[] whereargs) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int num = db.delete("words", where, whereargs);
        db.close();
        return num;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] whereargs) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int num = db.update("words", contentValues, where, whereargs);
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String where, String[] whereargs, String sortorder) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        return db.query("words", projection, where, whereargs, null, null, sortorder, null);
    }
}
