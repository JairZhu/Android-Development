package com.example.isszym.shapedrawable;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by isszym on 2018/5/21.
 */

public class MyView extends View {
    private ShapeDrawable mDrawable;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    @Override
    protected void onDraw(Canvas canvas){
        int x = 2;
        int y = 2;
        int width = 200;
        int height = 200;

        mDrawable = new ShapeDrawable(new OvalShape());
        mDrawable.getPaint().setColor(0xff74AC23);
        mDrawable.setBounds(x, y, x + width, y + height);
        mDrawable.draw(canvas);
    }
}
