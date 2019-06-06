package com.example.phonebook;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Uri callrecorduri = Uri.parse("content://com.example.providers.recordDB/");
    int[] images = {R.drawable.callin, R.drawable.callout, R.drawable.missed};
    ContentResolver resolver;
    AlertDialog alertDialog;
    ListView record_listview;
    SimpleAdapter adapter = null;
    ArrayList<Map<String, Object>> record_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getApplicationContext().deleteDatabase("records");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dial:
                        return true;
                    case R.id.contact:
                        return true;
                }
                return false;
            }
        });
        FloatingActionButton floatingActionButton = findViewById(R.id.dial_number);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        resolver = getContentResolver();
        record_listview = (ListView) findViewById(R.id.dial_listview);
        diplay_call_record();
    }

    public void add_new_call_record() {
        TableLayout record_form = (TableLayout) getLayoutInflater().inflate(R.layout.new_record, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        alertDialog = builder.setTitle("新增通话记录")
                .setView(record_form)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "取消新增通话记录", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText newname = (EditText) alertDialog.findViewById(R.id.new_name);
                        EditText newnumber = (EditText) alertDialog.findViewById(R.id.new_number);
                        EditText newattribution = (EditText) alertDialog.findViewById(R.id.new_attribution);
                        EditText newtime = (EditText) alertDialog.findViewById(R.id.new_time);
                        EditText newduration = (EditText) alertDialog.findViewById(R.id.new_duration);
                        RadioGroup radioGroup = (RadioGroup) alertDialog.findViewById(R.id.radio_group);
                        Cursor cursor = resolver.query(callrecorduri, new String[]{"id"}, null, null, "id desc");
                        int index;
                        if (cursor != null && cursor.getCount() != 0) {
                            cursor.moveToFirst();
                            index = cursor.getInt(0);
                        } else
                            index = 0;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", index + 1);
                        contentValues.put("number", newnumber.getText().toString());
                        contentValues.put("name", newname.getText().toString());
                        contentValues.put("attribution", newattribution.getText().toString());
                        contentValues.put("calltime", newtime.getText().toString());
                        contentValues.put("whitelist", 0);
                        contentValues.put("duration", newduration.getText().toString());
                        for (int j = 0; j < radioGroup.getChildCount(); ++j) {
                            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(j);
                            if (radioButton.isChecked()) {
                                contentValues.put("status", j);
                            }
                        }
                        resolver.insert(callrecorduri, contentValues);
                        cursor = resolver.query(callrecorduri, new String[]{"number", "name",
                                        "attribution", "calltime", "status", "duration", "whitelist"},
                                null, null, null);
                        change_record_list(cursor);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        alertDialog.show();
    }

    public void change_record_list(Cursor cursor) {
        record_list.clear();
        while (cursor != null && cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            map.put("number", cursor.getString(cursor.getColumnIndex("number")));
            map.put("name", cursor.getString(cursor.getColumnIndex("name")));
            map.put("attribution", cursor.getString(cursor.getColumnIndex("attribution")));
            map.put("calltime", cursor.getString(cursor.getColumnIndex("calltime")));
            map.put("duration", cursor.getString(cursor.getColumnIndex("duration")));
            map.put("status", images[cursor.getInt(cursor.getColumnIndex("status"))]);
            map.put("whitelist", cursor.getInt(cursor.getColumnIndex("whitelist")));
            record_list.add(map);
        }
    }

    public void diplay_call_record() {
        Cursor cursor = resolver.query(callrecorduri, new String[]{"number", "name", "attribution",
                "calltime", "duration", "status", "whitelist"}, null, null, null);
        change_record_list(cursor);
        adapter = new SimpleAdapter(this, record_list, R.layout.dial_listview_item,
                new String[]{"number", "name", "attribution", "calltime", "status", "duration"},
                new int[]{R.id.phone_number, R.id.person_name, R.id.attribution, R.id.time, R.id.call_status, R.id.duration});
        record_listview.setAdapter(adapter);
        record_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone_number = record_list.get(i).get("number").toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_number));
                startActivity(intent);
            }
        });
        record_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                if (record_list.get(i).get("name").toString().isEmpty() || record_list.get(i).get("name") == null) {
                    popupMenu.getMenuInflater().inflate(R.menu.record_long_click_menu1, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.add_white_list:
                                    break;
                                case R.id.delete_record:
                                    break;
                                case R.id.add_new_contact:
                                    break;
                                case R.id.store_contact:
                                    break;
                            }
                            return true;
                        }
                    });
                } else {
                    popupMenu.getMenuInflater().inflate(R.menu.record_long_click_menu2, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.add_white_list:
                                    break;
                                case R.id.delete_record:
                                    break;
                            }
                            return true;
                        }
                    });
                }
                popupMenu.show();
                return true;
            }
        });
    }
}
