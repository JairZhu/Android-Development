package com.example.isszym.layoutflater;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
        private TextView tv;
        private Button btn;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            LayoutInflater inflater = LayoutInflater.from(this);
            // LayoutInflater inflater = getLayoutInflater();
            // LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_main, null);
            //View layout = inflater.inflate(R.layout.activity_main2, null);
            tv = (TextView) layout.findViewById(R.id.textView);
            tv.setBackgroundColor(Color.YELLOW);
            btn = (Button) layout.findViewById(R.id.button);
            btn.setBackgroundColor(Color.CYAN);
            setContentView(layout);
        }
}
