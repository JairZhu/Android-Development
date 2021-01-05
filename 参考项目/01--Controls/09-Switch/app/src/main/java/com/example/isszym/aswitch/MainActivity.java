package com.example.isszym.aswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textview1;
    Switch switch1;
    Switch switch2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview1=(TextView)findViewById(R.id.textView);
        switch1=(Switch)findViewById(R.id.switch1);
        switch2=(Switch)findViewById(R.id.switch2);
        Switch.OnCheckedChangeListener swListener= new Switch.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton btn, boolean b){
                if(btn.getId()==R.id.switch1){
                    switch2.setChecked(switch1.isChecked());
                } else {
                    switch1.setChecked(switch2.isChecked());
                }
                if(switch1.isChecked())
                    textview1.setText("需要密码");
                else
                    textview1.setText("不需要密码");
            }
        };
        switch1.setOnCheckedChangeListener(swListener);
        switch2.setOnCheckedChangeListener(swListener);
        switch1.setChecked(true);
        //switch2.setChecked(true);
    }
}
