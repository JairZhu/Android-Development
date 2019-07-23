package com.example.spacebattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet {
    final float RADIUS = 5;   // 子弹半径
    static long seqnoX = 0;   // 当前子弹的最大序号
    String spName;            // 精灵名称（它发出的子弹）
    long  seqno;              // 子弹序号，和精灵名称一起可以唯一确定一颗子弹
    float x;                  // 子弹x轴定位（left）
    float y;                  // 子弹y轴定位（top）
    float dir;                // 移动方向，单位：degree
    float step = 10;          // 移动步幅 取值1~ Any
    boolean me = false;       // 是否是本玩家发出的子弹
    boolean active = true;    // 是否活动（击中精灵或离开画面则变为非活动）
    Paint paint1;
    Context context;

    Bullet(Context context, String spName, float x, float y, float dir, float step) {
        this.context = context;
        this.spName = spName;
        this.seqno = seqnoX++;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.step = step;
        paint1 = new Paint();
        paint1.setColor(Color.RED);
    }

    void draw(Canvas canvas, long loopTime) {
        if (canvas == null) return;
        pos(loopTime);
        canvas.drawCircle(Global.v2Rx(x), Global.v2Ry(y), RADIUS, paint1);
    }

    void pos(long loopTime) {
        float step1 = this.step * loopTime / Global.LOOP_TIME;
        x = x + step1 * (float) Math.cos(dir * Math.PI / 180);
        y = y + step1 * (float) Math.sin(dir * Math.PI / 180);
    }
}
