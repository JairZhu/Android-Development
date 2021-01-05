package com.example.isszym.canvasdrawtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by isszym on 2017/5/6.
 */

public class PorterDuffView extends View {
    Context context;
    int xOffset, yOffset, xDelta, yDelta;
    int canvasWidth;
    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);  this.context = context;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth=canvas.getWidth();
        xOffset = 10; yOffset = 10;
        xDelta = 200; yDelta = 260;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int width = 180;
        int height = width * bitmap.getHeight() / bitmap.getWidth();

        for(PorterDuff.Mode mode:PorterDuff.Mode.class.getEnumConstants()){
            paint.setColorFilter(new PorterDuffColorFilter(Color.RED, mode));
            canvas.drawBitmap(bitmap, null, new Rect(xOffset, yOffset, xOffset + width, yOffset + height), paint);
            paint.setColorFilter(null);
            canvas.drawText(mode.toString(),xOffset+20,yOffset+height+40,paint);
            updatePos();
        }
    }
    void updatePos() {
        if(xOffset + xDelta+100>= canvasWidth){
            xOffset=10;
            yOffset+=yDelta;
        }
        else{
            xOffset +=xDelta;
        }
    }
}
