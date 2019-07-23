package com.example.isszym.newnettraffic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class WidgetMainActivity extends Activity {
	// 一个有只有一个按钮的activity

	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_imitate_widget_main);
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				funClick();
			}
		});
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//			if (!Settings.canDrawOverlays(getApplicationContext())) {
//				//启动Activity让用户授权
//				Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//				intent.setData(Uri.parse("package:" + getPackageName()));
//				startActivityForResult(intent, 100);
//			}
//		}
	}

	public void funClick() {
		// 按钮被点击
		this.startService(new Intent(getApplicationContext(), MserServes.class));
		// new TableShowView(this).fun(); 如果只是在activity中启动
		// 当activity跑去后台的时候[暂停态，或者销毁态] 我们设置的显示到桌面的view也会消失
		// 所以这里采用的是启动一个服务，服务中创建我们需要显示到table上的view，并将其注册到windowManager上
		this.finish();
	}
	/*	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == 100){
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (Settings.canDrawOverlays(this)) {
						WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
						WindowManager.LayoutParams params = new WindowManager.LayoutParams();
						params.type = WindowManager.LayoutParams.TYPE_PHONE;
						params.format = PixelFormat.RGBA_8888;
						windowManager.addView(view,params);
					}else {
						Toast.makeText(this,"ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝",Toast.LENGTH_SHORT).show();;
					}
				}

			}
		}
*/
}