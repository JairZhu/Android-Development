package com.example.phonebook;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class ContactInfoRecord extends Fragment {
    private String name;
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private ContentResolver resolver;
    private AlertDialog alertDialog;
    private ArrayList<Map<String, Object>> lists;
    private ListView listView;
    private SimpleAdapter adapter;
    private Toolbar toolbar;
    private ArrayList<String> numbers;
    private TextView text;
    private ImageView img;
    private Receiver receiver;
    private int[] images = {R.drawable.callin, R.drawable.callout, R.drawable.missed};
    private Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
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
                updateListView();
            }
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setUpReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved) {
        View rootView = inflater.inflate(R.layout.contact_info_record, viewGroup, false);
        resolver = getActivity().getContentResolver();
        setToolBar(rootView);
        setListView(rootView);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean userVisibleHint) {
        super.onHiddenChanged(userVisibleHint);
        if (userVisibleHint)
            updateListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }

    private void setUpReceiver() {
        receiver = new Receiver(handler);
        IntentFilter filter = new IntentFilter();
        filter.addAction("update");
        getContext().registerReceiver(receiver, filter);
    }

    private void updateListView() {
        lists.clear();
        for (int i = 0; i < numbers.size(); ++i) {
            Cursor recordCursor = resolver.query(callRecordUri, new String[]{"calltime", "duration", "status"},
                    "number = ?", new String[]{numbers.get(i)}, null);
            while (recordCursor != null && recordCursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("calltime", recordCursor.getString(recordCursor.getColumnIndex("calltime")));
                int status = recordCursor.getInt(recordCursor.getColumnIndex("status"));
                String duration = recordCursor.getString(recordCursor.getColumnIndex("duration"));
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
                map.put("number", numbers.get(i));
                lists.add(map);
            }
            recordCursor.close();
        }
        Collections.sort(lists, comparator);
        adapter.notifyDataSetChanged();
        checkNoneRecord();
    }

    private void setListView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.contact_info_record_listview);
        Cursor cursor = resolver.query(contactUri, new String[]{"number"}, "name = ?", new String[]{name}, null);
        numbers = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            String numb = cursor.getString(cursor.getColumnIndex("number"));
            numbers.add(numb);
        }
        cursor.close();
        lists = new ArrayList<>();
        for (int i = 0; i < numbers.size(); ++i) {
            Cursor recordCursor = resolver.query(callRecordUri, new String[]{"calltime", "duration", "status"},
                    "number = ?", new String[]{numbers.get(i)}, null);
            while (recordCursor != null && recordCursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("calltime", recordCursor.getString(recordCursor.getColumnIndex("calltime")));
                int status = recordCursor.getInt(recordCursor.getColumnIndex("status"));
                String duration = recordCursor.getString(recordCursor.getColumnIndex("duration"));
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
                map.put("number", numbers.get(i));
                lists.add(map);
            }
            recordCursor.close();
        }
        Collections.sort(lists, comparator);
        adapter = new SimpleAdapter(getContext(), lists, R.layout.contact_info_record_list_item,
                new String[]{"calltime", "duration", "status", "number"},
                new int[]{R.id.calltime, R.id.duration, R.id.image, R.id.number});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone_number = lists.get(i).get("number").toString();
                MakePhoneCall makePhoneCall = new MakePhoneCall(getContext(), resolver);
                makePhoneCall.makeCall(phone_number);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.contact_record_long_click, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete) {
                            //删除对应的通话记录
                            RelativeLayout form = new RelativeLayout(getContext());
                            TextView textView = new TextView(getContext());
                            textView.setText("是否删除此通话记录？");
                            textView.setPadding(0, 20, 0, 0);
                            textView.setTextSize(18);
                            form.setGravity(Gravity.CENTER);
                            textView.setTextColor(Color.BLACK);
                            form.addView(textView);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            alertDialog = builder.setIcon(R.drawable.ic_warning_green_24dp)
                                    .setTitle("警告")
                                    .setView(form)
                                    .setNegativeButton("取消", null)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            resolver.delete(callRecordUri, "calltime = ? and number = ?",
                                                    new String[]{lists.get(i).get("calltime").toString(), lists.get(i).get("number").toString()});
                                            lists.remove(i);
                                            adapter.notifyDataSetChanged();
                                            checkNoneRecord();
                                        }
                                    }).create();
                            alertDialog.show();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
        img = (ImageView) rootView.findViewById(R.id.image);
        text = (TextView) rootView.findViewById(R.id.text);
        checkNoneRecord();
    }

    private void checkNoneRecord() {
        if (lists.size() > 0) {
            img.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        }
    }

    private void setToolBar(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.addView(LayoutInflater.from(getContext()).inflate(R.layout.contact_info_record_menu, null, false),
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageButton delete = (ImageButton) rootView.findViewById(R.id.delete_all_record);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除所有通话记录
                RelativeLayout form = new RelativeLayout(getContext());
                TextView textView = new TextView(getContext());
                textView.setText("是否删除全部通话记录？");
                textView.setTextSize(18);
                textView.setPadding(0, 20, 0, 0);
                form.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                form.addView(textView);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                alertDialog = builder.setIcon(R.drawable.ic_warning_green_24dp)
                        .setTitle("警告")
                        .setView(form)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int j = 0; j < numbers.size(); ++j)
                                    resolver.delete(callRecordUri, "number = ?", new String[]{numbers.get(j)});
                                lists.clear();
                                adapter.notifyDataSetChanged();
                                checkNoneRecord();
                            }
                        }).create();
                alertDialog.show();
            }
        });
    }
}
