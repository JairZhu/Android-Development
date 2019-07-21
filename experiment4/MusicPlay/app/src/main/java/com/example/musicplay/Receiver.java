package com.example.musicplay;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String musicName = null;
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            musicName = "summer";
        }
        else if (intent.getAction().equals("com.MusicPlay")) {
            musicName = intent.getExtras().getString("music");
        }
        Log.v("receiver", "received " + musicName);
        Intent intent1 = new Intent(context, Service.class);
        intent1.putExtra("music", musicName);
        context.startService(intent1);
    }
}
