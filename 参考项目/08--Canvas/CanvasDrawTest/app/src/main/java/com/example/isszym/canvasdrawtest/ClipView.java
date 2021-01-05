package com.example.isszym.canvasdrawtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
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
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class ClipView extends View {
    private Context mContext;
    private Paint mPaint;
    private Path mPath;

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setFocusable(true);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
        mPaint.setTextSize(16);
        mPaint.setTextAlign(Paint.Align.RIGHT);

        mPath = new Path();
    }
    private void drawScene(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(16);
        canvas.drawLine(10, 10, 320, 320, mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(12);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(80, 240,110, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(60);
        canvas.drawText("Clipping", 300, 80, mPaint);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint textPaint=new Paint();
        textPaint.setTextSize(40);
        canvas.drawColor(Color.GRAY);
        //(1)
        canvas.save();
        canvas.translate(20, 20);
        canvas.drawText("(1)clipRect",80,340,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        drawScene(canvas);
        canvas.restore();
        //(2)
        canvas.save();
        canvas.translate(350, 20);
        canvas.drawText("(2)clipPath1",80,340,textPaint);
        Path path = new Path();
        path.moveTo(50,50);
        path.lineTo(200,20);
        path.lineTo(300,120);
        path.lineTo(160,300);
        path.lineTo(50,200);
        path.close();
        canvas.clipPath(path);
        drawScene(canvas);
        canvas.restore();
        //(3)
        canvas.save();
        canvas.translate(700, 20);
        canvas.drawText("(3)clipPath2",80,340,textPaint);
        path.addCircle(200f,200f,100f, Path.Direction.CCW);
        canvas.clipPath(path);//XOR
        drawScene(canvas);
        canvas.restore();
        // A--Rect  B--path
        path.reset();
        path.addCircle(120f,280f,100f, Path.Direction.CCW);
        //(4)DIFFERENCE
        canvas.save();
        canvas.translate(20, 380);
        canvas.drawText("(4)DIFFERENCE",40,420,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        canvas.clipPath(path, Region.Op.DIFFERENCE);
        drawScene(canvas);
        canvas.restore();
        //(5)REPLACE
        canvas.save();
        canvas.translate(350, 380);
        canvas.drawText("(5)REPLACE",40,420,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        canvas.clipPath(path, Region.Op.REPLACE);
        drawScene(canvas);
        canvas.restore();
        //(6)UNION
        canvas.save();
        canvas.translate(700, 380);
        canvas.drawText("(6)UNION",40,420,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        canvas.clipPath(path, Region.Op.UNION);
        drawScene(canvas);
        canvas.restore();
        //(7)XOR
        canvas.save();
        canvas.translate(20, 820);
        canvas.drawText("(7)XOR",40,420,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        canvas.clipPath(path, Region.Op.XOR);
        drawScene(canvas);
        canvas.restore();
        //(8)INTERSECT
        canvas.save();
        canvas.translate(350, 820);
        canvas.drawText("(8)INTERSECT",10,420,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        canvas.clipPath(path, Region.Op.INTERSECT);
        drawScene(canvas);
        canvas.restore();
        //(9)REVERSE_DIFFERENCE
        canvas.save();
        canvas.translate(700, 820);
        canvas.drawText("(9)REVERSE_DIFFERENCE",10,420,textPaint);
        canvas.clipRect(10, 10, 300, 300);
        canvas.clipPath(path, Region.Op.REVERSE_DIFFERENCE);
        drawScene(canvas);
        canvas.restore();

    }


}



