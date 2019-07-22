package com.example.spacebattle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Context context;                // 带入Activity
    private SurfaceHolder holder;
    private boolean isRun = true;
    private float count;
    private GameObjects gameObjects;

    public GameSurfaceView(Context context) {
        super(context);
        this.context = context;   //可以采用(MainActivity)context得到Activity
        holder = this.getHolder();
        gameObjects = new GameObjects(context, holder);
        holder.addCallback(this);         // 给SurfaceView当前的持有者一个回调对象
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        Thread myThread = new Thread(this); // 创建一个绘图线程
        myThread.start();
    }

    @Override    // 在surface的大小发生改变时激发
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override  // 在创建时激发，一般在这里调用画图线程
    public void surfaceCreated(SurfaceHolder holder) {
        isRun = true;
        Global.realW = getWidth();    // 获取屏幕宽度
        Global.realH = getHeight();   // 获取屏幕高度
        Log.v("Real", "Real Width: " + Global.realW + " Real Height: " + Global.realH);
    }

    @Override  // 销毁时激发，一般在这里将画图的线程停止、释放
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }

    @Override
    public void run() {
        while (isRun) {
            gameObjects.draw();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            String buttonText = gameObjects.getPressedButton(x, y);
            if (buttonText != null) {
                System.out.println("按了[" + buttonText + "]按钮");
                switch (buttonText) {
                    case "关闭":
                        ((Activity) context).finish();
                        break;
                    case "开始":
                        if (gameObjects.myName == null || gameObjects.myName.isEmpty()) {
                            Random random = new Random();
                            gameObjects.myName = String.valueOf(random.nextInt(9999) % (10000));
                            gameObjects.mySprite = new Sprite(context);
                            gameObjects.mySprite.spName = gameObjects.myName;
                        }
                        break;
                    case "开火":
                        break;
                    case "自动":
                        break;
                }
            }
            else {
                Sprite mySprite = gameObjects.mySprite;
                mySprite.dir = mySprite.getDirection(mySprite.x, mySprite.y, x, y);
            }
        }
        return true;
    }
}
