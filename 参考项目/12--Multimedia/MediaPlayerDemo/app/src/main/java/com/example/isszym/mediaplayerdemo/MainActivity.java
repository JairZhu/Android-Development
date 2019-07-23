package com.example.isszym.mediaplayerdemo;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.media.MediaPlayer.*;

public class MainActivity extends AppCompatActivity {
    private Button bplay, bplay2,bpause, bstop;
    private MediaPlayer mp = new MediaPlayer();
    private MediaPlayer mp2 = new MediaPlayer();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bplay = (Button) findViewById(R.id.play);
        bplay2 = (Button) findViewById(R.id.play2);
        bpause = (Button) findViewById(R.id.pause);
        bstop = (Button) findViewById(R.id.stop);
        bplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRawMusic(R.raw.a1);
                //openAssetMusic("waka.mp3");
                mp.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            }
        });
        bplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openRawMusic(R.raw.a1);
                openAssetMusic("waka.mp3");
                mp.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
            }
        });

        bpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null) {
                    mp.pause();
                }
            }
        });

        bstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp != null) {
                    mp.stop();
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onDestroy() {
        if (mp != null)
            mp.release();
        super.onDestroy();
    }

    private void openRawMusic(int rawId){
        try {
            mp.reset();
            mp = create(MainActivity.this, rawId);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setVolume(12f, 12f); //声音调不了
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAssetMusic(String fileName) {
        //打开Asset目录
        AssetManager assetManager = getAssets();

        try {
            //打开音乐文件shot.mp3
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(fileName);
            mp2.reset();
            //设置媒体播放器的数据资源
            mp2.setDataSource(assetFileDescriptor.getFileDescriptor(),
                             assetFileDescriptor.getStartOffset(),
                             assetFileDescriptor.getLength());
            mp2.prepare();
            mp2.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("GsonUtils", "IOException" + e.toString());
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

  /*  *//**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     *//*
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }*/
}