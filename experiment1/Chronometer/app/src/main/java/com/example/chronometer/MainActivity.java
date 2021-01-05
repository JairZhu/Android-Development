package com.example.chronometer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Chronometer;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Chronometer chronometer1, chronometer2;
    Button button1, button2, button3;
    long stoptime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer1 = (Chronometer) findViewById(R.id.chronometer1);
        chronometer2 = (Chronometer) findViewById(R.id.chronometer2);
        button1 = (Button) findViewById(R.id.start);
        button2 = (Button) findViewById(R.id.stop);
        button3 = (Button) findViewById(R.id.reset);
        button2.setEnabled(false);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        chronometer2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (stoptime == 0) {
                    chronometer1.setBase(SystemClock.elapsedRealtime());
                    chronometer2.setBase(SystemClock.elapsedRealtime());
                    chronometer1.start();
                    chronometer2.start();
                }
                else {
                    chronometer1.setBase(chronometer1.getBase() + (SystemClock.elapsedRealtime() - stoptime));
                    chronometer1.start();
                    chronometer2.setBase(SystemClock.elapsedRealtime());
                    chronometer2.start();
                }
                button1.setEnabled(false);
                button2.setEnabled(true);
                break;
            case R.id.stop:
                stoptime = SystemClock.elapsedRealtime();
                chronometer1.stop();
                chronometer2.stop();
                button1.setEnabled(true);
                button2.setEnabled(false);
                break;
            case R.id.reset:
                stoptime = 0;
                chronometer1.setBase(SystemClock.elapsedRealtime());
                chronometer2.setBase(SystemClock.elapsedRealtime());
                chronometer1.stop();
                chronometer2.stop();
                button1.setEnabled(true);
                button2.setEnabled(false);
                break;
            case R.id.chronometer2:
                chronometer2.setVisibility(View.INVISIBLE);
        }
    }
}
