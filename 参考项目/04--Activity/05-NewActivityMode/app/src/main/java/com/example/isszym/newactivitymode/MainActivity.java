package com.example.isszym.newactivitymode;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
// Activity1 -- standard（默认） 每次启动新增一个实例
public class MainActivity extends AppCompatActivity {
    static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count++;
        TextView tv1=(TextView)findViewById(R.id.tv1);
        tv1.setText("计数："+count);
    }
    public void clickHandler1(View vw){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void clickHandler2(View vw){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
    public void clickHandler3(View vw){
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }
}
