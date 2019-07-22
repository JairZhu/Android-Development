package com.example.spacebattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Sprite {
    final float HEIGHT = 120;   // 精灵高度（虚拟尺寸）。final变量(Java常量)只能赋一次值
    final float WIDTH = 120;    // 精灵宽度（虚拟尺寸）
    final int FONT_SIZE = 24;   // 字体大小（虚拟尺寸）
    final float FRAME_DURATION = 1 * Global.LOOP_TIME;  // 每帧持续时间（ms）
    float step = 10;           // 每个LOOP_TIME的移动步幅（用于控制速度）取值1~ Any(虚拟单位)
    String spName;              // 精灵名称
    float x;                    // 精灵x轴定位
    float y;                    // 精灵y轴定位
    float dir;                  // 移动方向，单位：degree
    boolean me = true;          // 区分自己的精灵还是其它用户的精灵
    boolean active = true;     // 是否活动（可见）。重用（reuse）方式可用
    int curFrameIndex;       // 当前帧号，取值0~7
    float frameDuration = 0;   // 当前帧已显示时间

    int[] frames = {R.drawable.sprite1, R.drawable.sprite2, R.drawable.sprite3,
            R.drawable.sprite4, R.drawable.sprite5, R.drawable.sprite6,
            R.drawable.sprite7, R.drawable.sprite8
    };
    Paint paint1;   // 精灵图像画笔
    Paint paint2;   // 精灵文字画笔
    Context context;

    Sprite(Context context) {
        this.context = context;
        paint1 = new Paint();
        paint2 = new Paint();
        paint2.setColor(Color.argb(60, 50, 50, 50));
        paint2.setTextSize(Global.v2Rx(FONT_SIZE));
        curFrameIndex = 0;
        x = y = 0;
    }

    // 绘制精灵：先计算下一帧的索引，计算精灵的位置，再绘制精灵
    void draw(Canvas canvas, long loopTime) {
        if (canvas == null) return;
        nextFrame(loopTime);
        pos(loopTime);
        Bitmap bitmap = ((BitmapDrawable) context.getDrawable(frames[curFrameIndex])).getBitmap();
        bitmap = rotateSprite(bitmap, dir);
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect destRect = new Rect((int) Global.v2Rx(x), (int) Global.v2Ry(y), (int) Global.v2Rx(x + WIDTH), (int) Global.v2Ry(y + HEIGHT));
        canvas.drawBitmap(bitmap, srcRect, destRect, paint1);
    }

    // 设置精灵图像画笔和文字画笔
    void setImagePaint() {
    }

    // 通过累计frameDuration计算下一个帧的索引
    private void nextFrame(long loopTime) {
        frameDuration += loopTime;
        if (frameDuration > FRAME_DURATION) {
            frameDuration = 0;
            curFrameIndex = (curFrameIndex + 1) % 8;
        }
    }

    private void pos(long loopTime) {
        float step1 = this.step * loopTime / Global.LOOP_TIME;
        x = x + step1 * (float) Math.cos(dir * Math.PI / 180);
        y = y + step1 * (float) Math.sin(dir * Math.PI / 180);
        x = x > Global.virtualW - WIDTH ? Global.virtualW - WIDTH : x;
        y = y > Global.virtualH - HEIGHT ? Global.virtualH - HEIGHT : y;
        x = x <= 0 ? 0 : x;
        y = y <= 0 ? 0 : y;
    }

    // 给出两个点(left,top)和（newLeft,newTop），计算出方向（degree）
    float getDirection(float left, float top, float newLeft, float newTop) {
        float dx = newLeft - left;
        float dy = newTop - top;
        double dist = Math.sqrt(dx * dx + dy * dy);
        float theta = 0;
        if ((int) dist != 0) {
            theta = (float) Math.asin(Math.abs(dy) / dist) * 180.0f / (float) Math.PI;
        } else {
            theta = 0;
        }
        if (dx <= 0 && dy >= 0) {
            theta = 180 - theta;
        }
        if (dx <= 0 && dy <= 0) {
            theta = 180 + theta;
        }
        if (dx >= 0 && dy <= 0) {
            theta = 360 - theta;
        }
        return theta;
    }

    // 根据方向（度数）旋转精灵
    Bitmap rotateSprite(Bitmap bitmap, float degree) {
        float deg = degree;
        Matrix matrix = new Matrix();

        if (degree >= 0 && degree <= 90) {
        }
        if (degree >= 90 && degree <= 180) {
            matrix.postTranslate(-bitmap.getWidth(), 0);
            matrix.postScale(-1, 1);    //镜像水平翻转. 垂直(1, -1)
            deg = degree - 180;
        }
        if (degree >= 180 && degree <= 270) {
            matrix.postTranslate(-bitmap.getWidth(), 0);
            matrix.postScale(-1, 1);    //镜像水平翻转. 垂直(1, -1)
            deg = degree - 180;
        }
        if (degree >= 270 && degree <= 360) {
            deg = degree - 360;
        }
        Bitmap tempBmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tempBmp);
        matrix.postRotate(deg, bitmap.getWidth() / 2, bitmap.getHeight() / 2);    //旋转-deg度
        canvas.drawBitmap(bitmap, matrix, null);
        return tempBmp;
    }

    // 射击（产生新子弹）,速度是精灵移动速度的3倍
    Bullet shot(Bullets bullets){
        Point point = getShotStPos();
//        return bullets.add(spName,-1,point.x,point.y,dir,step*3);  //-1为序号
        return null;
    }
    // 根据精灵的位置定位新子弹的初始位置
    Point getShotStPos(){
//        return new Point((int)x1,(int)y1);
        return null;
    }


}
