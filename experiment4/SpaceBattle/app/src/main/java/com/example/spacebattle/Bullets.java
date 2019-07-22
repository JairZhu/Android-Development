package com.example.spacebattle;

import android.content.Context;

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

}
