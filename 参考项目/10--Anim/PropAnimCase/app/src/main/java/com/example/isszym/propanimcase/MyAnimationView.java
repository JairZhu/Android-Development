package com.example.isszym.propanimcase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Random;

class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {
    //static final float BALL_SIZE = 80F;   // 定义小球的大小的常量
    static final float DROP_TIME = 2000;  // 定义小球从屏幕上方下落到屏幕底端的总时间
    static final int ALHPA_TIME = 2000;  // 定义小球透明度变化的时间
    static final int END_RADIUS = 50;  // 定义小球大小变化
    final int[] colors = {Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW};
    public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();

    public MyAnimationView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
    }

    // 通过ObjectAnimator在一段时间内逐步改变对象的某些属性值
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {  //MotionEvent.ACTION_MOVE
            return false;
        }

        //获得随机的半径（不小于END_RADIUS）和随机的颜色（六种颜色选一种）
        Random rnd = new Random();
        float radius = rnd.nextFloat()/2 * END_RADIUS + END_RADIUS;
        int colorIndex = rnd.nextInt(colors.length);

        float viewHeight = (float) getHeight();                              // 获取控件的高度
        float touchX = event.getX();
        float touchY = event.getY();
        int duration = (int) (DROP_TIME * ((viewHeight - touchY) / viewHeight));

        ShapeHolder ball = new ShapeHolder(touchX-radius, touchY, radius, colors[colorIndex]);
        balls.add(ball);

        // ball的属性y由startY变到endY
        ObjectAnimator fallAnim = ObjectAnimator.ofFloat(ball, "y", touchY, viewHeight - END_RADIUS*2);
        fallAnim.setDuration(duration);                             // 设置下落动画持续时间
        fallAnim.setInterpolator(new AccelerateInterpolator());     // 采用加速插值
        fallAnim.addUpdateListener(this);                           // 监听属性值改变事件(每10秒一次)

        // ball的属性radius由radius变到END_RADIUS
        ObjectAnimator radiusAnim = ObjectAnimator.ofFloat(ball, "radius", radius, END_RADIUS);
        radiusAnim.setDuration(duration);                           // 设置渐隐动画持续时间
        radiusAnim.setInterpolator(new LinearInterpolator());      // 动画采用线性插值
        //radiusAnim.addUpdateListener(this);                      // 监听属性值改变事件

        // ball的属性alpha由255变到0
        ObjectAnimator fadeAnim = ObjectAnimator.ofInt(ball, "alpha", 255, 0);
        fadeAnim.setDuration(ALHPA_TIME);                      // 设置渐隐动画持续时间
        fadeAnim.setInterpolator(new LinearInterpolator());     // 动画采用线性插值
        fadeAnim.addUpdateListener(this);                       // 监听属性值改变事件
        fadeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                balls.remove(((ObjectAnimator) animation).getTarget()); // 动画结束时删除ShapeHolder
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();                // 用AnimatorSet来组合
        animatorSet.play(fallAnim)
                .with(radiusAnim)
                .before(fadeAnim);    // 先播完fallAnim和radiusAnim再播fadeAnim
        animatorSet.start();          // 播放动画
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        for (ShapeHolder ball : balls) {
            canvas.save();         // 保存canvas的当前坐标系统
            OvalShape circle = new OvalShape();                         // 创建一个椭圆
            circle.resize(ball.getRadius()*2, ball.getRadius()*2);         // 设置该椭圆的宽、高
            ShapeDrawable drawable = new ShapeDrawable(circle);         // 将椭圆包装成Drawable对象
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    ball.getRadius()*2, ball.getColor(), 0xff4E4E4E & ball.getColor(), Shader.TileMode.CLAMP);   // 创建圆形渐变
            drawable.getPaint().setShader(gradient);
            drawable.getPaint().setAlpha(ball.getAlpha());
            canvas.translate(ball.getX(), ball.getY());
            drawable.draw(canvas); // 在Canvas上圆形绘制
            canvas.restore();      // 恢复Canvas坐标系统
        }
    }

    @Override  //属性值改变事件
    public void onAnimationUpdate(ValueAnimator animation) {
        this.invalidate();  // 产生重绘消息
    }
}
