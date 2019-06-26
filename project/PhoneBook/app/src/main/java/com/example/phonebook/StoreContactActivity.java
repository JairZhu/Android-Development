package com.example.phonebook;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StoreContactActivity extends AppCompatActivity {
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private ContentResolver resolver;
    private RecyclerView contacts_view;
    private StoreContactAdapter contactsAdapter;
    private WaveSideBar sideBar;
    private List<Contact> contactList = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.store_contact_activity);
        resolver = getContentResolver();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择联系人");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getBundleExtra("key");
        String number = bundle.getString("number");
        String attribution = bundle.getString("attribution");
        Cursor cursor = resolver.query(contactUri, new String[] {"distinct name"},
                null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setName(cursor.getString(cursor.getColumnIndex("name")));
            contactList.add(contact);
        }
        Collections.sort(contactList, comparator);
        contacts_view = (RecyclerView) findViewById(R.id.store_recyclerview);
        contacts_view.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter = new StoreContactAdapter(this, contactList, R.layout.contact_listview_item, number, attribution);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
