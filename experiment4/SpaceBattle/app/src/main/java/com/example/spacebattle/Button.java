package com.example.spacebattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Button {
    Context context;
    final float RADIUS = 80;  // 半径 （虚拟单位）
    final int FONT_SIZE = 48; // 字体大小（虚拟单位）
    String text;               // 按钮文本
    float centerX;
    float centerY;
    Paint paint1;
    Paint paint2;

    Button(Context context) {
        this.context = context;
        paint1 = new Paint();
        paint1.setColor(Color.argb(128, 100, 160, 100));
        paint2 = new Paint();
        paint2.setColor(Color.argb(128, 0, 0, 0));
        paint2.setTextSize(FONT_SIZE); //px
    }

    void draw(Canvas canvas) {
        if (canvas == null) return;
        canvas.drawText(text, Global.v2Rx(centerX - FONT_SIZE), Global.v2Ry(centerY + FONT_SIZE / 2), paint2);
        canvas.drawCircle(Global.v2Rx(centerX), Global.v2Ry(centerY), Global.v2Ry(RADIUS), paint1);// Global.v2Rx！！！
    }

    boolean getPressed(float x, float y) {
        return Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) < RADIUS;
    }

}
