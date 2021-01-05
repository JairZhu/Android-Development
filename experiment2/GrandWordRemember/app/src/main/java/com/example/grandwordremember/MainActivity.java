package com.example.grandwordremember;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    Uri uri = Uri.parse("content://com.example.providers.DictProvider/");
    Uri test_uri = Uri.parse("content://com.example.grandwordremember.TestDB/");
    ContentResolver resolver;
    ListView listView;
    SimpleAdapter adapter;
    ArrayList<Map<String, Object>> lists = new ArrayList<>();

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("-快速记忆法");
        resolver = getContentResolver();
        listView = (ListView) findViewById(R.id.find_list_view);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.test:
                intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.add_word:
                add_new_word();
                break;
            case R.id.learning_statistics:
                intent = new Intent(this, StatisticsAcitivity.class);
                startActivity(intent);
                break;
            case R.id.system_settings:
                intent = new Intent(this, SystemSettingAcitivity.class);
                startActivity(intent);
                break;
            case R.id.find_word:
                find_word();
                break;
        }
        return true;
    }

    public void add_new_word() {
        TableLayout word_form = (TableLayout) getLayoutInflater().inflate(R.layout.new_word, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        alertDialog = builder.setIcon(R.drawable.dict)
                .setTitle("增加单词")
                .setView(word_form)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText newword = (EditText) alertDialog.findViewById(R.id.new_word);
                        EditText newexplan = (EditText) alertDialog.findViewById(R.id.new_explanation);
                        EditText newlevel = (EditText) alertDialog.findViewById(R.id.new_level);
                        CheckedTextView checkedTextView = (CheckedTextView) alertDialog.findViewById(R.id.check);
                        Cursor cursor = resolver.query(uri, new String[]{"_id"}, "word = ?", new String[]{newword.getText().toString()}, null);
                        if (cursor.getCount() == 0) {
                            Log.v("out:", "insert");
                            cursor = resolver.query(uri, new String[]{"count(*)"}, null, null, null);
                            cursor.moveToFirst();
                            int index = cursor.getInt(0);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("_id", index + 1);
                            contentValues.put("word", newword.getText().toString());
                            contentValues.put("explanation", newexplan.getText().toString());
                            contentValues.put("level", newlevel.getText().toString());
                            resolver.insert(uri, contentValues);
                        }
                        else if (checkedTextView.isChecked()) {
                            cursor.moveToFirst();
                            int index = cursor.getInt(cursor.getColumnIndex("_id"));
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("_id", index);
                            contentValues.put("word", newword.getText().toString());
                            contentValues.put("explanation", newexplan.getText().toString());
                            contentValues.put("level", newlevel.getText().toString());
                            resolver.update(uri, contentValues, "word = ?", new String[]{newword.getText().toString()});
                        }
                        else
                            Toast.makeText(getApplicationContext(), "新增单词失败", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }

    public void find_word() {
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
                        Cursor cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"},
                                "word like ?", new String[]{"%" + searchword.getText().toString() + "%"},
                                "word");
                        display(cursor);
                    }
                }).create();
        alertDialog.show();
    }

    public void display(Cursor cursor) {
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("word", cursor.getString(cursor.getColumnIndex("word")));
            map.put("explanation", cursor.getString(cursor.getColumnIndex("explanation")));
            map.put("level", cursor.getString(cursor.getColumnIndex("level")));
            lists.add(map);
        }
        Collections.sort(lists, comparator);
        adapter = new SimpleAdapter(this, lists, R.layout.list_meaning, new String[]{"word", "explanation"}, new int[]{R.id.word_explanation, R.id.explanation});
        listView.setAdapter(adapter);
    }

    public void toggle(View view) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        checkedTextView.toggle();
    }
}
