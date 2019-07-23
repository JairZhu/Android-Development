package com.example.isszym.widgets;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv=new TextView(this);
        int color=0x8FFF;
        tv.setTextColor(color);
        int color1=getResources().getColor(R.color.colorAccent,null);
    }
}
