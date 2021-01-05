package com.example.isszym.newbindservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;

public class BindService extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder = new MyBinder();   // 定义onBinder方法所返回的对象
    public BindService() { }
    @Override
    public IBinder onBind(Intent intent) {       // 必须实现的方法，绑定该Service时回调该方法
        System.out.println("Service is Binded");
        return binder;
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    public class MyBinder extends Binder {  // 通过继承Binder来实现IBinder类
        public int getCount() {              // 获取Service的运行状态：count
            return count;
        }
    }
    @Override
    public void onCreate(){
        super.onCreate();
        System.out.println("Service is Created");
        // 启动一条线程、动态地修改count状态值
        new Thread() {
            @Override
            public void run() {
                while (!quit) {
                    try  {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                    }
                    count++;
                }
            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent){   // Service被断开连接时回调该方法
        System.out.println("Service is Unbinded");
        return true;
    }
    @Override
    public void onDestroy(){          // Service被关闭之前回调该方法。
        super.onDestroy();
        this.quit = true;
        System.out.println("Service is Destroyed");
    }
}
