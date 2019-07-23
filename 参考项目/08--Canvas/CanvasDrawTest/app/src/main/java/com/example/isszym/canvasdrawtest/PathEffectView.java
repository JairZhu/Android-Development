package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2018/5/2.
 * https://blog.csdn.net/aigestudio/article/details/41447349
 */

public class PathEffectView extends View {
    private float mPhase;// 偏移值
    Path mPath;
    Paint mPaint;
    PathEffect[] mEffects;
    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPath.moveTo(10, 10);     mPath.lineTo(100,100);
        mPath.lineTo(300,10);     mPath.lineTo(500,100);
        mPath.lineTo(800,50);     mPath.lineTo(980,100);
        mEffects = new PathEffect[7];
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEffects[0] = null;
        mEffects[1] = new CornerPathEffect(10);
        mEffects[2] = new DiscretePathEffect(3.0F, 5.0F);
        mEffects[3] = new DashPathEffect(new float[] { 20, 10, 5, 10 }, mPhase);
        Path path1=new Path();
        path1.addRect(0, 0, 8, 8, Path.Direction.CCW);
        mEffects[4] = new PathDashPathEffect(path1, 12, mPhase, PathDashPathEffect.Style.ROTATE);
        mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]);
        mEffects[6] = new SumPathEffect(mEffects[3],mEffects[4]);
        for (int i = 0; i < mEffects.length; i++) {
            canvas.translate(0,160);
            mPaint.setPathEffect(mEffects[i]);
            canvas.drawPath(mPath, mPaint);
        }
        mPhase += 1;
        invalidate(); //要求系统重画
    }
}
