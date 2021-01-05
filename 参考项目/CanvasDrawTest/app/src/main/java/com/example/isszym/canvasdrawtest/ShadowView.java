package com.example.isszym.canvasdrawtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class ShadowView extends View {
    Context context;
    public ShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);  //取消硬件加速
        Paint paint = new Paint();
        //（1）
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(10, 12, 8, Color.DKGRAY);//需要取消硬件加速
        canvas.drawCircle(460, 120, 100, paint);
        //（2）
        paint.reset();
        paint.setColor(Color.rgb(0,88,37));
        // 阴影半径, X 轴位移, Y 轴位移, 阴影颜色
        paint.setShadowLayer(10, 10, 2, Color.DKGRAY);
        paint.setTextSize(160);
        canvas.drawText("中山大学", 200, 420, paint);

        canvas.translate(160, 480);
        paint.reset();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dogs);
        int width = 600;
        int height = width * bitmap.getHeight() / bitmap.getWidth();
        Bitmap alphaBmp = bitmap.extractAlpha();
         //绘制阴影(3)
        paint.setColor(Color.DKGRAY);
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(alphaBmp, null, new Rect(10, 10, width, height), paint);
        //绘制阴影(4)
        canvas.translate(0, 400);
        canvas.drawBitmap(alphaBmp, null, new Rect(10, 10, width, height), paint);
        //绘制原图像（4）
        paint.setMaskFilter(null);
        canvas.drawBitmap(bitmap, null, new Rect(0, 0, width, height), paint);
    }
}
