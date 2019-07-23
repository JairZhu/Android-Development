package com.example.isszym.newhandlermainloop;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1 = null;
    MyThread thrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        thrd = new MyThread();
        thrd.start();
    }
    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) { // 处理消息
            if (msg.what == 0x100)
                tv1.setText("Hi!  -- From Main Looper");
            else if (msg.what == 0x123)
                tv1.setText("Hi!  -- From Sub Looper");
        }
    }
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                new MyHandler(Looper.getMainLooper()).sendEmptyMessage(0x100);
                break;
            case R.id.btn2:
                new MyHandler(thrd.getLooper()).sendEmptyMessage(0x123);
        }
    }
    private class MyThread extends Thread {
        public Looper getLooper() {
            return Looper.myLooper();
        }
        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        }
    }
}

