package com.example.spacebattle;

import android.content.Context;
import android.graphics.Canvas;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Bullets {
    Context context;
    ConcurrentLinkedQueue<Bullet> lqBullets;
    String myName;
    Bullets(Context context, String myName){
        this.context = context;
        lqBullets = new ConcurrentLinkedQueue<Bullet>();
        this.myName = myName;
    }

    Bullet add(String spName, float x, float y, float dir, float step) {
        Bullet bullet = new Bullet(context, spName, x, y, dir, step);
        lqBullets.add(bullet);
        return bullet;
    }

    void draw(Canvas canvas, long loopTime) {
        for (Bullet bullet: lqBullets) {
            bullet.draw(canvas, loopTime);
        }
    }
}
