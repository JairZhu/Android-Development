package com.example.isszym.button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;

public class MainActivity extends AppCompatActivity {
    CheckedTextView checkedTextView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkedTextView1=(CheckedTextView)findViewById(R.id.checkedTextView);
        checkedTextView1.setOnClickListener(new CheckedTextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                checkedTextView1.toggle();
            }
        });
    }
}
