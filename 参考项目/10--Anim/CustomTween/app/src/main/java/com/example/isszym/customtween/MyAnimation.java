package com.example.isszym.customtween;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class MyAnimation extends Animation
{
	private float centerX;
	private float centerY;
	// 定义动画的持续事件
	private int duration;
	private Camera camera = new Camera();

	public MyAnimation(float x, float y, int duration){
		this.centerX = x;
		this.centerY = y;
		this.duration = duration;
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight){
		super.initialize(width, height, parentWidth, parentHeight);
		setDuration(duration);
		setFillAfter(true);   // 动画结束后保留最后的图像
		setInterpolator(new LinearInterpolator());
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t){
		/* interpolatedTime代表了抽象的动画持续时间，取值0（动画开始时）～1（动画结束时）
		*  Transformation表示对目标组件所做的变化.	*/
		camera.save();

		camera.translate(100.0f - 100.0f * interpolatedTime,  // 控制X、Y、Z上的偏移
				150.0f * interpolatedTime - 150,
				80.0f - 80.0f * interpolatedTime);
		// 设置根据interpolatedTime时间在Y柚上旋转不同角度。
		camera.rotateY(360 * interpolatedTime);
		// 设置根据interpolatedTime时间在X柚上旋转不同角度
		camera.rotateX(360 * interpolatedTime);
		// 获取Transformation参数的Matrix对象
		Matrix matrix = t.getMatrix();
		camera.getMatrix(matrix);
		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
		camera.restore();
	}
}