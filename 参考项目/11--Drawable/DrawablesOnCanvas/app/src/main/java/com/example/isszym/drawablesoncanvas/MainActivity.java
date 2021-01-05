package com.example.isszym.drawablesoncanvas;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgScale=(ImageView) findViewById(R.id.scaleView);
        final Drawable scaleDrawable = imgScale.getDrawable();
        scaleDrawable.setLevel(1);  //值越大图形越大（0~10000）

        ImageView transView = (ImageView) findViewById(R.id.transView);
        TransitionDrawable td = (TransitionDrawable) transView.getDrawable();
        td.startTransition(5000);
        //你可以可以反过来播放，使用reverseTransition即可~
        //td.reverseTransition(3000);

        ImageView imageview = (ImageView) findViewById(R.id.clipView);
        final ClipDrawable clipDrawable = (ClipDrawable)
                imageview.getDrawable();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    clipDrawable.setLevel(clipDrawable.getLevel() + 200); // 修改ClipDrawable的level值
                    scaleDrawable.setLevel(scaleDrawable.getLevel() + 500);
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
                if (clipDrawable.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 0, 300);
    }
}
