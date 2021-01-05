package com.example.spacebattle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameSurfaceView(this));
        Intent intent = new Intent();
        intent.setAction("com.MusicPlay");
        intent.putExtra("music", "summer");
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setAction("com.MusicPlay");
        intent.putExtra("music", "stop");
        sendBroadcast(intent);
    }
}
