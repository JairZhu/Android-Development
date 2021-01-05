package com.example.granddictionary;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DictProvider extends ContentProvider {
    private DBOpenHandler dbOpenHandler;
    @Override
    public boolean onCreate() {
        this.dbOpenHandler = new DBOpenHandler(getContext(), "DICT", null, 1);
        return true;
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.insert("dict", null, contentValues);
        db.close();
        return uri;
    }
    @Override
    public int delete(Uri uri, String where, String[] whereargs) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int ret = db.delete("dict", where, whereargs);
        db.close();
        return ret;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] whereArgs) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        int num = db.update("dict", contentValues, where, whereArgs);
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String where, String[] whereArgs, String sortorder) {
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        Cursor cursor = db.query("dict", projection, where, whereArgs, null,
                null, sortorder, null);
        return cursor;
    }

}
