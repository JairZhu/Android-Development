package com.example.isszym.ratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RatingBar ratingBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar1.setRating(4);
        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            // 该方法可以获取到 3个参数
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean paramBoolean) {
                // 第三个参数 如果评分改变是由用户触摸手势或方向键轨迹球移动触发的，则返回true
                Toast.makeText(MainActivity.this,""+ratingBar1.getRating(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
