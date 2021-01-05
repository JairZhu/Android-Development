package com.example.isszym.newpreferenceactivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class PreferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preference);
        Bundle bundle;
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        SettingFragment fg = new SettingFragment();
        bundle = new Bundle();
        bundle.putString("data", "第一个Fragment");
        fg.setArguments(bundle);
        fTransaction.add(R.id.ly_content, fg);
        fTransaction.commit();
    }
}
