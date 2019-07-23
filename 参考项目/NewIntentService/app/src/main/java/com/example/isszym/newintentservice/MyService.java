package com.example.isszym.newintentservice;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)  {
        // 该方法内执行耗时任务可能导致ANR（Application Not Responding）异常
        long endTime = System.currentTimeMillis() + 20 * 1000;
        System.out.println("---Service onStart---");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                }
                catch (Exception e) {
                }
            }
        }
        System.out.println("---Service完成任务---");
        return START_STICKY;
    }
   }
