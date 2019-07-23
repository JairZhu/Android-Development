package com.example.isszym.newintentservice;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class MyIntentService extends IntentService {
     public MyIntentService() {
        super("MyIntentService");
    }

    // IntentService会使用单独的线程来执行该方法的代码
    @Override
    protected void onHandleIntent(Intent intent) {
        // 该方法内可以执行任何耗时任务，比如下载文件等，此处只是让线程暂停20秒
        long endTime = System.currentTimeMillis() + 20 * 1000;
        System.out.println("---IntentService onStart---");
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try  {
                    wait(endTime - System.currentTimeMillis());
                }
                catch (Exception e) {
                }
            }
        }
        System.out.println("---IntentService完成任务---");
    }
}
