package com.example.isszym.drawshapeoncanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class DrawText extends View {
    public DrawText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();               // 创建新画笔
        paint.setColor(Color.BLUE);              // 设置画笔颜色
        paint.setStrokeWidth(2);                 // 设置画笔粗细
        paint.setTextSize(60);
        String str = "ABCDEFGHIJK";
        canvas.drawText(str,20,100,paint);              // 文本 x轴坐标 y轴坐标 画笔
        canvas.drawText(str,1,3,20,200,paint);          // 字符串 开始截取位置 结束截取位置 x轴坐标 y轴坐标 画笔
        char[] chars = str.toCharArray();    // 参数为 (字符数组 起始坐标 截取长度 x轴坐标 y轴坐标 画笔)
        canvas.drawText(chars,1,6,20,300,paint);
        paint.setColor(Color.RED);
        canvas.drawPosText("SLOOP",new float[]{
                100,400, // 第一个字符位置
                180,460, // 第二个字符位置
                280,520, // ...
                320,600,
                420,660 },  paint);
        Path path = new Path(); //定义一条路径
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(110, 310); //移动到 坐标10,10 
        path.lineTo(300, 380);
        path.lineTo(500,450);
        path.lineTo(600,480);
        path.lineTo(700, 500);  //canvas.drawPath(path, paint);
        canvas.drawTextOnPath("ABCDEFGHIJKLMN", path, 110, 510, paint);
    }
}
