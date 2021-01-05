package com.example.isszym.font;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tw1 = (TextView)findViewById(R.id.tw1);
        tw1.setTypeface(Typeface.create("sans-serif",Typeface.BOLD) );
    }
}
