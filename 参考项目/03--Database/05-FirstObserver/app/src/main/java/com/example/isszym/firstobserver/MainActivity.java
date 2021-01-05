package com.example.isszym.firstobserver;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 100:
                    String body = (String) msg.obj;
                    TextView tv = (TextView) findViewById(R.id.tv);
                    tv.setText(body);
                    break;
            }
        }
    };
    MyContentObserver mContentObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://com.example.providers.firstprovider/#");
        mContentObserver = new MyContentObserver(handler);
        // true表示匹配所有派生Uri
        this.getContentResolver().registerContentObserver(uri, true,mContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContentObserver!=null) {
            this.getContentResolver().unregisterContentObserver(mContentObserver);
        }
    }
}
class MyContentObserver extends ContentObserver {
    Handler handler;
    MyContentObserver(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d("测试","ContentObserver onChange() selfChange=" + selfChange);
        handler.obtainMessage(100, "ContentObserver Changed!").sendToTarget();
    }

    @Override
    public boolean deliverSelfNotifications() {
        Log.d("测试","ContentObserver deliverSelfNotifications!");
        return true;
    }
}


