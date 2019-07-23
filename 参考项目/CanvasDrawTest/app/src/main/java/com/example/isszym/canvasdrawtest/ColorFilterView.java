package com.example.isszym.canvasdrawtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class ColorFilterView extends View {
    Context context;

    public ColorFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    // @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        //(1)
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        paint.setColor(Color.argb(255, 255, 128, 103));
        canvas.drawCircle(160, 160, 120, paint);
        //(2)
        colorMatrix = new ColorMatrix(new float[]{
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawCircle(500, 160, 120, paint);
        //(3)
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.kale);
        canvas.scale(0.4f, 0.4f);
        colorMatrix = new ColorMatrix(new float[]{
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, 1, 0,
        });

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 1800, 100, paint);
        //(4)
        //paint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
        paint.setColorFilter(new LightingColorFilter(0xFFFF0000, 0x000000FF));
        canvas.drawBitmap(bitmap, 80, 960, paint);
        //(5)
        paint.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN));
        canvas.drawBitmap(bitmap, 960, 960, paint);
        //(6)
        colorMatrix = new ColorMatrix(new float[]{
                1/2f, 1/2f, 1/2f, 0, 0,
                1/3f, 1/3f, 1/3f, 0, 0,
                1/4f, 1/4f, 1/4f, 0, 0,
                0,       0   , 0, 1, 0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, null, new Rect(1800, 960, 1800 + bitmap.getWidth(), 960 + bitmap.getHeight()), paint);
        //(7)
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.button);
        paint.setAntiAlias(true);
        int width = 600;
        int height = width * bitmap.getHeight() / bitmap.getWidth();
        canvas.drawBitmap(bitmap, null, new Rect(80, 1880, 80 + width, 1880 + height), paint);
        //(8)
        paint.setColorFilter(new LightingColorFilter(0x00ff00, 0x000000));
        canvas.drawBitmap(bitmap, null, new Rect(960, 1880, 960 + width, 1880 + height), paint);
        //(9)
        paint.setColorFilter(new LightingColorFilter(0xffffff, 0x0000f0));
        canvas.drawBitmap(bitmap, null, new Rect(1800, 1880, 1800 + width, 1880 + height), paint);

    }
}
