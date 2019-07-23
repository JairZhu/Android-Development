package com.example.isszym.customtween;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrice = new DisplayMetrics();
        display.getMetrics(metrice);  // 获取屏幕的宽（metrice.xdpi/2）和高（metrice.ydpi/2）
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setTextSize(64);
        tv.setAnimation(new MyAnimation(metrice.xdpi/2, metrice.ydpi/2, 20500));
    }
}
