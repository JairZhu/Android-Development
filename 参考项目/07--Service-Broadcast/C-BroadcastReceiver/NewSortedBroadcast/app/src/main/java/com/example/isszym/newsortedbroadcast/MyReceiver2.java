package com.example.isszym.newsortedbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent)	{
		Bundle bundle = getResultExtras(true);//true:没数据则创建空Bundle，false:没数据则返回null
		String first = bundle.getString("first"); // 解析前一个BroadcastReceiver所存入的key为first的消息
		Toast.makeText(context, first, Toast.LENGTH_LONG).show();
        Toast.makeText(context, intent.getStringExtra("main"), Toast.LENGTH_LONG).show();
	}
}
