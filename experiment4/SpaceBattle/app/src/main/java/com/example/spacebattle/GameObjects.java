package com.example.spacebattle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameObjects {
    private Context context;
    private SurfaceHolder holder;
    Buttons buttons;
    Sprites sprites;
    Sprite mySprite;
    String myName;

    GameObjects(Context context, SurfaceHolder holder) {
        this.context = context;
        this.holder = holder;
        buttons = new Buttons(context);
        buttons.pos();
    }

    void draw() {
        Canvas canvas = null;
        long start = System.currentTimeMillis();
        try {
            canvas = holder.lockCanvas();
            Log.v("lockCanvas", (System.currentTimeMillis() - start)+"");
            drawBackground(canvas);
            buttons.draw(canvas);
            if (mySprite != null)
                mySprite.draw(canvas, System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。
            }
        }
        sleep(System.currentTimeMillis() - start);
    }

    void sleep(float runTime) {
        try {
            float leftTime = Global.LOOP_TIME - runTime;  // 剩余时间
            Log.v("leftTime", leftTime+"");
            Thread.sleep(leftTime > 0 ? (int) leftTime : 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void drawBackground(Canvas canvas) {
        if (canvas == null) return;
        Paint paint = new Paint();
        Bitmap bitmap = ((BitmapDrawable) context.getDrawable(R.drawable.cloud)).getBitmap();
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    String getPressedButton(float x, float y) {
        return buttons.getPressedButton(Global.r2Vx(x), Global.r2Vy(y));
    }

}
