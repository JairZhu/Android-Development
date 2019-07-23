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
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.BlurMaskFilter.Blur.INNER;
import static android.graphics.BlurMaskFilter.Blur.NORMAL;
import static android.graphics.BlurMaskFilter.Blur.OUTER;
import static android.graphics.BlurMaskFilter.Blur.SOLID;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by isszym on 2017/5/6.
 */

public class MaskFilterView extends View {
    Context context;
    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    @SuppressLint("NewApi")    //新功能不报错
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null); //关闭硬件加速
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint textPaint = new TextPaint(ANTI_ALIAS_FLAG);
        textPaint.setTextSize(48);
        textPaint.setColor(Color.BLUE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawColor(Color.WHITE);

        BlurMaskFilter blur = new BlurMaskFilter(100, BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blur);
        canvas.drawCircle(300, 240, 150, paint);
        canvas.drawText("NORMAL", 300, 460, textPaint);

        paint.setMaskFilter(new BlurMaskFilter(100, INNER));
        canvas.drawCircle(800, 240, 150, paint);
        canvas.drawText("INNER", 800, 460, textPaint);

        paint.setMaskFilter(new BlurMaskFilter(100, OUTER));
        canvas.drawCircle(300, 660, 150, paint);
        canvas.drawText("OUTER", 300, 880, textPaint);

        paint.setMaskFilter(new BlurMaskFilter(100, SOLID));
        canvas.drawCircle(800, 660, 150, paint);
        canvas.drawText("SOLID", 800, 880, textPaint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setTextSize(180);
        // new float[] {x, y, z}--direction（设置光源的方向），light（设置环境光亮度），
        // specular（定义镜面反射系数），blur（模糊半径）
        EmbossMaskFilter emboss = new EmbossMaskFilter(new float[]{10, 10, 10}, 0.1f, 5f, 5f);
        paint.setMaskFilter(emboss);
        canvas.drawText("中山大学", 220, 1100, paint);

        paint.setStyle(Paint.Style.FILL);  //画笔风格
        paint.setTextSize(120);             //绘制文字大小，单位px
        paint.setStrokeWidth(20);
        canvas.drawText("计算机学院", 280, 1280, paint);
    }
}
