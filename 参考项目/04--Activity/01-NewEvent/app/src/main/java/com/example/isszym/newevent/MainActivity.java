package com.example.isszym.newevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    TextView tv;
    @Override
    public void onClick(View v){
        tv.setText("Button4 is clicked!");
    }
    // 把事件处理器直接绑定到标签上
    public void clickHandler1(View source) {
        Toast.makeText(this, "Button1 is clicked!", Toast.LENGTH_SHORT).show();
    }

    class MyClickListener implements OnClickListener {
        @Override
        public void onClick(View v){
            Toast.makeText(MainActivity.this, "Button2 is clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        // 使用监听类作为事件监听器
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new MyClickListener());

        // 使用匿名类作为事件监听器
        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this, "Button3 is clicked!", Toast.LENGTH_SHORT).show();
            }
        });
       // 把Activity本身作为监听类
        Button btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(this);
    }

}
