package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by isszym on 2017/5/6.
 */

public class PorterDuffXferView extends View {
    Context context;
    int xOffset, yOffset, xDelta, yDelta;
    int screenW,screenH;
    Bitmap srcBitmap,dstBitmap;
    //源图和目标图宽高
    private int width = 180;
    private int height = 180;
    public PorterDuffXferView(Context context, AttributeSet attrs) {
        super(context, attrs);  this.context = context;
        screenW = ScreenUtil.getScreenW(context);
        screenH = ScreenUtil.getScreenH(context);
        srcBitmap = makeSrc(width, height);
        dstBitmap = makeDst(width, height);
    }
    //创建一个圆形bitmap，作为dst图
    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xDFDF8024);
        c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
        return bm;
    }

    // 创建一个矩形bitmap，作为src图
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xDF669ADF);
        c.drawRect(new RectF(w / 3, h / 3, w * 19 / 20, h * 19 / 20), p);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        xOffset = 10; yOffset = 10;
        xDelta = width+100; yDelta = height+100;
        Paint textPaint= new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);

        for(PorterDuff.Mode mode:PorterDuff.Mode.class.getEnumConstants()){
            drawXfer(canvas,mode);
            canvas.drawText(mode.toString(),xOffset+20,yOffset+height+40,textPaint);
            updatePos();
        }
    }
    void drawXfer(Canvas canvas,PorterDuff.Mode mode) {
        Paint paint = new Paint();
        //创建一个图层，在图层上演示图形混合后的效果
        int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        paint.setXfermode(null);
        canvas.drawBitmap(dstBitmap, xOffset, yOffset, paint);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(mode);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(srcBitmap, xOffset, yOffset, paint);
        canvas.restoreToCount(sc);
    }

    void updatePos() {
        if(xOffset + xDelta+100>= screenW){
            xOffset=10;
            yOffset+=yDelta;
        }
        else{
            xOffset +=xDelta;
        }
    }
}
