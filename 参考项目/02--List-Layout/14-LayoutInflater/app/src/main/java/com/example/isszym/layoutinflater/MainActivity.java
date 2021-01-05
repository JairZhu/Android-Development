package com.example.isszym.layoutinflater;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LayoutInflater inflater;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        inflater = LayoutInflater.from(this);
        // LayoutInflater inflater = getLayoutInflater();
        // LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout mainlayout = (LinearLayout)inflater.inflate(R.layout.activity_main, null);
        setContentView(mainlayout);
        layout = (LinearLayout)findViewById(R.id.browse);
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.line_item, null);
                TextView num = (TextView)ll.findViewById(R.id.num);
                num.setText(((EditText)findViewById(R.id.num)).getText());
                TextView name = (TextView)ll.findViewById(R.id.name);
                name.setText(((EditText)findViewById(R.id.name)).getText());
                TextView age = (TextView)ll.findViewById(R.id.age);
                age.setText(((EditText)findViewById(R.id.age)).getText());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                 LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.setPadding(20, 20, 10, 10);
                layout.addView(ll,lp);
            }
        });
     }
}
