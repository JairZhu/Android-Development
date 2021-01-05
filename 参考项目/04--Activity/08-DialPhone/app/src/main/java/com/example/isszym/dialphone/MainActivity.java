package com.example.isszym.dialphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Intent.ACTION_DIAL="android.intent.action.DIAL"
    public void clickHandler1(View source) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
    }
    public void clickHandler2(View source) {
        Intent intent = new Intent(Intent.ACTION_DIAL); // "android.intent.action.DIAL"
        Uri data = Uri.parse("tel:" + "13600015411");
        intent.setData(data);
        startActivity(intent);
    }
}
