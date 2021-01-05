package com.example.isszym.drawalloncanvas;

/**
 * Created by isszym on 2017/4/25.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();               // 创建新画笔
        paint.setColor(Color.BLUE);              // 设置画笔颜色
        canvas.drawCircle(180, 200, 160, paint);  // 画圆

        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(8);                  // 设置画笔粗细
        canvas.drawLine(100,154,400,400,paint);   // 画线

        paint.setColor(Color.RED);
        paint.setTextSize(100);                   //设置文字大小
        paint.setStyle(Paint.Style.STROKE);     // 设置画笔样式(空心) .默认为FILL
        canvas.drawText("Hello, world!",360,340,paint); //画出一段文字

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.raw.sysu);
        canvas.drawBitmap(bitmap,820,340,new Paint());  //  贴图到画布上
    }
}
