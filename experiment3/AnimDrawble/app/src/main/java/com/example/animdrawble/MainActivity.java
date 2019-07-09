package com.example.animdrawble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private ImageView imageView, emptyImage, shapeView;
    private AnimationDrawable sprite, drawable;
    private boolean toRight = true, record = false, play = false, respond = true;
    private ArrayList<Pos> pathList = new ArrayList<>();
    private Pos lastPos;
    private PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.container);
        constraintLayout.setOnTouchListener(this);
        imageView = (ImageView) findViewById(R.id.sprite);
        emptyImage = (ImageView) findViewById(R.id.empty_image);
        shapeView = (ImageView) findViewById(R.id.shape);
        pathView = (PathView) findViewById(R.id.path);
        sprite = (AnimationDrawable) imageView.getBackground();
        drawable = (AnimationDrawable) shapeView.getBackground();
        sprite.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_record:
                record = true;
                shapeView.setVisibility(View.VISIBLE);
                drawable.start();
                pathList.clear();
                pathList.add(new Pos(imageView.getX(), imageView.getY(), toRight));
                break;
            case R.id.stop_record:
                record = false;
                drawable.stop();
                shapeView.setVisibility(View.GONE);
                break;
            case R.id.start_play:
                if (record) break;
                pathView.clearPath();
                play = true;
                lastPos = new Pos(imageView.getX(), imageView.getY(), toRight);
                if (pathList.size() != 0) {
                    Pos firstPos = pathList.get(0);
                    imageView.setX(firstPos.getX());
                    imageView.setY(firstPos.getY());
                    if (toRight != firstPos.isToRight())
                        if (firstPos.isToRight())
                            imageView.setRotationY(0);
                        else
                            imageView.setRotationY(180);
                    toRight = firstPos.isToRight();
                    playRecord(firstPos, 1);
                }
                break;
            case R.id.stop_play:
                pathView.clearPath();
                imageView.clearAnimation();
                imageView.setX(lastPos.getX());
                imageView.setY(lastPos.getY());
                if (toRight != lastPos.isToRight())
                    if (lastPos.isToRight())
                        imageView.setRotationY(0);
                    else
                        imageView.setRotationY(180);
                toRight = lastPos.isToRight();
                play = false;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, final MotionEvent event) {
        if (play || !respond) return true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                respond = false;
                float deX = event.getX() + imageView.getWidth()/2 > emptyImage.getWidth() ? emptyImage.getWidth() - imageView.getWidth()/2 : event.getX();
                float deY = event.getY() + imageView.getHeight()/2 > emptyImage.getHeight() ? emptyImage.getHeight() - imageView.getHeight()/2 : event.getY();
                final float X = deX < imageView.getWidth()/2 ? imageView.getWidth()/2 : deX;
                final float Y = deY < imageView.getHeight()/2 ? imageView.getHeight()/2 : deY;
                final float toX = X - imageView.getX() - imageView.getWidth()/2;
                final float toY = Y - imageView.getY() - imageView.getHeight()/2;
                if (toX < 0 && toRight) {
                    toRight = false;
                    ObjectAnimator rotateY = ObjectAnimator.ofFloat(imageView, "rotationY", 0, 180);
                    rotateY.setDuration(500);
                    rotateY.start();
                }
                else if (toX > 0 && !toRight) {
                    toRight = true;
                    ObjectAnimator rotateY = ObjectAnimator.ofFloat(imageView, "rotationY", 180, 0);
                    rotateY.setDuration(500);
                    rotateY.start();
                }
                TranslateAnimation animation = new TranslateAnimation(0, toX, 0, toY);
                animation.setStartOffset(500);
                double maxLength = Math.sqrt(Math.pow(emptyImage.getHeight() - imageView.getHeight(), 2) + Math.pow(emptyImage.getWidth() - imageView.getWidth(), 2));
                double speed = Math.sqrt(Math.pow(toX, 2) + Math.pow(toY, 2)) / maxLength;
                animation.setDuration(Double.valueOf(5000 * speed).longValue());
                imageView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imageView.setX(X - imageView.getWidth()/2);
                        imageView.setY(Y - imageView.getHeight()/2);
                        if (record) {
                            Pos pos = new Pos(imageView.getX(), imageView.getY(), toRight);
                            pathList.add(pos);
                        }
                        respond = true;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                break;
        }
        return true;
    }

    private void playRecord(Pos firstPos, final int index) {
        if (index >= pathList.size()) {
            return;
        }
        final Pos nextPos = pathList.get(index);
        pathView.setData(firstPos, nextPos, imageView.getWidth(), imageView.getHeight());
        float toX = nextPos.getX() - firstPos.getX();
        float toY = nextPos.getY() - firstPos.getY();
        if (toX < 0 && toRight) {
            toRight = false;
            ObjectAnimator rotateY = ObjectAnimator.ofFloat(imageView, "rotationY", 0, 180);
            rotateY.setDuration(500);
            rotateY.start();
        }
        else if (toX > 0 && !toRight) {
            toRight = true;
            ObjectAnimator rotateY = ObjectAnimator.ofFloat(imageView, "rotationY", 180, 0);
            rotateY.setDuration(500);
            rotateY.start();
        }
        TranslateAnimation animation = new TranslateAnimation(0, toX, 0, toY);
        animation.setStartOffset(500);
        double maxLength = Math.sqrt(Math.pow(emptyImage.getHeight() - imageView.getHeight(), 2) + Math.pow(emptyImage.getWidth() - imageView.getWidth(), 2));
        double speed = Math.sqrt(Math.pow(toX, 2) + Math.pow(toY, 2)) / maxLength;
        animation.setDuration(Double.valueOf(5000 * speed).longValue());
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setX(nextPos.getX());
                imageView.setY(nextPos.getY());
                playRecord(nextPos, index + 1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
