package com.example.isszym.fragmenttest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        MyFragment fg1 = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", "第一个Fragment");
        fg1.setArguments(bundle);
        fTransaction.add(R.id.content1,fg1);

        MyFragment fg2 = new MyFragment();
        bundle = new Bundle();
        bundle.putString("data", "第二个Fragment");
        fg2.setArguments(bundle);
        fTransaction.add(R.id.content2,fg2);
        fTransaction.commit();
    }
}
