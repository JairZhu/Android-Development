package com.example.phonebook;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MakePhoneCall {
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private ContentResolver resolver;
    private Context context;

    public MakePhoneCall(Context context, ContentResolver resolver) {
        this.context = context;
        this.resolver = resolver;
    }

    public void makeCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
            addNewCallRecord(phoneNumber);
        }
        else {
            Toast.makeText(context,"没有权限，请给予权限！", Toast.LENGTH_LONG).show();
        }
    }

    private void addNewCallRecord(String phoneNumber) {
        //TODO:实现添加记录功能
        ContentValues contentValues = new ContentValues();
        Cursor cursor = resolver.query(callRecordUri, new String[]{"id"}, null, null, "id desc");
        int index;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            index = cursor.getInt(0);
        } else
            index = 0;
        contentValues.put("id", index + 1);
        contentValues.put("number", phoneNumber);
        contentValues.put("status", 1);
        Cursor contactCursor = resolver.query(contactUri, new String[]{"name", "attribution"},
                "number = ?", new String[]{phoneNumber}, null);
        if (contactCursor != null && contactCursor.moveToNext()) {
            contentValues.put("name", contactCursor.getString(contactCursor.getColumnIndex("name")));
            contentValues.put("attribution", contactCursor.getString(contactCursor.getColumnIndex("attribution")));
        }
        else
            contentValues.put("attribution", new QueryAttribution(phoneNumber).getAttribution());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        contentValues.put("calltime", currentTime);
        //TODO:获取通话时间
        //contentValues.put("duration", 0);
        resolver.insert(callRecordUri, contentValues);
    }
}
