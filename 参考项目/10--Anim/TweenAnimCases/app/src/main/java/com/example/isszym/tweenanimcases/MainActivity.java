package com.example.isszym.tweenanimcases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imgFlower;
    //TranslateAnimation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFlower = (ImageView) findViewById(R.id.imgFlower);
    }
    public void Scale(View vw){
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        imgFlower.startAnimation(anim);
    }
    public void Rotate(View vw){
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imgFlower.startAnimation(anim);
    }
    public void Alpha(View vw){
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        imgFlower.startAnimation(anim);
    }
    public void Translate(View vw){
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        imgFlower.startAnimation(anim);
    }

    public void Scale1(View vw){
        //fromScaleX,toScaleX,fromScaleY,toScaleY,pivotX,piVotY
        ScaleAnimation anim = new ScaleAnimation(0,2,0,3,
                Animation.RELATIVE_TO_SELF,4f,Animation.RELATIVE_TO_SELF,4f);
        anim.setDuration(3000);
        anim.setFillAfter(true);
        anim.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.anim.accelerate_interpolator));
        imgFlower.startAnimation(anim);
    }

    public void Rotate1(View vw) {
        int method=0;
        //  fromDegree,toDegree,PivotXType,pivotX,PivotYType,pivotY
        RotateAnimation anim= new RotateAnimation(0,1845,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);;

        if(method==1)
            anim = new RotateAnimation(0,1845,0,0);// pivotX=0,pivotY=0 (RELATIVE_TO_SELF)
        else if(method==2)
            anim = new RotateAnimation(0,1845); //pivotX=0,pivotY=0 (RELATIVE_TO_SELF)
        else if(method==3)
           anim = new RotateAnimation(0,1845,
               Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
        anim.setDuration(3000);
        anim.setFillAfter(true);
        anim.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.anim.accelerate_interpolator));
        imgFlower.startAnimation(anim);
    }
     public void Alpha1(View vw){
        AlphaAnimation anim = new AlphaAnimation(1f,0.1f);
        anim.setDuration(3000);
        anim.setFillBefore(false);
        anim.setFillAfter(true);
        anim.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.anim.accelerate_decelerate_interpolator));
        imgFlower.startAnimation(anim);
    }
    public void Translate1(View vw){
        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,3f);
        anim.setStartOffset(2000);
        anim.setDuration(3000);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.anim.anticipate_interpolator));
        imgFlower.startAnimation(anim);
    }

    public void playset(View vw){
        // 加载第一份动画资源
        final Animation anims = AnimationUtils.loadAnimation(this, R.anim.anim);
        // 设置动画结束后保留结束状态
        anims.setFillAfter(true);   //默认为false
        imgFlower.startAnimation(anims);
    }
    public void reverse(View vw){
        final Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        scale.setRepeatCount(Animation.INFINITE);
        scale.setRepeatMode(Animation.REVERSE);
        scale.setFillAfter(true);  // 设置动画结束后保留结束状态

        final Animation trans = AnimationUtils.loadAnimation(this, R.anim.translate);
        trans.setRepeatCount(Animation.INFINITE);
        trans.setRepeatMode(Animation.REVERSE);
        trans.setFillAfter(true);  // 设置动画结束后保留结束状态

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(trans);
        imgFlower.startAnimation(animationSet);
    }

    public void playsets(View vw){
        final Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale1);
        scale.setFillAfter(true);  // 设置动画结束后保留结束状态

        final Animation trans = AnimationUtils.loadAnimation(this, R.anim.translate);
        trans.setFillAfter(true);  // 设置动画结束后保留结束状态

        final Animation anims = AnimationUtils.loadAnimation(this, R.anim.anim1);
        anims.setFillAfter(true);  // 设置动画结束后保留结束状态


        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(anims);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(trans);

        imgFlower.startAnimation(animationSet);
    }
}
