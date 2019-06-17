package com.example.phonebook;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ContactInfoFragment extends Fragment {
    private String name;
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private ContentResolver resolver;
    private ListView listView;
    private ArrayList<Map<String, Object>> lists;
    private SimpleAdapter adapter;
    private Toolbar toolbar;
    private AlertDialog alertDialog;

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved) {
        View rootView = inflater.inflate(R.layout.contact_info_fragment, viewGroup, false);
        resolver = getActivity().getContentResolver();
        setListView(rootView);
        setToolBar(rootView);
        return rootView;
    }

    private void setListView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.contact_info_listview);
        Cursor cursor = resolver.query(contactUri, new String[]{"number", "attribution"},
                "name = ?", new String[]{name}, null);
        lists = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            map.put("number", cursor.getString(cursor.getColumnIndex("number")));
            map.put("attribution", cursor.getString(cursor.getColumnIndex("attribution")));
            lists.add(map);
        }
        adapter = new SimpleAdapter(getContext(), lists, R.layout.contact_info_list_item,
                new String[]{"number", "attribution"}, new int[]{R.id.number, R.id.attribution});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone_number = lists.get(i).get("number").toString();
                MakePhoneCall makePhoneCall = new MakePhoneCall(getContext(), resolver);
                makePhoneCall.makeCall(phone_number);
            }
        });
    }

    private void setToolBar(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.addView(LayoutInflater.from(getContext()).inflate(R.layout.contact_info_menu, null, false),
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageButton edit = (ImageButton) rootView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editContactInfo();
            }
        });
        ImageButton more = (ImageButton) rootView.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.contact_info_edit_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.share_contact:
                                shareContact();
                                break;
                            case R.id.add_white_list:
                                addWhiteList();
                                break;
                            case R.id.delete_record:
                                deleteRecord();
                                break;
                            case R.id.delete_contact:
                                deleteContact();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void editContactInfo() {
        //TODO:编辑联系人信息
    }

    private void addWhiteList() {
        //加入白名单
        ContentValues values = new ContentValues();
        values.put("whitelist", 1);
        resolver.update(contactUri, values, "name = ?", new String[]{name});
        Toast.makeText(getContext(), "已加入白名单", Toast.LENGTH_SHORT).show();
    }

    private void deleteRecord() {
        //擦除联系痕迹
        RelativeLayout form = new RelativeLayout(getContext());
        TextView textView = new TextView(getContext());
        textView.setText("是否擦除所有联系痕迹？");
        textView.setTextSize(18);
        textView.setPadding(30, 40, 0, 0);
        textView.setTextColor(Color.BLACK);
        form.addView(textView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        alertDialog = builder.setView(form)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        resolver.delete(callRecordUri, "name = ?", new String[]{name});
                    }
                }).create();
        alertDialog.show();
    }

    private void deleteContact() {
        //删除联系人
        RelativeLayout form = new RelativeLayout(getContext());
        TextView textView = new TextView(getContext());
        textView.setText("是否删除此联系人？");
        textView.setTextSize(18);
        textView.setPadding(30, 40, 0, 0);
        textView.setTextColor(Color.BLACK);
        form.addView(textView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        alertDialog = builder.setView(form)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        resolver.delete(contactUri, "name = ?", new String[]{name});
                        ContentValues values = new ContentValues();
                        values.put("name", "");
                        resolver.update(callRecordUri, values, "name = ?", new String[]{name});
                        getActivity().finish();
                    }
                }).create();
        alertDialog.show();
    }

    private void shareContact() {
        //TODO:分享联系人
    }
}
