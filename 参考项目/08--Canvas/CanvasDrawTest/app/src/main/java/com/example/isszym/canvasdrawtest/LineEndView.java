package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2018/5/2.
 * https://blog.csdn.net/aigestudio/article/details/41447349
 */

public class LineEndView extends View {
    private Paint mPaint;// 画笔对象

    public LineEndView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mPaint.setColor(Color.BLUE);

        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRect(60,80,300,280,mPaint);

        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawRect(400,80,700,280,mPaint);

        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawRect(800,80,1000,280,mPaint);

        canvas.translate(80,600);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(60,60,60,440,mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(400,60,400,440,mPaint);

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(800,60,800,440,mPaint);
    }
}
