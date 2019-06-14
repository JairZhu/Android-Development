package com.example.phonebook;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class ContactInfoRecord extends Fragment {
    private String name;
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private ContentResolver resolver;
    private Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> stringObjectMap, Map<String, Object> t1) {
            if (stringObjectMap.get("calltime").toString().compareTo(t1.get("calltime").toString()) < 0)
                return 1;
            else
                return -1;
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved) {
        View rootView = inflater.inflate(R.layout.contact_info_record, viewGroup, false);
        resolver = getActivity().getContentResolver();
        setListView(rootView);
        setToolBar(rootView);
        return rootView;
    }

    private void setListView(View rootView) {
        ListView listView = (ListView) rootView.findViewById(R.id.contact_info_record_listview);
        Cursor cursor = resolver.query(contactUri, new String[]{"number"},"name = ?", new String[]{name}, null);
        ArrayList<String> numbers = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            String numb = cursor.getString(cursor.getColumnIndex("number"));
            numbers.add(numb);
        }
        cursor.close();
        final ArrayList<Map<String, Object>> lists = new ArrayList<>();
        int[] images = {R.drawable.callin, R.drawable.callout, R.drawable.missed};
        for (int i = 0; i < numbers.size(); ++i) {
            Cursor recordCursor = resolver.query(callRecordUri, new String[] {"calltime", "duration", "status"},
                    "number = ?", new String[]{numbers.get(i)}, null);
            while (recordCursor != null && recordCursor.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("calltime", recordCursor.getString(recordCursor.getColumnIndex("calltime")));
                map.put("duration", recordCursor.getString(recordCursor.getColumnIndex("duration")));
                map.put("status", images[recordCursor.getInt(recordCursor.getColumnIndex("status"))]);
                map.put("number", numbers.get(i));
                lists.add(map);
            }
            recordCursor.close();
        }
        Collections.sort(lists, comparator);
        SimpleAdapter adapter = new SimpleAdapter(getContext(), lists, R.layout.contact_info_record_list_item,
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
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.contact_record_long_click, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.delete) {
                            //删除对应的通话记录
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    private void setToolBar(View rootView) {
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.addView(LayoutInflater.from(getContext()).inflate(R.layout.contact_info_record_menu, null, false),
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ImageButton delete = (ImageButton) rootView.findViewById(R.id.delete_all_record);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:删除所有通话记录
            }
        });
    }
}
