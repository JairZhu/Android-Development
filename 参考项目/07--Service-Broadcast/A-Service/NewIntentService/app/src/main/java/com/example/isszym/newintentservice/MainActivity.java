package com.example.isszym.newintentservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View source) {
        // 创建需要启动的Service的Intent
        Intent intent = new Intent(this, MyService.class);
        // 启动Service
        startService(intent);
    }

    public void startIntentService(View source){
        // 创建需要启动的IntentService的Intent
        Intent intent = new Intent(this, MyIntentService.class);
        // 启动IntentService
        startService(intent);
    }
}
