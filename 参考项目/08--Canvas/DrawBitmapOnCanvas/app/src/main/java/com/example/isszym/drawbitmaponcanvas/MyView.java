package com.example.isszym.drawbitmaponcanvas;

/**
 * Created by isszym on 2017/4/25.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();               // 创建新画笔

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.raw.sysu);
        int w = bitmap.getWidth();  //300px
        int h = bitmap.getHeight(); //300px

        //(1) 在(top,left)处显示图片
        canvas.drawBitmap(bitmap, 100, 0, paint);
        //（2）把图片的一部分拷贝到目标区域（可能会伸缩）
        canvas.drawBitmap(bitmap, new Rect(0,0,w/2,h/2), new Rect(300,300,500,500), paint);
        //（3）通过Matrix平移和旋转后复制到画布
        Matrix matrix = new Matrix();
        matrix.setTranslate(600, 500);
        matrix.preRotate(45);
        canvas.drawBitmap(bitmap, matrix, paint);

        //（4）制作透明图片
        Bitmap bitmap1=toTransparentBitmap(bitmap,0xffffffff);
        paint.setAlpha(254);               //设置paint的alpha值，不要到255，高一点就好了
        canvas.drawBitmap(bitmap1,100,1000,paint);//如果背景有图片，就可以看到透明的效果了
       // (5)制作圆形图片
        canvas.drawBitmap(toRoundBitmap(bitmap),500,1000,paint);
    }
    // 把bitmap中的颜色transColor变为透明色
    public Bitmap toTransparentBitmap(Bitmap bitmap,int transColor) {
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        // 最后一个参数为isMutable
        Bitmap bitmappic = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int color;
        for (int px = 0; px < width; px++) {
            for (int py = 0; py < height; py++) {
                color = bitmappic.getPixel(px, py);
                int r=(color & 0x00FF0000)>>16;
                int g=(color & 0x0000FF00)>>8;
                int b=(color & 0x000000FF);
                int r1=(transColor & 0x00FF0000)>>16;
                int g1=(transColor & 0x0000FF00)>>8;
                int b1=(transColor & 0x000000FF);
                // 不采用color == transColor而是采用近似方法
                if(Math.abs(r1-r)+Math.abs(g1-g)+Math.abs(b1-b)<80)
                    bitmappic.setPixel(px, py, 0x00000000);
            }
        }
        return bitmappic;
    }
    // 把一个位图变成圆形位图
    public Bitmap toRoundBitmap(Bitmap bitmap) {
         // 圆形图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 正方形的边长
        int r = (width>height)?height:width; //取最短边做边长
        // 构建一个bitmap作为背景图
        Bitmap backgroundBmp = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);//确定canvas的长宽
        Canvas canvas = new Canvas(backgroundBmp);//背景色默认为白色
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(r/2, r/2, r/2, paint);  //画了一个圆
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        RectF rect = new RectF(0, 0, r, r);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);//与背景叠加
        return backgroundBmp;
    }
}
