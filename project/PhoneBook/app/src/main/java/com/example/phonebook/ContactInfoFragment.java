package com.example.phonebook;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ContactInfoFragment extends Fragment {
    private String name;
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private ContentResolver resolver;

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
        ListView listView = (ListView) rootView.findViewById(R.id.contact_info_listview);
        Cursor cursor = resolver.query(contactUri, new String[]{"number", "attribution"},
                "name = ?", new String[]{name}, null);
        final ArrayList<Map<String, Object>> lists = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            map.put("number", cursor.getString(cursor.getColumnIndex("number")));
            map.put("attribution", cursor.getString(cursor.getColumnIndex("attribution")));
            lists.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), lists, R.layout.contact_info_list_item,
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
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.addView(LayoutInflater.from(getContext()).inflate(R.layout.contact_info_menu, null, false),
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageButton edit = (ImageButton) rootView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:编辑联系人信息
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
                                //TODO:分享联系人
                                break;
                            case R.id.add_white_list:
                                //TODO:加入白名单
                                break;
                            case R.id.delete_record:
                                //TODO:擦除联系痕迹
                                break;
                            case R.id.delete_contact:
                                //TODO:删除联系人
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

}
