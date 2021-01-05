package com.example.isszym.canvasdrawtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SumPathEffect;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class PathView extends View {
    Context context;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(6);
        Path path = new Path();

        paint.setStyle(Paint.Style.FILL);
        path.moveTo(40, 60);    // 移动到坐标340,1060
        path.lineTo(300, 260);   // 再到坐标900,1260
        path.lineTo(100, 300);    // 再到坐标800,1400
        path.close();

        paint.setStyle(Paint.Style.STROKE);
        path.addRect(330, 30, 660, 260, Path.Direction.CCW);

        path.addCircle(820, 120, 100, Path.Direction.CCW);  //counter-clockwise

        path.addOval(30, 480, 300, 650, Path.Direction.CW);//clockwise

        path.addArc(330, 480, 600, 780, 180, 135);//L T R B startAngle sweepAngle

        path.addRoundRect(710, 460, 1000, 660, 20, 20, Path.Direction.CW); //L T R B rx ry
        canvas.drawPath(path, paint);//路径

        path.reset();
        canvas.translate(0,800);
        path.moveTo(140, 0);
        path.lineTo(400, 100);
        path.lineTo(200, 240);
        path.arcTo(100, 160, 300, 300, 0, 180, false);
        canvas.drawPath(path, paint);

        path.reset();
        canvas.translate(400, 0);
        paint.setPathEffect(new DashPathEffect(new float[]{60, 10}, 1));//虚线长，空隙长
        path.moveTo(140, 0);
        path.lineTo(400, 100);
        path.lineTo(200, 240);
        path.arcTo(100, 160, 300, 300, 0, 180, true);
        canvas.drawPath(path, paint);
    }
}
