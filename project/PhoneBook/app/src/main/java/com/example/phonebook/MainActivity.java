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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.InputType;
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
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    private ContentResolver resolver;
    private ActionBar actionBar;
    private ListView record_listview;
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
                    && contact.getName().compareTo(t1.getName()) < 0)
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = getContentResolver();
        makePhoneCall = new MakePhoneCall(this, resolver);
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
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
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
        Cursor cursor = resolver.query(contactUri, new String[] {"distinct name"},
                null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contactList.add(contact);
        }
        Collections.sort(contactList, comparator);
        contactsAdapter.notifyDataSetChanged();
    }

    private void initialContactView() {
        Cursor cursor = resolver.query(contactUri, new String[] {"distinct name"},
                null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contactList.add(contact);
        }
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
                    Cursor cursor = resolver.query(callRecordUri, new String[]{"number", "name", "attribution",
                            "calltime", "duration", "status"}, null, null, null);
                    updateRecordListView(cursor);
                }
                else {
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

    @Override
    protected void onRestart() {
        super.onRestart();
        updateContactListView();
        Cursor cursor = resolver.query(callRecordUri, new String[]{"number", "name", "attribution",
                "calltime", "duration", "status"}, null, null, null);
        updateRecordListView(cursor);
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

    private void updateRecordListView(Cursor cursor) {
        record_list.clear();
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
            map.put("duration", cursor.getString(cursor.getColumnIndex("duration")));
            map.put("status", images[cursor.getInt(cursor.getColumnIndex("status"))]);
            record_list.add(map);
        }
        Collections.sort(record_list, recordComparator);
        adapter.notifyDataSetChanged();
    }

    private void deleteCallRecord(int position) {
        //删除通话记录
        resolver.delete(callRecordUri, "calltime = ? and number = ?",
                new String[]{record_list.get(position).get("calltime").toString(),
                        record_list.get(position).get("number").toString()});
        record_list.remove(position);
        adapter.notifyDataSetChanged();
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
            map.put("duration", cursor.getString(cursor.getColumnIndex("duration")));
            map.put("status", images[cursor.getInt(cursor.getColumnIndex("status"))]);
            record_list.add(map);
        }
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
            if (number.equals(cursor.getString(cursor.getColumnIndex("number"))))
                return true;
        }
        return false;
    }

    private void addWhiteList(int i) {
        //加入白名单
        ContentValues values = new ContentValues();
        values.put("whitelist", 1);
        resolver.update(contactUri, values, "name = ?", new String[]{record_list.get(i).get("name").toString()});
        Toast.makeText(this, "已加入白名单", Toast.LENGTH_SHORT).show();
    }

    private void addNewContact(int i) {
        //新建联系人
        Intent intent = new  Intent(MainActivity.this, AddContactActivity.class);
        intent.putExtra("number", record_list.get(i).get("number").toString());
        startActivity(intent);
    }

    private void storeContact(int i) {
        //TODO:保存至已有联系人

    }
}
