package com.example.isszym.canvasdrawtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class ShapeView extends View {
    Context context;

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int test = 32;
        super.onDraw(canvas);
        Paint paint = new Paint();

        paint.setStrokeWidth(4);   // 设置画笔粗细
        paint.setColor(Color.BLUE); //// 设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(240, 240, 200, paint);

        canvas.translate(480, 40);
        paint.reset();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true); //抗锯齿
        paint.setDither(true);    //抖动（会让线条更圆滑）
        canvas.drawLine(10, 20, 200, 20, paint);
        paint.setStrokeWidth(6);                  // 设置画笔粗细
        canvas.drawLine(10, 60, 200, 60, paint);

        canvas.translate(320, 0);
        paint.reset();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(90, 90, 80, paint);
        paint.setAntiAlias(true);
        canvas.drawCircle(90, 300, 80, paint);

        canvas.translate(-800, 300);
        paint.reset();
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(70, 114, 930, 286, paint);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(160, 200, 80, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(500, 200, 80, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(840, 200, 80, paint);

        canvas.translate(0, 300);
        paint.reset();
        paint.setStrokeJoin(Paint.Join.BEVEL);
        paint.setStrokeWidth(40);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(100, 100, 300, 300, paint);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRect(400, 100, 600, 300, paint);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawRect(700, 100, 900, 300, paint);

        canvas.translate(0, 300);
        paint.reset();
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeWidth(80);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(100, 100, 300, 300, paint);
        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(400, 100, 600, 300, paint);
        paint.setColor(Color.RED);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(700, 100, 900, 300, paint);

//        canvas.translate(0, 300);
//        paint.reset();
//        paint.setStrokeMiter(20);
//        paint.setStrokeWidth(80);
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawLine(100, 100, 300, 300, paint);
//        paint.setColor(Color.BLUE);
//        paint.setStrokeMiter(90);
//        canvas.drawLine(400, 100, 600, 300, paint);
//        paint.setColor(Color.RED);
//        paint.setStrokeMiter(120);
//        canvas.drawLine(700, 100, 900, 300, paint);
    }
}
