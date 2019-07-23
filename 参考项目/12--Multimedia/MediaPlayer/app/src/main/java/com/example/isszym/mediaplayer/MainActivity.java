package com.example.isszym.mediaplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import static android.media.MediaPlayer.create;

public class MainActivity extends AppCompatActivity {
    final private int INIT = 0;
    final  private int PLAYING =1;
    final  private int PAUSE =2;
    final  private int  STOP = 3;
    MediaPlayer mp=null;
    int currentRawId=0;
    int state = 0;        // 1-playing,2-pause,3-stop
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPlay = (Button)findViewById(R.id.buttonPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean loop =((CheckBox)findViewById(R.id.checkBox)).isChecked();
                if(mp!=null && (mp.isPlaying() || state==PAUSE)){
                   mp.setLooping(loop);
                   state=PLAYING;
                   if(!mp.isPlaying())mp.start();
                   return;
                }

                try {
                    if(mp!=null) {
                        mp.stop();
                        mp.reset();
                    }
                    mp = create(MainActivity.this, R.raw.waka);
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.setLooping(loop);
                    mp.start();
                    mp.setVolume(14.0f, 14.0f); //声音调不了
                    state=PLAYING;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnPause = (Button)findViewById(R.id.buttonPause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.setLooping(false);
                mp.pause();
                state=PAUSE;
            }
        });

        Button btnStop = (Button)findViewById(R.id.buttonStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.setLooping(false);
                mp.stop();
                state=STOP;
            }
        });
    }
}
