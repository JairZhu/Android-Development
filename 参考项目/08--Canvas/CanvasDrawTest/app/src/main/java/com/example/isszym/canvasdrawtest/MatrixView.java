package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class MatrixView extends View {
    Context context;

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawRGB(128, 128, 255);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.raw.sysu);
        int w = bitmap.getWidth();  //300px
        int h = bitmap.getHeight(); //300px
        //(1)原图
       canvas.drawBitmap(bitmap, 0,0, paint);
        //(2)平移
        Matrix matrix = new Matrix();
        matrix.setTranslate(360, 10);//dx,xy
        canvas.drawBitmap(bitmap, matrix, paint);
        //(3)先位移，再缩放
        matrix.setTranslate(720, 10);//会自动复位
        matrix.preScale(0.5f,0.5f);
        canvas.drawBitmap(bitmap, matrix, paint);
        //(4)先缩放，再位移(按缩放比例位移）
        matrix.setTranslate(1440, 320);
        matrix.postScale(0.5f,0.5f);
        canvas.drawBitmap(bitmap, matrix, paint);
        //(5)先平移再错切
        //matrix.setSkew(0.5f,0.1f);   // 这两句
        //matrix.postTranslate(10,320);// 与下面两句功能相同
        matrix.setTranslate(10,320);
        matrix.preSkew(0.5f,0.1f);
        canvas.drawBitmap(bitmap,matrix,paint);
         //(6)先错切再平移
        //matrix.setSkew(0.5f,0.1f);   // 这两句
        //matrix.preTranslate(200,320);// 与下面两句功能相同
        matrix.setTranslate(200,320);
        matrix.postSkew(0.5f,0.1f);
        canvas.drawBitmap(bitmap,matrix,paint);

        //(7)先平移再旋转
        matrix.setRotate(45);
        matrix.postTranslate(240,700);
        canvas.drawBitmap(bitmap, matrix, paint);
        //(8)先旋转再平移(按旋转后的坐标平移)
        matrix.setRotate(45);
        matrix.preTranslate(1000,300);
        canvas.drawBitmap(bitmap, matrix, paint);
        //（9）先移位顺时针再旋转θ角度
        matrix.setSinCos(1, 0, w/2, h/2);//sinθ=1,cosθ=0,px,py
        matrix.postTranslate(760,900);
        canvas.drawBitmap(bitmap, matrix, paint);
    }
}
