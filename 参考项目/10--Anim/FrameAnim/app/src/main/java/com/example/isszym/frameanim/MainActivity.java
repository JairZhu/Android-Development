package com.example.isszym.frameanim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.zombie);
        final AnimationDrawable zombie = (AnimationDrawable) imageView.getBackground();
        zombie.start();

        TranslateAnimation anim = new TranslateAnimation(0, 600, 0, 900);
        anim.setDuration(20000);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(-1);
        // 开始位移动画
        imageView.startAnimation(anim);
    }
}
