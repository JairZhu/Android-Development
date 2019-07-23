package com.example.isszym.sms;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ContentResolver resolver = null;
    private static final String URI_SMS_LOG = "content://sms";
    private static final String URI_CALL_LOG = "content://call_log/calls";

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    String body = (String) msg.obj;
                    //TextView tv = (TextView) findViewById(R.id.tv);
                    //tv.setText(body);
                    Log.d("测试",body);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRight();
        resolver = getContentResolver();
        ContentObserver smsObserver = new  SmsContentObserver(this, handler);
        // 第二个参数,true表示观察所有派生Uri
        resolver.registerContentObserver(Uri.parse(URI_SMS_LOG),
                true, smsObserver);

        ContentObserver callObserver = new  CallContentObserver(this, handler);
        resolver.registerContentObserver(Uri.parse(URI_CALL_LOG),
                false, callObserver);
        readSmsLog();
        readCallLog();
    }
    void  setRight() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
    }
    private void readSmsLog() {
        Cursor cursor_sms = resolver.query(Uri.parse(URI_SMS_LOG), new String[]{"_id", "address", "body", "date", "type"}, null, null, "_id desc limit 0,10");
        if (cursor_sms != null){
            while(cursor_sms.moveToNext()){
                String id = cursor_sms.getString(cursor_sms.getColumnIndex("_id"));
                String address = cursor_sms.getString(cursor_sms.getColumnIndex("address"));
                String body = cursor_sms.getString(cursor_sms.getColumnIndex("body"));
                String date = cursor_sms.getString(cursor_sms.getColumnIndex("date"));
                String type = cursor_sms.getString(cursor_sms.getColumnIndex("type"));
                String sms_type = "0".equals(type)?"待发":("1".equals(type)?"收到":"已发送");
                Log.d("测试","sms: "+ id + ":" + address + ":" + body + ":" + date + ":" + sms_type);

            }
            cursor_sms.close();
        }
    }
    private void readCallLog() {
        Cursor cursor_call = resolver.query(Uri.parse(URI_CALL_LOG), new String[]{"_id", "number", "date", "type"}, null, null, "_id desc limit 0,10");
        if (cursor_call != null){
            while(cursor_call.moveToNext()){
                String id = cursor_call.getString(cursor_call.getColumnIndex("_id"));
                String number = cursor_call.getString(cursor_call.getColumnIndex("number"));
                String date = cursor_call.getString(cursor_call.getColumnIndex("date"));
                String type = cursor_call.getString(cursor_call.getColumnIndex("type"));
                String call_type = "1".equals(type)?"接入":("2".equals(type)?"打出":"未接");
                Log.d("测试","call: " + id + ":" + number + ":" + date + ":" + call_type );
            }
            cursor_call.close();
        }
    }
}

class SmsContentObserver extends ContentObserver {
    private Handler handler;
    private Context context;

    public SmsContentObserver(Context context, Handler
            handler) {
        super(handler);
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Uri.parse("content://sms"), null, null, null, "date desc ");
        String sb = "";
        if (c!=null && c.moveToNext()) {
            String sendNumber = c.getString(c.getColumnIndex("address")); //发件人手机号码
            String body = c.getString(c.getColumnIndex("body"));  //信息内容
            //int hasRead = c.getInt(c.getColumnIndex("read")); // 表示是否已经读
            sb = "短信   "+sendNumber + ":" + body  + ":";   // + ((hasRead == 0)?"已读":"未读");
        }
        handler.obtainMessage(100, sb.toString()).sendToTarget();

    }
}

class CallContentObserver extends ContentObserver {
    private Handler handler;
    private Context context;

    public CallContentObserver(Context context, Handler
            handler) {
        super(handler);
        this.handler = handler;
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor_call = resolver.query(Uri.parse("content://call_log/calls"), new String[]{"_id", "number", "date", "type"}, null, null, "_id desc limit 0,10");
        String sb="";
        if(cursor_call != null && cursor_call.moveToNext()){
            String id = cursor_call.getString(cursor_call.getColumnIndex("_id"));
            String number = cursor_call.getString(cursor_call.getColumnIndex("number"));
            String date = cursor_call.getString(cursor_call.getColumnIndex("date"));
            String type = cursor_call.getString(cursor_call.getColumnIndex("type"));
            String call_type = "1".equals(type)?"接入":("2".equals(type)?"打出":"未接");
            sb= "拨号   " + id + ":" + number + ":" + date + ":" + call_type;
        }
        cursor_call.close();
        handler.obtainMessage(100, sb.toString()).sendToTarget();
    }
}