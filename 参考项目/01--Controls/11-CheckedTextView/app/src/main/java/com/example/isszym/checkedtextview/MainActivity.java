package com.example.isszym.checkedtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    CheckedTextView checkedTextView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.textView);
        checkedTextView1=(CheckedTextView)findViewById(R.id.checkedTextView);
        checkedTextView1.setChecked(true);
        tv1.setText("选中");
        checkedTextView1.setOnClickListener(new CheckedTextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                checkedTextView1.toggle();
                tv1.setText(checkedTextView1.isChecked()?"选中":"未选中");
            }
        });
    }
}
