package com.example.isszym.newsendmsgtomainthrd;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int COMPLETED = 0x123;
    private static final int DONE = 0x124;
    private int cnt = 0;
    private TextView stateText;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case COMPLETED:
                    stateText.setText("Finished! " + cnt);
                    break;
                case DONE:
                    String body = (String) msg.obj;
                    stateText.setText(body);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateText = (TextView) findViewById(R.id.tv);
    }
    public void onClick1(View v) {
        new Thread(){
            @Override
            public void run() {
                try {  Thread.sleep(2000);  }catch(Exception e) {
                };//耗时的操作
                synchronized(this) {
                    cnt++;  //利用共享变量在主线程和子线程之间传递数据
                }
                handler.sendEmptyMessage(COMPLETED);
            }
        }.start();
    }
    public void onClick2(View v) {
        new Thread(){
            @Override
            public void run() {
                try {Thread.sleep(2000);}catch(Exception e){};//耗时的操作
                synchronized(this) {
                    cnt++;  //利用共享变量在主线程和子线程之间传递数据
                }
                handler.obtainMessage(DONE, "Hello! " + cnt).sendToTarget();
            }
        }.start();
    }
}

