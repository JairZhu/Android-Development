package com.example.isszym.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FirstService extends Service {
    public FirstService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("Service is bound");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Service被创建时回调该方法。
    @Override
    public void onCreate()
    {
        super.onCreate();
        System.out.println("Service is Created");
    }
    // Service被启动时回调该方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        System.out.println("Service is Started:"+intent.getStringExtra("Test"));
        return START_STICKY;
    }
    // Service被关闭之前回调。
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.out.println("Service is Destroyed");
    }
}
