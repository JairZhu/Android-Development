package com.example.musicplay;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class Service extends IntentService {
    private static MediaPlayer mediaPlayer;

    public Service() {
        super("Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("service", "start serve");
        String musicName = intent.getExtras().getString("music");
        Log.v("music", musicName);
        switch (musicName) {
            case "stop":
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                break;
            case "waka":
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(Service.this, R.raw.waka);
                mediaPlayer.start();
                break;
            case "summer":
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(Service.this, R.raw.summer);
                mediaPlayer.start();
                break;
            case "wish":
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = MediaPlayer.create(Service.this, R.raw.wish);
                mediaPlayer.start();
                break;
        }
    }
}
