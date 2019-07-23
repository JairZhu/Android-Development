package com.example.isszym.newstartedactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String parm= intent.getStringExtra("parm");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(parm);
    }

    public void clickHandler(View source) {
        finish();
    }
}
