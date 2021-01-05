package com.example.isszym.seekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar1;
    private TextView tv1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar1.setProgress(30);
        tv1 = (TextView) findViewById(R.id.textView);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv1.setText("拖动停止");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv1.setText("开始拖动");
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                tv1.setText("当前进度：" + seekBar1.getProgress() + "/" + seekBar1.getMax());
            }
        });
    }
}
