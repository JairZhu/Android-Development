package com.example.isszym.drawtextoncanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/4/30.
 */

public class MyView extends View {
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        // 文本(要绘制的内容)
        String str = "SLOOP";
        paint.setColor(Color.BLUE);  // 设置画笔颜色
        paint.setStrokeWidth(2);     // 设置画笔粗细
        paint.setTextSize(80);
        // (1)参数:字符串 起始位置(x,y) 画笔
        canvas.drawText(str, 100, 80, paint);

        paint.setColor(Color.GREEN);
        // (2）参数:字符串 开始截取位置
        //          结束截取位置 x轴坐标 y轴坐标 画笔
        canvas.drawText(str, 1, 3, 100, 250, paint);

        paint.setColor(Color.CYAN);
        char[] chars = str.toCharArray();
        // （3）参数：字符数组 起始 截取长度
        //             x轴坐标 y轴坐标 画笔
        canvas.drawText(chars, 1, 3, 100, 400, paint);

        paint.setColor(Color.BLACK);
        // （4）每个字符定位显示
        canvas.drawPosText(str, new float[]{
                100, 510, // 第一个字符位置
                200, 550, // 第二个字符位置
                250, 630, // ...
                300, 740,
                360, 810}, paint);
        Path path = new Path(); //定义一条路径
        path.moveTo(610, 510); //移动到 坐标10,10
        path.lineTo(650, 660);
        path.lineTo(800, 680);
        path.lineTo(610, 810);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        paint.setColor(Color.RED);
        //（5）按路径显示字符串
        canvas.drawTextOnPath("AB CDE FGHI JK",
                              path, 10, 10, paint);
    }
}
