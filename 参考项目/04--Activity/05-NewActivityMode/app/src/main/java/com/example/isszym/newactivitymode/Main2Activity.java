package com.example.isszym.newactivitymode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
//  launchMode="singleTop"  每次启动看Task的顶部是否是该Activity的实例，
//         是则不新增，直接用他，如果不是则新增一个
public class Main2Activity extends AppCompatActivity {
    static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
