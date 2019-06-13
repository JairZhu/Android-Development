package com.example.phonebook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

public class ContactInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saved) {
        View rootView = inflater.inflate(R.layout.contact_info_fragment, viewGroup, false);
        ListView listView = (ListView) rootView.findViewById(R.id.contact_info_listview);
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
        return rootView;
    }
}
