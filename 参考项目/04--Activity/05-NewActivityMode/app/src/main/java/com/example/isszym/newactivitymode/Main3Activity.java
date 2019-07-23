package com.example.isszym.newactivitymode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
//  launchMode="singleTask"  每次启动在Task中找实例，
//          找到弹至顶部，没找到新增一个
public class Main3Activity extends AppCompatActivity {
    static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
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
