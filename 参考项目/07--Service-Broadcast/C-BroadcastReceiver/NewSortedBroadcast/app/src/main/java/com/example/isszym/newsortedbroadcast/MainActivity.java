package com.example.isszym.newsortedbroadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction("com.example.action.SORTED_BROADCAST");
        intent.putExtra("main", "呵呵，all eceivers, 你们好！");
        sendOrderedBroadcast(intent, null); //第二个参数为String receiverPermission，
            //    要求接收器必须具有相应权限才能正常接收到广播，取值null时没有权限。
    }
}
