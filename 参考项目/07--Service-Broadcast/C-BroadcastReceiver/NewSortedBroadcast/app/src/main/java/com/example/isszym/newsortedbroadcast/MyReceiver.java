package com.example.isszym.newsortedbroadcast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
public class MyReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent){
		Toast.makeText(context,	intent.getStringExtra("main"), Toast.LENGTH_LONG).show();
		Bundle bundle = new Bundle();
		bundle.putString("first", "哈哈，Receiver 2，你好！");
		setResultExtras(bundle);
		// abortBroadcast(); /* 取消Broadcast的继续传播 */
	}
}
