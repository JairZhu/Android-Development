package com.example.isszym.bitmapbytes;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View tv=findViewById(R.id.textView);
        View btn=findViewById(R.id.button);
        //getPicturePixel(getBitmapFromView(tv));
        getPicturePixel(getBitmapFromView(btn));
    }

    public Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }
        private void getPicturePixel(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 保存所有的像素的数组，图片宽×高
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i++) {
            int clr = pixels[i];
            //int alpha = (clr & 0xff000000) >> 24; // 取最高两位
            int red = (clr & 0x00ff0000) >> 16; // 取高两位
            //int green = (clr & 0x0000ff00) >> 8; // 取中两位
           // int blue = clr & 0x000000ff; // 取低两位
            //Log.d("tag", "a="+alpha+",r=" + red + ",g=" + green + ",b=" + blue);
            Log.d("tag",String.format("%08x", clr));
        }
    }
}
