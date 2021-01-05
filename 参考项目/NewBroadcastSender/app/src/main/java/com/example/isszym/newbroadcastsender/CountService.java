package com.example.isszym.newbroadcastsender;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CountService extends Service {
    private int count = 0;
    private boolean threadDisable=false;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
     public void onCreate() {
        super.onCreate();
        threadDisable=false;
        System.out.println("Service is Created");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!threadDisable) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    System.out.println(""+count);
                    Intent intent = new Intent();
                    intent.putExtra("count", count);
                    intent.setAction("com.example.activity.CountService");
                    sendBroadcast(intent);   //发送广播
                }
            }
        }).start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        System.out.println("Service is Started");
        return START_STICKY; //被系统终止后自动重启
    }
    @Override
    public void onDestroy(){ 		// Service被关闭之前回调。
        threadDisable=true;
        super.onDestroy();
    }
}
