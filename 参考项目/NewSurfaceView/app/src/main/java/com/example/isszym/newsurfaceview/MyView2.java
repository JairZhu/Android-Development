package com.example.isszym.newsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView2 extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    final float PERIOD = 30;
    Context context;                // 带入Activity
    private SurfaceHolder holder;
    private boolean isRun=true;
    float count;

    public MyView2(Context context) {
        super(context);
        this.context = context;   //可以采用(MainActivity)context得到Activity
        // TODO Auto-generated constructor stub
        holder = this.getHolder();
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
        float realW = getWidth();    // 获取屏幕宽度
        float realH = getHeight();   // 获取屏幕高度
        System.out.println("Real Width: "+realW+"Real Height: "+realH);
    }

    @Override  // 销毁时激发，一般在这里将画图的线程停止、释放
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }

    @Override
    public void run() {
        long start = 0;    // 开始时间
        long loopTime = 0;  // 每次循环的实际执行时间
        while(isRun) {
            start = System.currentTimeMillis();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();//锁定画布并返回画布对象Canvas
                draw(canvas, loopTime);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(canvas!= null) {
                    holder.unlockCanvasAndPost(canvas);//解锁画布并显示出来。

                }
            }
            sleep((float)System.currentTimeMillis()-start);  // 睡眠一段时间
            loopTime =  System.currentTimeMillis()-start;    // 本次循环的实际执行时间
        }
    }

    // 睡眠一段时间，使每次循环的时间为PERIOD
    void sleep(float runTime){
        try {
            float leftTime = PERIOD - runTime;  // 剩余时间
            Thread.sleep(leftTime>0 ? (int)leftTime : 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void draw(Canvas canvas, float execTime) {
        Paint p = new Paint(); //创建画笔
        count = count + execTime/1000.0f;   //每秒加1
        if(canvas!=null) {
            canvas.drawColor(Color.BLUE);//设置画布背景颜色
            p.setColor(Color.WHITE);
            Rect r = new Rect(100, 100, 600, 600);
            canvas.drawRect(r, p);
            p.setTextSize(60);
            p.setColor(Color.RED);
            canvas.drawText("这是第" + (int) count + "秒", 120, 180, p);
        }
    }
}



