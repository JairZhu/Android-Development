package com.example.phonebook;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;

import java.util.ArrayList;

public class ContactInfoActivity extends AppCompatActivity {
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"详情", "通话记录"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String name;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.contact_info_layout);
        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getBundleExtra("key");
        name = bundle.getString("name");
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
                //分享联系人（二维码）
                Cursor cursor = getContentResolver().query(contactUri, new String[]{"number", "name", "birthday", "attribution", "pinyin"},
                        "name=?", new String[]{name}, null);
                String content = "";
                while (cursor.moveToNext()) {
                    String nowname = cursor.getString(cursor.getColumnIndex("name"));
                    String nowbirthday = cursor.getString(cursor.getColumnIndex("birthday"));
                    String nownumber = cursor.getString(cursor.getColumnIndex("number"));
                    String nowattribution = cursor.getString(cursor.getColumnIndex("attribution"));
                    String newpinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    content = content + nowname + "," + nownumber + "," + nowbirthday + "," + nowattribution + "," + newpinyin + '\n';
                }
                try {
                    Bitmap bitmap = BitmapUtils.create2DCode(content);
                    RelativeLayout form = new RelativeLayout(this);
                    ImageView imageView = new ImageView(this);
                    imageView.setImageBitmap(bitmap);
                    form.setGravity(Gravity.CENTER);
                    form.addView(imageView);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    AlertDialog alertDialog = builder.setTitle("分享联系人")
                            .setView(form)
                            .setPositiveButton("确定", null).create();
                    alertDialog.show();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }
}
