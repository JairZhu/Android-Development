package com.example.phonebook;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;


public class Observer extends ContentObserver {
    private Context context;
    private Handler handler;
    private long lastTime = 0, thisTime = 0;
    public Observer(Context context, Handler handler) {
        super(handler);
        this.context = context;
        this.handler = handler;
    }
    @Override
    public void onChange(boolean selfChange) {
        thisTime = System.currentTimeMillis();
        if (thisTime - lastTime > 10000)
            handler.sendEmptyMessage(0);
        lastTime = System.currentTimeMillis();
    }
    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }
}
