package com.example.isszym.newhandlerallinmain;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(MainActivity.this);
        text = (TextView) findViewById(R.id.tv1);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Looper looper = Looper.myLooper(); // 取得当前线程里的looper
                MyHandler mHandler = new MyHandler(looper); // 构造一个handler 使之可与looper 通信
                //buton 等组件可以由mHandler 将消息传给looper 后, 再放入messageQueue 中, 同时mHandler 也可以接受来自looper 消息
                mHandler.removeMessages(0);   //清除what=0的消息
                String msgStr = " 主线程不同组件通信: 消息来自button";
                Message m = mHandler.obtainMessage(1, 1, 1, msgStr); // 构造要传递的消息
                mHandler.sendMessage(m); // 发送消息: 系统会自动调用handleMessage 方法来处理消息
                break;
        }
    }
    private class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) { // 处理消息
            text.setText(msg.obj.toString());
        }
    }
}
