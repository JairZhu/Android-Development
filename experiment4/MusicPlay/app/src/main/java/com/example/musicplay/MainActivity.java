package com.example.musicplay;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction("com.MusicPlay");
                switch (position) {
                    case 0:
                        intent.putExtra("music", "stop");
                        break;
                    case 1:
                        intent.putExtra("music", "waka");
                        break;
                    case 2:
                        intent.putExtra("music", "summer");
                        break;
                    case 3:
                        intent.putExtra("music", "wish");
                        break;
                }
                sendBroadcast(intent);
                Log.v("mainactivity", "broadcast " + intent.getExtras().getString("music"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
