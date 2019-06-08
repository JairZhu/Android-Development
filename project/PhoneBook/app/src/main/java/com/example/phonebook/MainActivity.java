package com.example.phonebook;

import android.Manifest;
import android.support.v7.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // status(通话状态): 0-呼入，1-呼出，2-未接听
    Uri callRecordUri = Uri.parse("content://com.example.providers.recordDB/");
    int[] images = {R.drawable.callin, R.drawable.callout, R.drawable.missed};
    ContentResolver resolver;
    ActionBar actionBar;
    AlertDialog alertDialog;
    ListView record_listview;
    SimpleAdapter adapter = null;
    FloatingActionButton DialpadActionButton, AddContactButton;
    RelativeLayout DialpadLayout, DialLayout, Contact_layout;
    ArrayList<Map<String, Object>> record_list = new ArrayList<>();
    TextView textView;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bstar, bdash;
    ImageButton iCall, iBack, iDialpad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getApplicationContext().deleteDatabase("records");
        initialNavigation();
        initialFloatingActionButton();
        DialpadsetOnClickListeners();
        resolver = getContentResolver();
        DialLayout = (RelativeLayout) findViewById(R.id.dial_layout);
        Contact_layout = (RelativeLayout) findViewById(R.id.contact_layout);
        record_listview = (ListView) DialLayout.findViewById(R.id.dial_listview);
        actionBar = (ActionBar) getSupportActionBar();
        actionBar.setTitle("拨号");
        Contact_layout.setVisibility(View.GONE);
        diplayCallRecord();
    }

    private void DialpadsetOnClickListeners() {
        DialpadLayout = findViewById(R.id.dialpad_layout);
        textView = DialpadLayout.findViewById(R.id.textView);
        b1 = DialpadLayout.findViewById(R.id.b1);
        b2 = DialpadLayout.findViewById(R.id.b2);
        b3 = DialpadLayout.findViewById(R.id.b3);
        b4 = DialpadLayout.findViewById(R.id.b4);
        b5 = DialpadLayout.findViewById(R.id.b5);
        b6 = DialpadLayout.findViewById(R.id.b6);
        b7 = DialpadLayout.findViewById(R.id.b7);
        b8 = DialpadLayout.findViewById(R.id.b8);
        b9 = DialpadLayout.findViewById(R.id.b9);
        b0 = DialpadLayout.findViewById(R.id.b0);
        bstar = DialpadLayout.findViewById(R.id.bstar);
        bdash = DialpadLayout.findViewById(R.id.bdash);
        iCall = DialpadLayout.findViewById(R.id.callButton);
        iBack = DialpadLayout.findViewById(R.id.backspaceButton);
        iDialpad = DialpadLayout.findViewById(R.id.dialpadButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '1';
                textView.setText(newText);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '2';
                textView.setText(newText);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '3';
                textView.setText(newText);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '4';
                textView.setText(newText);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '5';
                textView.setText(newText);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '6';
                textView.setText(newText);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '7';
                textView.setText(newText);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '8';
                textView.setText(newText);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '9';
                textView.setText(newText);
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '0';
                textView.setText(newText);
            }
        });
        bstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '*';
                textView.setText(newText);
            }
        });
        bdash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString() + '#';
                textView.setText(newText);
            }
        });
        iBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = textView.getText().toString();
                if (newText.length() > 0) {
                    newText = newText.substring(0, newText.length() - 1);
                }
                textView.setText(newText);
            }
        });
        iCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newText = textView.getText().toString();
                if (newText.length() > 0) {
                    makePhoneCall(newText);
                }
            }
        });
        iDialpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialpadLayout.setVisibility(View.GONE);
                DialpadActionButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addNewCallRecord(String phoneNumber) {
        //TODO:实现添加记录功能
        ContentValues contentValues = new ContentValues();
        Cursor cursor = resolver.query(callRecordUri, new String[]{"id"}, null, null, "id desc");
        int index;
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            index = cursor.getInt(0);
        } else
            index = 0;
        contentValues.put("id", index + 1);
        contentValues.put("number", phoneNumber);
        contentValues.put("status", 1);
        //TODO:实现查找号码对应的联系人姓名、归属地
        //contentValues.put("name", );
        //contentValues.put("attribution",);
        //contentValues.put("duration", 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        contentValues.put("calltime", currentTime);
        resolver.insert(callRecordUri, contentValues);
        cursor = resolver.query(callRecordUri, new String[]{"number", "name",
                        "attribution", "calltime", "status", "duration"},
                null, null, null);
        changeRecordList(cursor);
        adapter.notifyDataSetChanged();
    }

    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
        addNewCallRecord(phoneNumber);
    }

    private void initialFloatingActionButton() {
        DialpadActionButton = findViewById(R.id.dial_number);
        DialpadActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialpadLayout.setVisibility(View.VISIBLE);
                DialpadActionButton.setVisibility(View.GONE);
            }
        });
        DialpadActionButton.setVisibility(View.GONE);
        AddContactButton = findViewById(R.id.add_new_contact);
        AddContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"添加联系人", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initialNavigation() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dial:
                        actionBar.setTitle("拨号");
                        DialLayout.setVisibility(View.VISIBLE);
                        Contact_layout.setVisibility(View.GONE);
                        return true;
                    case R.id.contact:
                        actionBar.setTitle("联系人");
                        DialLayout.setVisibility(View.GONE);
                        Contact_layout.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
    }

    private void changeRecordList(Cursor cursor) {
        record_list.clear();
        while (cursor != null && cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            map.put("number", cursor.getString(cursor.getColumnIndex("number")));
            map.put("name", cursor.getString(cursor.getColumnIndex("name")));
            map.put("attribution", cursor.getString(cursor.getColumnIndex("attribution")));
            map.put("calltime", cursor.getString(cursor.getColumnIndex("calltime")));
            map.put("duration", cursor.getString(cursor.getColumnIndex("duration")));
            map.put("status", images[cursor.getInt(cursor.getColumnIndex("status"))]);
            record_list.add(map);
        }
    }

    private void deleteCallRecord(int position) {
        resolver.delete(callRecordUri, "calltime = ? and number = ?",
                new String[]{record_list.get(position).get("calltime").toString(),
                        record_list.get(position).get("number").toString()});
        record_list.remove(position);
        adapter.notifyDataSetChanged();
    }

    private void diplayCallRecord() {
        Cursor cursor = resolver.query(callRecordUri, new String[]{"number", "name", "attribution",
                "calltime", "duration", "status"}, null, null, null);
        changeRecordList(cursor);
        adapter = new SimpleAdapter(this, record_list, R.layout.dial_listview_item,
                new String[]{"number", "name", "attribution", "calltime", "status", "duration"},
                new int[]{R.id.phone_number, R.id.person_name, R.id.attribution, R.id.time, R.id.call_status, R.id.duration});
        record_listview.setAdapter(adapter);
        record_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone_number = record_list.get(i).get("number").toString();
                makePhoneCall(phone_number);
            }
        });
        record_listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DialpadActionButton.setVisibility(View.VISIBLE);
                DialpadLayout.setVisibility(View.GONE);
                return false;
            }
        });
        record_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                if (record_list.get(i).get("name") == null || record_list.get(i).get("name").toString().isEmpty()) {
                    popupMenu.getMenuInflater().inflate(R.menu.record_long_click_menu1, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.add_white_list:
                                    //TODO:将该号码加入白名单
                                    break;
                                case R.id.delete_record:
                                    deleteCallRecord(i);
                                    break;
                                case R.id.add_new_contact:
                                    //TODO:新增联系人
                                    break;
                                case R.id.store_contact:
                                    //TODO:保存至已有联系人
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
                                    //TODO:加入白名单
                                    break;
                                case R.id.delete_record:
                                    deleteCallRecord(i);
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
