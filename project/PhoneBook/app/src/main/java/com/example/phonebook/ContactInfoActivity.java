package com.example.phonebook;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ContactInfoActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"详情", "通话记录"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.contact_info_layout);
        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getBundleExtra("key");
        String name = bundle.getString("name");
        actionBar.setTitle(name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpaper);
        ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
        contactInfoFragment.setName(name);
        fragments.add(contactInfoFragment);
        ContactInfoRecord contactInfoRecord = new ContactInfoRecord();
        contactInfoRecord.setName(name);
        fragments.add(contactInfoRecord);
        ContactFragmentPagerAdapter adapter = new ContactFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.qr_code_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.qr_code:
                //TODO:分享联系人（二维码）
                break;
        }
        return true;
    }
}
