package com.example.isszym.canvasdrawtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by isszym on 2017/5/6.
 */

public class PathOpView extends View {
    Context context;

    public PathOpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(50);
        //(1)
        Path path1 = new Path();
        Path  path2  =  new  Path();
        path1.addCircle(200,  200,  160,  Path.Direction.CCW);
        path2.addRect(200,  200,  360,  360,  Path.Direction.CCW);
        path1.op(path2,  Path.Op.DIFFERENCE);
        canvas.drawPath(path1,  paint);
        canvas.drawText("DIFFERENCE",40,440,paint);
        //(2)
        path1.reset();
        path2.reset();
        path1.addCircle(700,  200,  160,  Path.Direction.CCW);
        path2.addRect(700,  200,  860,  360,  Path.Direction.CCW);
        path1.op(path2,  Path.Op.INTERSECT);
        canvas.drawPath(path1,  paint);
        canvas.drawText("INTERSECT",540,440,paint);
        //(3)
        path1.reset();
        path2.reset();
        path1.addCircle(200,  700,  160,  Path.Direction.CCW);
        path2.addRect(200,  700,  360,  860,  Path.Direction.CCW);
        path1.op(path2,  Path.Op.UNION);
        canvas.drawPath(path1,  paint);
        canvas.drawText("UNION",140,940,paint);
        //(4)
        path1.reset();
        path2.reset();
        path1.addCircle(700,  700,  160,  Path.Direction.CCW);
        path2.addRect(700,  700,  860,  860,  Path.Direction.CCW);
        path1.op(path2,  Path.Op.REVERSE_DIFFERENCE);
        canvas.drawPath(path1,  paint);
        canvas.drawText("REVERSE_DIFFERENCE",480,940,paint);
        //(5)
        path1.reset();
        path2.reset();
        path1.addCircle(200,  1160,  160,  Path.Direction.CCW);
        path2.addRect(200,  1160,  360,  1320,  Path.Direction.CCW);
        paint.setStyle(Paint.Style.FILL);
        path1.op(path2,  Path.Op.XOR);
        canvas.drawPath(path1,  paint);
        canvas.drawText("XOR",140,1380,paint);

    }
}
