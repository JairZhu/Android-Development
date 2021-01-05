package com.example.isszym.quickcontactbadge;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.QuickContactBadge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuickContactBadge quickContactBadge;
        quickContactBadge = (QuickContactBadge) findViewById(R.id.quickContactBadge);
        quickContactBadge.assignContactFromPhone("13611112222", true);
        quickContactBadge.setMode(ContactsContract.QuickContact.MODE_SMALL);

    }
}
