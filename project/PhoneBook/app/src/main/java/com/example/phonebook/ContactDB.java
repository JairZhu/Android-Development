package com.example.phonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContactDB extends ContentProvider {
    private ContactDBHandler contactDBHandler;
    private SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        contactDBHandler = new ContactDBHandler(getContext(), "contacts", null, 1);
        db = contactDBHandler.getWritableDatabase();
        return true;
    }
    @Override
    public String getType(Uri uri) { return null; }
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db.insert("contacts", null, contentValues);
//        db.close();
        return uri;
    }
    @Override
    public int delete(Uri uri, String where, String[] whereargs) {
        int ret = db.delete("contacts", where, whereargs);
//        db.close();
        return ret;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String where, String[] whereargs) {
        int ret = db.update("contacts", contentValues, where, whereargs);
//        db.close();
        return ret;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String where, String[] whereargs, String sortorder) {
        return db.query("contacts", projection, where, whereargs, null, null, sortorder);
    }
}
