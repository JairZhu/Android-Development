package com.example.isszym.newalldrawable;

import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView scale=(ImageView) findViewById(R.id.scaleView);
        Drawable d = scale.getDrawable();
        ScaleDrawable sd = new ScaleDrawable(d, 2, 0.01f, 0.01f);
        sd.setLevel(200);  //0~10000

        ImageView imageview = (ImageView) findViewById(R.id.image);
        final ClipDrawable drawable = (ClipDrawable)
                imageview.getDrawable();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    drawable.setLevel(drawable.getLevel() + 200); // 修改ClipDrawable的level值
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0x123;
                handler.sendMessage(msg);
                if (drawable.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 0, 300);
    }
}
