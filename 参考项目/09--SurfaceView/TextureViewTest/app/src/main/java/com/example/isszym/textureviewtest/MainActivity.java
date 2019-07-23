package com.example.isszym.textureviewtest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, TextureView.SurfaceTextureListener{

    private TextureView mSurface;
    private DrawingThread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        findViewById(R.id.button_transform).setOnClickListener(this);
        mSurface = (TextureView)findViewById(R.id.surface);
        mSurface.setOnTouchListener(this);
        mSurface.setSurfaceTextureListener(this);
    }

    @Override
    public void onClick(View v) {
        // 旋转整个绘制视图
        mSurface.animate().rotation(mSurface.getRotation() < 180.f ? 180.f : 0.f);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mThread.addItem((int)event.getX(), (int)event.getY());
        }
        return true;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mThread = new DrawingThread(new Surface(surface),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mThread.updateSize(width, height);
        mThread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        mThread.updateSize(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mThread.quit();
        mThread = null;

        // 返回 true 并允许框架释放 Surface
        return true;
    }

    /**
     *  当 SurfaceTexture 渲染一个新的帧时会调用该方法。本例中不用
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    private class DrawingThread extends HandlerThread implements Handler.Callback{

        private static final int MSG_ADD = 100;
        private static final int MSG_MOVE = 101;
        private static final int MSG_CLEAR = 102;

        private int mDrawingWidth, mDrawingHeight;
        private boolean mRunning = false;

        private Surface mDrawingSurface;
        private Rect mSurfaceRect;
        private Paint mPaint;

        private Handler mReceiver;
        private Bitmap mIcon;
        private ArrayList<DrawingItem> mLocations;

        private class DrawingItem {
            // 当前位置标识
            int x, y;
            // 运动方向的标识
            boolean horizontal, vertical;

            public DrawingItem(int x, int y, boolean horizontal, boolean vertical) {
                this.x = x;
                this.y = y;
                this.horizontal = horizontal;
                this.vertical = vertical;
            }
        }


        public DrawingThread(Surface surface, Bitmap icon) {
            super("DrawingThread");
            mDrawingSurface = surface;
            mSurfaceRect = new Rect();
            mLocations = new ArrayList<>();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mIcon = icon;
        }

        @Override
        protected void onLooperPrepared() {
            mReceiver = new Handler(getLooper(), this);
            // 开始渲染
            mRunning = true;
            mReceiver.sendEmptyMessage(MSG_MOVE);
        }

        @Override
        public boolean quit() {
            // 退出前清除所有的消息
            mRunning = false;
            mReceiver.removeCallbacksAndMessages(null);
            return super.quit();
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ADD:
                    // 在触摸的位置创建一个新的条目，该条目的开始方向是随机的
                    DrawingItem newItem = new DrawingItem(msg.arg1, msg.arg2,
                            Math.round(Math.random()) == 0,
                            Math.round(Math.random()) == 0);
                    mLocations.add(newItem);
                    break;
                case MSG_CLEAR:
                    // 删除所有的对象
                    mLocations.clear();
                    break;
                case MSG_MOVE:
                    // 如果取消，则不做任何事情
                    if (!mRunning) return true;

                    // 渲染一帧
                    try {
                        // 锁定 SurfaceView，并返回到要绘图的 Canvas
                        Canvas canvas = mDrawingSurface.lockCanvas(mSurfaceRect);
                        // 首先清空 Canvas
                        canvas.drawColor(Color.BLACK);
                        // 绘制每个条目
                        for (DrawingItem item : mLocations) {
                            // 更新位置
                            item.x += (item.horizontal ? 5 : -5);
                            if (item.x >= (mDrawingWidth - mIcon.getWidth())) {
                                item.horizontal = false;
                            }
                            if (item.x <= 0) {
                                item.horizontal = true;
                            }
                            item.y += (item.vertical ? 5 : -5);
                            if (item.y >= (mDrawingHeight - mIcon.getHeight())) {
                                item.vertical = false;
                            }
                            if (item.y <= 0) {
                                item.vertical = true;
                            }
                            canvas.drawBitmap(mIcon, item.x, item.y, mPaint);
                        }
                        // 解锁 Canvas，并渲染当前的图像
                        mDrawingSurface.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            // 发送下一帧
            if (mRunning) {
                mReceiver.sendEmptyMessage(MSG_MOVE);
            }
            return true;
        }

        public void updateSize(int width, int height) {
            mDrawingWidth = width;
            mDrawingHeight = height;
            mSurfaceRect.set(0, 0, mDrawingWidth, mDrawingHeight);
        }

        public void addItem(int x, int y) {
            // 通过 Message 参数将位置传给处理程序
            Message msg = Message.obtain(mReceiver, MSG_ADD, x, y);
            mReceiver.sendMessage(msg);
        }

        public void clearItems() {
            mReceiver.sendEmptyMessage(MSG_CLEAR);
        }
    }
}

