package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class SaveLayerView extends View {
    public SaveLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawColor(Color.LTGRAY);
        int saveCount = canvas.saveLayer(100, 100, 500, 500, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(Color.DKGRAY);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(400, 400, 200, paint);
        canvas.saveLayer(160, 360, 560, 560, null, Canvas.ALL_SAVE_FLAG);
        paint.setColor(Color.CYAN);
        canvas.drawCircle(160, 550, 100, paint);
        canvas.restore();
        canvas.restore();
        //canvas.restoreToCount(2); //可以替换上面两个语句
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(760, 650, 100, paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(200, 200, 800, 800,  paint);
        canvas.saveLayerAlpha(200, 200, 800, 800, 0x88,  Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(700, 500, 200, paint);
        canvas.restore();


//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4);
//        paint.setColor(Color.YELLOW);
//        canvas.drawRect(new RectF(100, 100, 500, 500), paint);
//        paint.setColor(Color.RED);
//        canvas.drawRect(new RectF(160, 360, 560, 560), paint);
//        paint.setColor(Color.MAGENTA);
//        canvas.drawRect(new RectF(200, 200, 800, 800), paint);


    }
}
