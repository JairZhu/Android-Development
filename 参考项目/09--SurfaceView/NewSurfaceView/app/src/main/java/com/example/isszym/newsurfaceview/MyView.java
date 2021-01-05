package com.example.isszym.newsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder holder;
    private boolean isRun=true;

    public MyView(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
        holder = this.getHolder();
        holder.addCallback(this);         // 给SurfaceView当前的持有者一个回调对象
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        holder.setFormat(PixelFormat.TRANSPARENT);
        Thread myThread = new Thread(this); //创建一个绘图线程
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {   //在surface的大小发生改变时激发
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { //在创建时激发，一般在这里调用画图的线程。
        // TODO Auto-generated method stub
        isRun = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { //销毁时激发，一般在这里将画图的线程停止、释放。
        // TODO Auto-generated method stub
    }

    @Override
    public void run() {
        int count = 0;
        while(isRun) {
            Canvas c = null;
            try {
                synchronized (holder)  {
                    c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                    c.drawColor(Color.BLUE);//设置画布背景颜色
                    Paint p = new Paint(); //创建画笔
                    p.setColor(Color.WHITE);
                    Rect r = new Rect(100, 100, 600, 600);
                    c.drawRect(r, p);
                    p.setTextSize(60);
                    p.setColor(Color.RED);
                    c.drawText("这是第"+(count++)+"秒", 120, 180, p);
                    Thread.sleep(1000);//睡眠时间为1秒
                }
            }
            catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            finally {
                if(c!= null) {
                    holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。

                }
            }
        }
    }
}



