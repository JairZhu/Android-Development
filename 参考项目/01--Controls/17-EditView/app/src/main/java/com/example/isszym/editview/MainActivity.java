package com.example.isszym.editview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.isszym.editview.R.id.text;

public class MainActivity extends AppCompatActivity {
    private String text = "";
    TextView tv1;
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView1);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //final TextView tv3 = (TextView) findViewById(R.id.textView1);
        //final Spinner spinner3 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                tv1.setText((String) spinner1.getSelectedItem());//获取spinner选中的值
                //tv3.setText((String) spinner3.getSelectedItem());//获取spinner选中的值
                //TextView tv2=(TextView)findViewById(R.id.textView1);
                //Spinner spinner2 = (Spinner) findViewById(R.id.spinner1);
                //tv2.setText((String) spinner2.getSelectedItem());//获取spinner选中的值
                // AdapterView sp=(AdapterView)arg0;
                // tv1.setText((String)sp.getSelectedItem().toString()+" "+arg2+" "+arg3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}

