package com.example.isszym.newbindservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bind, unbind, getServiceStatus;
    BindService.MyBinder binder;               // 保持所启动的Service的IBinder对象

    private ServiceConnection conn = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){  // 当该Activity与Service连接成功时回调该方法
            System.out.println("--Service Connected--");
            binder = (BindService.MyBinder) service; // 获取Service的onBind方法所返回的MyBinder对象
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {  // 当该Activity与Service断开连接时回调该方法
            System.out.println("--Service Disconnected--");
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取程序界面中的start、stop、getServiceStatus按钮
        bind = (Button) findViewById(R.id.bind);
        unbind = (Button) findViewById(R.id.unbind);
        getServiceStatus = (Button) findViewById(R.id.getServiceStatus);
        // 创建启动Service的Intent
        final Intent intent = new Intent(this,BindService.class);
        // 为Intent设置Action属性
        //intent.setAction("org.crazyit.service.BIND_SERVICE");
        bind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                // 绑定指定Serivce
                bindService(intent, conn, Service.BIND_AUTO_CREATE);
            }
        });
        unbind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                // 解除绑定Serivce
                unbindService(conn);
            }
        });
        getServiceStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                // 获取、并显示Service的count值
                Toast.makeText(MainActivity.this,
                        "Serivce的count值为：" + binder.getCount(),
                        Toast.LENGTH_SHORT).show(); //②
            }
        });
    }
}
