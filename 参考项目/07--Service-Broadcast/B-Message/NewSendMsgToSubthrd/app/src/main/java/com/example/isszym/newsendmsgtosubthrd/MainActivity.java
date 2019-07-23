package com.example.isszym.newsendmsgtosubthrd;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int SEND = 0x100;
    private static final int COMPLETED = 0x123;
    private TextView stateText;
    private Button btn;
    int count = 0;
    WorkThread thr = new WorkThread();  //建立新线程

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateText = (TextView) findViewById(R.id.tv);
        thr.start();   //启动线程
    }

    public void click1(View v) {
        if (thr == null) return;
        count++;
        stateText.setText("count: " + count);
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("status", "Send " + count + "!");
        msg.setData(bundle); // 消息可以带数据
        msg.what = SEND;    // 消息自定义类型. 用arg1,arg2可以定义子类型
        thr.mHandler.sendMessage(msg); //使用子线程Handler发送消息
    }

    public void click2(View v) {
        if (thr == null) return;
        stateText.setText("Finished!");
        Message msg = new Message();
        msg.what = COMPLETED;
        thr.mHandler.sendMessage(msg);
        thr = null;
    }

    class WorkThread extends Thread {
        public Handler mHandler;

        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == SEND) {
                        Log.i("test", msg.getData().getString("status"));
                    } else {
                        if (msg.what == COMPLETED) {
                            Looper.myLooper().quit();  // 结束消息循环。版本大于18时用safetyQuit()
                        }
                    }
                }
            };
            Looper.loop();  //启动消息循环
        }
    }
}
