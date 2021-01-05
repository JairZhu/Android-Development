package com.example.isszym.chronometer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Chronometer ch ;
    Button btnStart;
    Button btnPause;
    Button btnReset;
    long time;
    TextView txtTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ch=(Chronometer)findViewById(R.id.chronometer);
        txtTime=(TextView)findViewById(R.id.textView);
        btnStart = (Button)findViewById(R.id.start);
        btnPause = (Button)findViewById(R.id.pause);
        btnReset = (Button)findViewById(R.id.reset);
        reset();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch.setBase(SystemClock.elapsedRealtime());
                ch.start();
                btnStart.setEnabled(false);
                btnPause.setEnabled(true);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 reset();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = (SystemClock.elapsedRealtime() - ch.getBase())/1000+time;
                ch.stop();
                btnStart.setEnabled(true);
                btnPause.setEnabled(false);
            }
        });

        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer ch){
                long now=(SystemClock.elapsedRealtime() - ch.getBase())/1000+time;
                txtTime.setText(""+String.format("%02d:%02d",now/60,now%60));
            }
        });
    }
    void reset() {
        ch.stop();
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
        btnReset.setEnabled(true);
        time=0;
        txtTime.setText("00:00");
        ch.setBase(SystemClock.elapsedRealtime());
    }
}
