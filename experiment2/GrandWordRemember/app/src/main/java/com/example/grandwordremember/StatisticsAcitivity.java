package com.example.grandwordremember;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StatisticsAcitivity extends AppCompatActivity {
    Uri testdb = Uri.parse("content://com.example.grandwordremember.TestDB/");
    ContentResolver resolver;
    ArrayList<Map<String, Object>> lists = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listView;
    AlertDialog alertDialog;

    Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> stringObjectMap, Map<String, Object> t1) {
            if (stringObjectMap.get("word").toString().toLowerCase().compareTo(t1.get("word").toString().toLowerCase()) < 0)
                return -1;
            else
                return 1;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_statistics);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.statistics_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("测试统计");
        resolver = getContentResolver();
        listView = (ListView) findViewById(R.id.statistics_listview);
        display();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistics_search:
                search_word();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    public void display() {
        Cursor cursor = resolver.query(testdb, new String[]{"word", "level", "test_count", "correct_count"},
                null, null, "word");
        change_lists(cursor);
        setListView();
    }

    public void change_lists(Cursor cursor) {
        lists.clear();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            map.put("word", cursor.getString(cursor.getColumnIndex("word")));
            map.put("level", cursor.getString(cursor.getColumnIndex("level")));
            map.put("test_count", cursor.getInt(cursor.getColumnIndex("test_count")));
            map.put("correct_count", cursor.getInt(cursor.getColumnIndex("correct_count")));
            lists.add(map);
        }
        Collections.sort(lists, comparator);
    }

    public void setListView() {
        adapter = new SimpleAdapter(this, lists, R.layout.statistics_item, new String[]{"word", "level", "test_count", "correct_count"},
                new int[]{R.id.statistics_word, R.id.statistics_level, R.id.test_count, R.id.correct_count});
        listView.setAdapter(adapter);
    }

    public void search_word() {
        TableLayout wordform = (TableLayout) getLayoutInflater().inflate(R.layout.search_word, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.setIcon(R.drawable.dict)
                .setTitle("查询单词")
                .setView(wordform)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText searchword = (EditText) alertDialog.findViewById(R.id.search_word);
                        Cursor cursor = resolver.query(testdb, new String[]{"word", "level", "test_count", "correct_count"},
                                "word like ?", new String[]{searchword.getText().toString() + "%"},
                                "word");
                        change_lists(cursor);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        alertDialog.show();
    }
}
