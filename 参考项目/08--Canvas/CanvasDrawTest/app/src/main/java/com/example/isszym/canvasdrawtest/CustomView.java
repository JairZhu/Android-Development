package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by isszym on 2018/4/11.
 */

public class CustomView extends View {
    private final static String TAG = CustomView.class.getSimpleName();
    private Paint mPaint;
    private RectF oval;
    int mDx, mDy;
    float mRadius;
    int mShadowColor;

    public CustomView(Context context) throws Exception  {
        super(context);
        Log.e("err", "init 3");
        //init(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) throws Exception  {
        super(context, attrs);
        Log.e("err", "init 1 ");
        init(context,attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        Log.e("err", "init 2");
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) throws Exception {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval=new RectF();
        //setLayerType(LAYER_TYPE_SOFTWARE,null);
        /** 提取属性定义   */
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Shadow);
        int BitmapID = typedArray.getResourceId(R.styleable.Shadow_src,-1);
        if (BitmapID == -1){
            throw new Exception("BitmapShadowView 需要定义Src属性,而且必须是图像");
        }
        mDx = typedArray.getInt(R.styleable.Shadow_shadowDx,21);
        mDy = typedArray.getInt(R.styleable.Shadow_shadowDy,21);
        mRadius = typedArray.getFloat(R.styleable.Shadow_shadowRadius,21);
        mShadowColor = typedArray.getColor(R.styleable.Shadow_shadowColor,Color.BLACK);
        typedArray.recycle();
//        Log.e("attrs", "mDx="+mDx);
//        Log.e("attrs", "mDx="+mDx);
//        Log.e("attrs", "mDy="+mDy);
//        Log.e("attrs", "mRadius="+mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY: break;
            case MeasureSpec.AT_MOST: break;
            case MeasureSpec.UNSPECIFIED: break;
        }
//        Log.e(TAG, "onMeasure--widthMode-->" + widthMode);
//        Log.e(TAG, "onMeasure--widthSize-->" + widthSize);
//        Log.e(TAG, "onMeasure--heightMode-->" + heightMode);
//        Log.e(TAG, "onMeasure--heightSize-->" + heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int width = getWidth();
        int height = getHeight();
        Log.e(TAG, "onDraw---->" + width + "*" + height);
        float radius = width / 4;
        canvas.drawCircle(width / 2, width / 2, radius, mPaint);
        mPaint.setColor(Color.BLUE);
        oval.set(width / 2 - radius, width / 2 - radius, width / 2
                + radius, width / 2 + radius);//用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, 270, 120, true, mPaint);  //根据进度画圆弧
    }
}
