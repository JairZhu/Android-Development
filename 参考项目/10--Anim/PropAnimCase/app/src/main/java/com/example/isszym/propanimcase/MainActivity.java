package com.example.isszym.propanimcase;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout container = (LinearLayout) findViewById(R.id.activity_main);
        container.addView(new MyAnimationView(this));  // 设置该窗口显示MyAnimationView组件
        final ImageView imgView1 = (ImageView) findViewById(R.id.sysu1);
        imgView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                        .setDuration(2000)
                        .start();

            }
        });
        ImageView imgView2 = (ImageView) findViewById(R.id.sysu2);
        imgView2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startAnim();
                                        }
                                    }

        );
    }

    void startAnim() {
        final ImageView imgView2 = (ImageView) findViewById(R.id.sysu2);
        ObjectAnimator anim1 = new ObjectAnimator()
                .ofFloat(imgView2, "scale", 1.0f, 0.5f)
                .setDuration(8000);
        anim1.start();
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override            //10ms一次
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                imgView2.setScaleX(scale);
                imgView2.setScaleY(scale);
            }
        });
        ObjectAnimator anim2 = new ObjectAnimator()
                .ofInt(imgView2, "alpha", 255, 0)
                .setDuration(8000);
        anim2.start();

        anim1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imgView2.setAlpha(255);
                imgView2.setScaleX(1);
                imgView2.setScaleY(1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }
}
