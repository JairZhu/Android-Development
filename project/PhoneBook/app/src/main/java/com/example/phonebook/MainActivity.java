package com.example.phonebook;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // status(通话状态): 0-呼入，1-呼出，2-未接听
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private int[] images = {R.drawable.callin, R.drawable.callout, R.drawable.missed};
    private boolean reminder = true;
    private int BeginTime, EndTime;
    private ContentResolver resolver;
    private ActionBar actionBar;
    private ListView record_listview;
    private CustomPhoneStateListener myCall;
    private RecyclerView contacts_view;
    private SimpleAdapter adapter = null;
    private ContactsAdapter contactsAdapter;
    private FloatingActionButton DialpadActionButton, AddContactButton;
    private RelativeLayout DialpadLayout;
    private ArrayList<Map<String, Object>> record_list = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();
    private WaveSideBar sideBar;
    private TextView textView;
    private MakePhoneCall makePhoneCall;
    private SearchView searchView;
    private Receiver receiver;
    private Comparator<Contact> comparator = new Comparator<Contact>() {
        @Override
        public int compare(Contact contact, Contact t1) {
            if (contact.getIndex().equals("#") && !t1.getIndex().equals("#"))
                return 1;
            else if (t1.getIndex().equals("#") && !contact.getIndex().equals("#"))
                return -1;
            else if (contact.getIndex().compareTo(t1.getIndex()) < 0)
                return -1;
            else if (contact.getIndex().compareTo(t1.getIndex()) == 0
                    && contact.getPinyin().compareTo(t1.getPinyin()) < 0)
                return -1;
            else
                return 1;
        }
    };
    private Comparator<Map<String, Object>> recordComparator = new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> stringObjectMap, Map<String, Object> t1) {
            if (stringObjectMap.get("calltime").toString().compareTo(t1.get("calltime").toString()) < 0)
                return 1;
            else
                return -1;
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 0) {
                updateRecordListView();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        makePhoneCall = new MakePhoneCall(this, resolver);
//        getApplicationContext().deleteDatabase("contacts");
//        getApplicationContext().deleteDatabase("records");
        initialNavigation();
        initialFloatingActionButton();
        DialpadsetOnClickListeners();
        initialContactView();
        actionBar = (ActionBar) getSupportActionBar();
        actionBar.setTitle("拨号");
        setContactViewVisible(View.GONE);
        displayCallRecord();
        getPermission();
        giveTips();
        listener();
        setUpReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onNewIntent(Intent intent) {
        //Get search query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            updateContactListView(searchContect(query));
        }
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.search_button).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                updateContactListView(searchContect(s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                updateContactListView(searchContect(s));
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                onSearchRequested();
                break;
            case R.id.add_qr_contact:
                //扫描二维码添加联系人
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                break;
            case R.id.no_disturb:
                if (item.isChecked()) {
                    //关闭免打扰
                    item.setChecked(false);
                    myCall.setChecked(0);
                } else {
                    //开启免打扰
                    item.setChecked(true);
                    setUpNoDisturb(item);
                }
                break;
            case R.id.remind:
                if (item.isChecked()) {
                    //关闭温馨提示
                    item.setChecked(false);
                    reminder = false;
                } else {
                    //开启温馨提示
                    item.setChecked(true);
                    reminder = true;
                    giveTips();
                }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            String[] contactInfo = result.split("\n");
            for (int i = 0; i < contactInfo.length; ++i) {
                String[] information = contactInfo[i].split(",");
                if (information.length != 5) {
                    Toast.makeText(this, "无法识别", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = information[0], number = information[1], birthday = information[2],
                        attribution = information[3], pinyin = information[4];
                Cursor cursor = resolver.query(contactUri, new String[]{"number"}, "number = ?",
                        new String[]{number}, null);
                if (cursor != null && cursor.getCount() != 0)
                    continue;
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("number", number);
                values.put("birthday", birthday);
                values.put("attribution", attribution);
                values.put("pinyin", pinyin);
                resolver.insert(contactUri, values);
                values.clear();
                values.put("name", name);
                resolver.update(callRecordUri, values, "number = ?", new String[]{number});
                updateContactListView();
                updateRecordListView();
            }
            Toast.makeText(this, "联系人添加成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateContactListView();
        updateRecordListView();
    }

    private void setUpReceiver() {
        receiver = new Receiver(handler);
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        registerReceiver(receiver, filter);
    }

    private void getPermission() {
        ArrayList<String> permissionList = new ArrayList<String>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CALL_LOG);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.VIBRATE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NOTIFICATION_POLICY)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_NOTIFICATION_POLICY);
        }
        if (!permissionList.isEmpty())
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1000);
    }

    private void setDialViewVisible(int visible) {
        record_listview.setVisibility(visible);
        DialpadLayout.setVisibility(visible);
        DialpadActionButton.setVisibility(View.GONE);
    }

    private void setContactViewVisible(int visible) {
        contacts_view.setVisibility(visible);
        sideBar.setVisibility(visible);
        AddContactButton.setVisibility(visible);
    }

    private void updateContactListView() {
        contactList.clear();
        Cursor cursor = resolver.query(contactUri, new String[]{"distinct name"},
                null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contactList.add(contact);
        }
        Collections.sort(contactList, comparator);
        contactsAdapter.notifyDataSetChanged();
    }

    private void updateContactListView(List<Contact> newList) {
        contactList.clear();
        contactList.addAll(newList);
        Collections.sort(contactList, comparator);
        contactsAdapter.notifyDataSetChanged();
    }

    private void initialContactView() {
        Cursor cursor = resolver.query(contactUri, new String[]{"distinct name"},
                null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contactList.add(contact);
        }
        cursor.close();
        Collections.sort(contactList, comparator);
        contacts_view = (RecyclerView) findViewById(R.id.contact_recyclerview);
        contacts_view.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new ContactsAdapter(this, contactList, R.layout.contact_listview_item);
        contacts_view.setAdapter(contactsAdapter);
        sideBar = (WaveSideBar) findViewById(R.id.side_bar);

        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < contactList.size(); ++i) {
                    if (contactList.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) contacts_view.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void DialpadsetOnClickListeners() {
        Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bstar, bdash;
        ImageButton iCall, iBack, iDialpad;
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
                    makePhoneCall.makeCall(newText);
                    updateRecordListView();
                    DialpadLayout.setVisibility(View.GONE);
                    DialpadActionButton.setVisibility(View.VISIBLE);
                } else {
                    if (record_list.size() > 0) {
                        String number = record_list.get(0).get("number").toString();
                        textView.setText(number);
                    }
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
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra("number", "");
                startActivity(intent);
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
                        setDialViewVisible(View.VISIBLE);
                        setContactViewVisible(View.GONE);
                        return true;
                    case R.id.contact:
                        actionBar.setTitle("联系人");
                        setDialViewVisible(View.GONE);
                        setContactViewVisible(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
    }

    private void updateRecordListView() {
        record_list.clear();
        Cursor cursor = resolver.query(callRecordUri, new String[]{"number", "name", "attribution",
                "calltime", "duration", "status"}, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if (name == null || name.isEmpty())
                map.put("name", cursor.getString(cursor.getColumnIndex("number")));
            else
                map.put("name", name);
            map.put("number", cursor.getString(cursor.getColumnIndex("number")));
            map.put("attribution", cursor.getString(cursor.getColumnIndex("attribution")));
            map.put("calltime", cursor.getString(cursor.getColumnIndex("calltime")));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            String duration = cursor.getString(cursor.getColumnIndex("duration"));
            if (status == 1 && duration.equals("0"))
                duration = "未接通";
            else if (status == 0 && duration.equals("0")) {
                duration = "拒接";
                status = 2;
            }
            else if (status == 2 && duration.equals("0"))
                duration = "未接";
            else
                duration = duration + "秒";
            map.put("duration", duration);
            map.put("status", images[status]);
            record_list.add(map);
        }
        cursor.close();
        Collections.sort(record_list, recordComparator);
        adapter.notifyDataSetChanged();
    }

    private void deleteCallRecord(final int position) {
        //删除通话记录
        RelativeLayout form = new RelativeLayout(this);
        TextView textView = new TextView(this);
        textView.setText("是否删除此通话记录？");
        textView.setPadding(0, 20, 0, 0);
        textView.setTextSize(18);
        form.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        form.addView(textView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.setIcon(R.drawable.ic_warning_green_24dp)
                .setTitle("警告")
                .setView(form)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        resolver.delete(callRecordUri, "calltime = ? and number = ?",
                                new String[]{record_list.get(position).get("calltime").toString(),
                                        record_list.get(position).get("number").toString()});
                        record_list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        alertDialog.show();
    }

    private void displayCallRecord() {
        record_listview = (ListView) findViewById(R.id.dial_listview);
        Cursor cursor = resolver.query(callRecordUri, new String[]{"number", "name", "attribution",
                "calltime", "duration", "status"}, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            if (name == null || name.isEmpty())
                map.put("name", cursor.getString(cursor.getColumnIndex("number")));
            else
                map.put("name", name);
            map.put("number", cursor.getString(cursor.getColumnIndex("number")));
            map.put("attribution", cursor.getString(cursor.getColumnIndex("attribution")));
            map.put("calltime", cursor.getString(cursor.getColumnIndex("calltime")));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            String duration = cursor.getString(cursor.getColumnIndex("duration"));
            if (status == 1 && duration.equals("0"))
                duration = "未接通";
            else if (status == 0 && duration.equals("0")) {
                duration = "拒接";
                status = 2;
            }
            else if (status == 2 && duration.equals("0"))
                duration = "未接";
            else
                duration = duration + "秒";
            map.put("duration", duration);
            map.put("status", images[status]);
            record_list.add(map);
        }
        cursor.close();
        Collections.sort(record_list, recordComparator);
        adapter = new SimpleAdapter(this, record_list, R.layout.dial_listview_item,
                new String[]{"name", "attribution", "calltime", "status", "duration"},
                new int[]{R.id.person_name, R.id.attribution, R.id.time, R.id.call_status, R.id.duration});
        record_listview.setAdapter(adapter);
        record_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone_number = record_list.get(i).get("number").toString();
                makePhoneCall.makeCall(phone_number);
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
                if (!inContactList(record_list.get(i).get("number").toString())) {
                    popupMenu.getMenuInflater().inflate(R.menu.record_long_click_menu1, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.delete_record:
                                    deleteCallRecord(i);
                                    break;
                                case R.id.add_new_contact:
                                    addNewContact(i);
                                    break;
                                case R.id.store_contact:
                                    storeContact(i);
                                    break;
                                case R.id.add_white_list:
                                    addWhiteList(i);
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
                                    addWhiteList(i);
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

    private boolean inContactList(String number) {
        Cursor cursor = resolver.query(contactUri, new String[]{"number"}, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            if (number.equals(cursor.getString(cursor.getColumnIndex("number")))) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    private void addWhiteList(int i) {
        //加入白名单
        String number = record_list.get(i).get("number").toString();
        Cursor cursor = resolver.query(contactUri, new String[]{"number", "name"}, "number = ?", new String[]{number}, null);
        if (cursor != null && cursor.moveToNext()) {
            ContentValues values = new ContentValues();
            values.put("whitelist", 1);
            resolver.update(contactUri, values, "number = ?", new String[]{number});
        } else {
            ContentValues values = new ContentValues();
            values.put("name", number);
            values.put("number", number);
            values.put("attribution", new QueryAttribution().getAttribution(number));
            values.put("whitelist", 1);
            resolver.insert(contactUri, values);
        }
        Toast.makeText(this, "已加入白名单", Toast.LENGTH_SHORT).show();
        cursor.close();
    }

    private void addNewContact(int i) {
        //新建联系人
        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
        intent.putExtra("number", record_list.get(i).get("number").toString());
        startActivity(intent);
    }

    private void storeContact(int i) {
        //保存至已有联系人
        Bundle bundle = new Bundle();
        bundle.putString("number", record_list.get(i).get("number").toString());
        bundle.putString("attribution", record_list.get(i).get("attribution").toString());
        Intent intent = new Intent(this, StoreContactActivity.class);
        intent.putExtra("key", bundle);
        startActivity(intent);
    }

    private void giveTips() {
        //温馨提示
        if (reminder) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String message = "";
            if (month == 1 && day == 1)
                message = "春节";
            else if (month == 1 && day == 15)
                message = "元宵";
            else if (month == 5 && day == 5)
                message = "端午";
            else if (month == 7 && day == 7)
                message = "七夕";
            else if (month == 8 && day == 15)
                message = "中秋";
            else if (month == 9 && day == 9)
                message = "重阳";
            else if (month == 12 && day == 8)
                message = "腊八";
            else if (month == 12 && monthDays(year, month) == day)
                message = "除夕";
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "Tips", NOTIFICATION_CHANNEL_NAME = "Tips";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager.createNotificationChannel(notificationChannel);
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
            if (!message.isEmpty()) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                Notification notification = builder.setContentTitle("节日提醒")
                        .setSmallIcon(R.drawable.festival_reminder)
                        .setContentText("今天是" + message)
                        .setTicker("节日提醒")
                        .setAutoCancel(true)
                        .setFullScreenIntent(pendingIntent, true)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setOngoing(false)
                        .setDefaults(Notification.DEFAULT_VIBRATE).build();
                notificationManager.notify(0, notification);
            }
            Cursor cursor = resolver.query(contactUri, new String[]{"name", "number"}, "birthday like ?",
                    new String[]{"%" + month + "-" + day}, null);
            ArrayList<String> names = new ArrayList<>();
            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                if (!name.equals(number) && !names.contains(name))
                    names.add(name);
            }
            cursor.close();
            for (int i = 0; i < names.size(); ++i) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                Notification notification = builder.setContentTitle("生日提醒")
                        .setSmallIcon(R.drawable.birthdy_reminder)
                        .setContentText("今天是" + names.get(i) + "的生日")
                        .setTicker("生日提醒")
                        .setFullScreenIntent(pendingIntent, true)
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setOngoing(false)
                        .setDefaults(Notification.DEFAULT_VIBRATE).build();
                notificationManager.notify(i + 1, notification);
            }
        }
    }

    private int monthDays(int year, int month) {
        final long[] LUNAR_INFO = new long[]{0x04bd8, 0x04ae0,
                0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
                0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
                0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5,
                0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
                0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3,
                0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
                0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0,
                0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
                0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570,
                0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
                0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0,
                0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
                0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
                0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
                0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7,
                0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50,
                0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954,
                0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
                0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0,
                0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
                0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20,
                0x0ada0};
        if ((LUNAR_INFO[year - 1900] & (0x10000 >> month)) == 0)
            return 29;
        else
            return 30;
    }

    protected List<Contact> searchContect(String query) {
        List<Contact> result = new ArrayList<>();
        String args = "";
        for (int i = 0; i < query.length(); ++i)
            args = args + query.charAt(i) + "%";
        if (args.isEmpty())
            args = "%";
        String[] argList = {args};
        Cursor cursor = resolver.query(contactUri, new String[]{"distinct name"},
                "pinyin like ?", argList, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            result.add(contact);
        }
        cursor.close();
        return result;
    }

    private void listener() {
        myCall = new CustomPhoneStateListener(this);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(myCall, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void setUpNoDisturb(final MenuItem item) {
        myCall.setChecked(1);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View source = LayoutInflater.from(this).inflate(R.layout.white_list_time, null);
        final TimePicker startTime = (TimePicker) source.findViewById(R.id.start_time_picker);
        final TimePicker endTime = (TimePicker) source.findViewById(R.id.end_time_picker);
        startTime.setIs24HourView(true);
        endTime.setIs24HourView(true);
        long time = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        BeginTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        EndTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
        startTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        startTime.setMinute(calendar.get(Calendar.MINUTE));
        endTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        endTime.setMinute(calendar.get(Calendar.MINUTE));
        startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                BeginTime = hour * 100 + minute;
            }
        });
        endTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                EndTime = hour * 100 + minute;
            }
        });
        AlertDialog alertDialog = dialogBuilder.setView(source)
                .setTitle("免打扰时间")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        item.setChecked(false);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myCall.setBeginTime(BeginTime);
                        myCall.setEndTime(EndTime);
                    }
                }).create();
        alertDialog.show();
        Log.d("begintime", BeginTime+"");
        Log.d("endtime", EndTime+"");
    }
}
