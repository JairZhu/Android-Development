package com.example.phonebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;


public class ContactInfoRecord extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved) {
        View rootView = inflater.inflate(R.layout.contact_info_record, viewGroup, false);
        ListView listView = (ListView) rootView.findViewById(R.id.contact_info_record_listview);
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
        return rootView;
    }
}
