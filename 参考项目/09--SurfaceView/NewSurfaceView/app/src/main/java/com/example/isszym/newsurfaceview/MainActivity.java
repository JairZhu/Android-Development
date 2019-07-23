package com.example.isszym.newsurfaceview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.isszym.newsurfaceview.R.id.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
        //setContentView(R.layout.activity_main);

    }
}
