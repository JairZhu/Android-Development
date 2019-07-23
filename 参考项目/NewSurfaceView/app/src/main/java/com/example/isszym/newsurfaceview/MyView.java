package com.example.isszym.newsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    MyThread myThread;

    public MyView(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
        holder = this.getHolder();
        holder.addCallback(this);         // 给SurfaceView当前的持有者一个回调对象
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        myThread = new MyThread(holder); //创建一个绘图线程
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {   //在surface的大小发生改变时激发
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { //在创建时激发，一般在这里调用画图的线程。
        myThread.isRun = true;
        myThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { //销毁时激发，一般在这里将画图的线程停止、释放。
        myThread.isRun = false;
    }


}

class MyThread extends Thread {
    private SurfaceHolder holder;
    public boolean isRun;
    float count;
    public MyThread(SurfaceHolder holder) {
        this.holder =holder;
        isRun = true;
    }

    @Override
    public void run() {
        long start=0,end=0,intl=0;
        while(isRun) {
            start = System.currentTimeMillis();
            Canvas canvas = null;
            try {
                 canvas = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                 draw(canvas,intl);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(canvas!= null) {
                    holder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。

                }
            }
            end = System.currentTimeMillis();
            sleep((float)end-start);
            intl =  System.currentTimeMillis()-start;
        }
    }
    void sleep(float duration){
        try {
            float intl = 30 - (duration);
            Thread.sleep(intl>0?(int)intl:0);// 循环间隔为1000毫秒
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void draw(Canvas canvas, float duration) {
        Paint p = new Paint(); //创建画笔
        count = count + duration/1000.0f;
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