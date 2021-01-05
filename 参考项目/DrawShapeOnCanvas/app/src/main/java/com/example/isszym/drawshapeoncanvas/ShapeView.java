package com.example.isszym.drawshapeoncanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class ShapeView extends View {
    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();                // 创建新画笔
        paint.setColor(Color.BLUE);               // 设置画笔颜色
        paint.setStrokeWidth(2);                  // 设置画笔粗细
        paint.setAntiAlias(true);
        canvas.drawLine(30, 20, 330, 400, paint); //(1)画线（startX,startY,stopX,stopY）
        canvas.drawRect(340, 20, 620, 400,paint); //(2)画矩形（left，top，right，bottom）
        paint.setStyle(Paint.Style.STROKE);      // 设置画笔样式(空心) .默认为FILL(实心)
        paint.setColor(Color.RED);               // 设置画笔颜色
        //(3)画矩形（left，top，right，bottom）
        canvas.drawRect(new RectF(640f, 20f, 920f, 400f), paint);
        //(4)画圆(centerX,centerY,radius)
        canvas.drawCircle(180, 700, 150, paint);
        //(5)画椭圆(left，top，right，bottom）>=level 21
        canvas.drawOval(new RectF(180, 560, 680, 860), paint);
        //(6)画弧：弧线的椭圆,起始度数,顺时钟扫过的度数,用半径线进行封闭
        canvas.drawArc(new RectF(580f, 560f, 880f, 860f), 20f, 80f, true,paint);
        //(7)画弧：弧线的椭圆,起始度数,顺时钟扫过的度数,不用半径线进行封闭
        canvas.drawArc(new RectF(680f, 560f, 980f, 860f), 20f, 80f, false,paint);  //如果不用Rect而是直接写四个数，要求版本>=level 21
        //(8)画圆角矩形Rect，X方向半径，Y方向半径
        canvas.drawRoundRect(new RectF(30f, 1060f, 340f, 1360f),20f, 20f, paint);
        //(9)一次画多条线，每四个数一条线
        canvas.drawLines(new float[]{530f,1030f,560f, 1160f,560f,1160f,700f,1300f}, paint);
        //(10)一次画多个点
        paint.setStrokeWidth(8);
        canvas.drawPoints(new float[]{800f,1100f, 860f,1200f,900f,1300f}, paint);
    }
}
