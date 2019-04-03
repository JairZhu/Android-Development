package com.example.isszym.togglebutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import static com.example.isszym.togglebutton.R.styleable.View;

public class MainActivity extends AppCompatActivity {
    ToggleButton toggleButton1;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleButton1=(ToggleButton)findViewById(R.id.toggleButton);
        textView1=(TextView)findViewById(R.id.textView);
        toggleButton1.setChecked(true);
        toggleButton1.setTextOff("无声音");
        toggleButton1.setTextOn("有声音");
        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean b) {
                textView1.setText(toggleButton1.getText()
                               +" "+(toggleButton1.isChecked()?"true":"false"));
            }
        });
    }
}
