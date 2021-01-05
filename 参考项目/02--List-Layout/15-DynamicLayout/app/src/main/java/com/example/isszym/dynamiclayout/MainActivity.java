package com.example.isszym.dynamiclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp;   //用于子视图向父视图传递布局参数
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        this.addContentView(ll, lp);  // 加入Activity。可以说省略
        TextView myTextView = new TextView(MainActivity.this); //或this
        myTextView.setText("动态创建布局!");
        myTextView.setTextSize(30);
        LinearLayout.LayoutParams textViewLP;//用于向父视图传递布局参数
        textViewLP = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  //宽
                LinearLayout.LayoutParams.WRAP_CONTENT); //高
        ll.setPadding(20, 20, 10, 10);
        ll.addView(myTextView, textViewLP);
    }
}
