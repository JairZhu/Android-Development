package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
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

public class ShaderView extends View {
    Context context;

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int test = 0;
        int width = 200, height = 200;
        int xPos = 100, yPos = 100, xDelta = width + 60, yDelta = height + 60;
        super.onDraw(canvas);
        Paint paint = new Paint();

        canvas.save();
        canvas.translate(30, 0);
        Shader shader = new LinearGradient(0, 0, 150, 150, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT);
        paint.setShader(shader);
        canvas.drawRect(0, 0, 300, 300, paint);

        canvas.translate(340, 0);
        shader = new LinearGradient(0, 0, 150, 150,
                new int[]{Color.RED, Color.GREEN, Color.BLUE},
                new float[]{0, 0.5F, 0.8F}, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawRect(0, 0, 300, 300, paint);

        canvas.translate(340, 0);
        shader = new RadialGradient(100, 100, 200, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(0, 0, 300, 300, paint);
        canvas.restore();

        canvas.translate(30, 400);
        shader = new SweepGradient(150, 150, new int[]{Color.RED, Color.GREEN, Color.BLUE}, new float[]{0f, 0.5f, 0.85f});
        paint.setShader(shader);
        canvas.drawRect(0, 0, 300, 300, paint);

        canvas.translate(340, 0);
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.music1)).getBitmap();
        Shader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        Shader linearGradient = new LinearGradient(0, 0, 100, 100, new int[]{
                Color.WHITE, Color.LTGRAY, Color.TRANSPARENT, Color.GREEN}, null,
                Shader.TileMode.MIRROR); //平铺效果为镜像
        shader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.DARKEN);
        paint.setShader(shader);
        canvas.drawRect(0, 0, 300, 300, paint);

        canvas.translate(340, 0);
        bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.heart)).getBitmap();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        canvas.saveLayer(0, 0, bitmapWidth, bitmapHeight, null, Canvas.ALL_SAVE_FLAG);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        canvas.drawRect(0, 0, bitmapWidth, bitmapHeight, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        linearGradient = new LinearGradient(0, 0, bitmapWidth, bitmapHeight, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(0, 0,  bitmapWidth,  bitmapHeight, paint);
        paint.setXfermode(null);
        canvas.restore();
    }
}
