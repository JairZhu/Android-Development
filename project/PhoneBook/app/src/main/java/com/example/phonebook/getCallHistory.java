package com.example.phonebook;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getCallHistory {
    private String number;
    private String date;
    private int type;
    private int duration;

    getCallHistory(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                    new String[]{CallLog.Calls.CACHED_NAME,
                            CallLog.Calls.NUMBER,
                            CallLog.Calls.TYPE,
                            CallLog.Calls.DATE,
                            CallLog.Calls.DURATION,
                            CallLog.Calls.TYPE
                    }, null, null, "date desc limit 1");
            cursor.moveToNext();
            number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));
            int type_temp = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
            switch (type_temp) {
                case CallLog.Calls.INCOMING_TYPE:
                    type = 0;
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    type = 1;
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    type = 2;
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    type = 2;
            }
            cursor.close();
        }
    }

    String getNumber() {
        return number;
    }

    String getDate() {
        return date;
    }

    int getDuration() {
        return duration;
    }

    int getType() {
        return type;
    }
}
