package com.example.isszym.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start, stop;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取程序界面中的start、stop两个按钮
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        // 创建启动Service的Intent

        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this,FirstService.class);
                intent.putExtra("Test","Start,Hello!");
                // 启动指定Serivce
                startService(intent);
            }
        });
        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(MainActivity.this,FirstService.class);
                intent.putExtra("Test","Stop,Hello!");
                // 停止指定Serivce
                stopService(intent);
            }
        });
    }
}
