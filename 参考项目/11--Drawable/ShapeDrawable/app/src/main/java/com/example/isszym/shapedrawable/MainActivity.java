package com.example.isszym.shapedrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //椭圆形形状
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable drawable1 = new ShapeDrawable(ovalShape);
        drawable1.getPaint().setColor(Color.BLUE);
        drawable1.getPaint().setStyle(Paint.Style.FILL);
        findViewById(R.id.textView1).setBackground(drawable1);

        //矩形形状
        RectShape rectShape = new RectShape();
        ShapeDrawable drawable2 = new ShapeDrawable(rectShape);
        drawable2.getPaint().setColor(Color.RED);
        drawable2.getPaint().setStyle(Paint.Style.FILL);
        findViewById(R.id.textView2).setBackground(drawable2);

        //一个继承自ShapeDrawable更为通用、可以直接使用的形状
        PaintDrawable drawable3 = new PaintDrawable(Color.GREEN);
        drawable3.setCornerRadius(30);
        findViewById(R.id.textView3).setBackground(drawable3);

        //扇形、扇面形状
        //顺时针,开始角度45， 扫描的弧度跨度270
        ArcShape arcShape = new ArcShape(45, 270);
        ShapeDrawable drawable4 = new ShapeDrawable(arcShape);
        drawable4.getPaint().setColor(Color.YELLOW);
        drawable4.getPaint().setStyle(Paint.Style.FILL);
        findViewById(R.id.textView4).setBackground(drawable4);

        Path path = new Path();
        path.moveTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 240);
        path.lineTo(240, 220);
        path.lineTo(300, 50);
        path.lineTo(50, 0);
        PathShape pathShape = new PathShape(path, 300, 300);
        ShapeDrawable drawable5 = new ShapeDrawable(pathShape);
        drawable5.getPaint().setColor(Color.CYAN);
        drawable5.getPaint().setStyle(Paint.Style.FILL);
        findViewById(R.id.textView5).setBackground(drawable5);

        //圆角矩形形状
        float[] outerRadii = {20, 20, 40, 40, 60, 60, 80, 80};//外矩形 左上、右上、右下、左下 圆角半径
        RectF inset = new RectF(100, 100, 200, 200);//内矩形距外矩形，左上角x,y距离， 右下角x,y距离
        float[] innerRadii = {20, 20, 20, 20, 20, 20, 20, 20};//内矩形 圆角半径
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, innerRadii); //无内矩形
        ShapeDrawable drawable6 = new ShapeDrawable(roundRectShape);
        drawable6.getPaint().setColor(Color.MAGENTA);
        drawable6.getPaint().setAntiAlias(true);
        drawable6.getPaint().setStrokeWidth(1);
        drawable6.getPaint().setStyle(Paint.Style.STROKE);//描边
        findViewById(R.id.textView6).setBackground(drawable6);
        // Shader
        ArcShape arcShape2 = new ArcShape(45, 270);
        ShapeDrawable drawable7 = new ShapeDrawable(arcShape2);
        drawable7.getPaint().setColor(Color.YELLOW);
        drawable7.getPaint().setStyle(Paint.Style.FILL);
        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.zly)).getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader
                .TileMode.REPEAT);
        Matrix matrix = new Matrix();
        matrix.setTranslate(-480,-20);
        matrix.preScale(400.00f / bitmap.getWidth(), 400.00f / bitmap.getHeight());//view:w=600,h=600
        bitmapShader.setLocalMatrix(matrix);
        drawable7.getPaint().setShader(bitmapShader);
        findViewById(R.id.textView7).setBackground(drawable7);
        // 渐变
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                                               new int[]{Color.RED,Color.YELLOW});
        findViewById(R.id.textView8).setBackground(gradientDrawable);


    }
}
