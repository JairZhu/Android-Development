package com.example.isszym.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CheckBox chk[];
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.textView);
        chk = new CheckBox[3];
        chk[0]=(CheckBox) findViewById(R.id.checkBox1);
        chk[1]=(CheckBox) findViewById(R.id.checkBox2);
        chk[2]=(CheckBox) findViewById(R.id.checkBox3);
        CheckBox.OnCheckedChangeListener cbListener= new CheckBox.OnCheckedChangeListener(){
          public void onCheckedChanged(CompoundButton btn, boolean b){
              tv1.setText(btn.isChecked()?(btn.getText()+" V"):(btn.getText()+" X"));
          }
        };
        for(int i=0;i<3;i++){
            chk[i].setChecked(false);
            chk[i].setOnCheckedChangeListener(cbListener);
        }
    }
}
