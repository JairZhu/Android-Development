package com.example.grandwordremember;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestActivity extends AppCompatActivity {
    Uri uri = Uri.parse("content://com.example.providers.DictProvider/");
    Uri testdb = Uri.parse("content://com.example.grandwordremember.TestDB/");
    ContentResolver resolver;
    ArrayList<WordRec> word_lists;
    TestAdapter adapter;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("学霸背单词");
        actionBar.setSubtitle("单词测试");
        resolver = getContentResolver();
        settings = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_test:
                testing();
                break;
            case R.id.start_review:
                reviewing();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    public void testing() {
        Set set = new HashSet();
        Cursor cursor = resolver.query(testdb, new String[]{"_id", "word", "level"}, null, null, null);
        while (cursor.moveToNext())
            set.add(cursor.getString(cursor.getColumnIndex("word")));
        cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"}, null, null, "word");
        word_lists = new ArrayList<>();
        int count = 0;
        Log.v("setting", settings.getString("edittext_key", ""));
        while (cursor.moveToNext() && count < Integer.parseInt(settings.getString("edittext_key", ""))) {
            if (!set.contains(cursor.getString(cursor.getColumnIndex("word")))) {
                Log.v("count", count + "");
                Log.v("size", word_lists.size() + "");
                WordRec wordRec = new WordRec();
                wordRec.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                wordRec.setWord(cursor.getString(cursor.getColumnIndex("word")));
                String explan = cursor.getString(cursor.getColumnIndex("explanation"));
                wordRec.setExplanation(explan.substring(explan.indexOf("]") + 1));
                wordRec.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
                word_lists.add(wordRec);
                count++;
            }
        }
        Log.v("word_lists", word_lists.toString());
        adapter = new TestAdapter(word_lists, this);
        adapter.color = settings.getString("list_key", "");
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        final Button button = (Button) findViewById(R.id.submit);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                button.setVisibility(View.INVISIBLE);
                adapter.show_answer = true;
                adapter.notifyDataSetChanged();
                if (!settings.getBoolean("checkbox_key", true))
                    return;
                for (int i = 0; i < word_lists.size(); ++i) {
                    String word = word_lists.get(i).getWord();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("word", word);
                    contentValues.put("level", word_lists.get(i).getLevel());
                    contentValues.put("test_count", 1);
                    if (adapter.choose.keySet().contains(word) &&
                            word_lists.get(i).getExplanation().equals(adapter.choose.get(word)))
                        contentValues.put("correct_count", 1);
                    resolver.insert(testdb, contentValues);
                    Log.v("insert", contentValues.toString());
                }
            }
        });
    }

    public void reviewing() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("单词复习");
        Set set = new HashSet();
        Cursor cursor = resolver.query(testdb, new String[]{"word"}, null, null, null);
        while (cursor.moveToNext())
            set.add(cursor.getString(cursor.getColumnIndex("word")));
        cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"}, null, null, "word");
        word_lists = new ArrayList<>();
        int count = 0;
        while (cursor.moveToNext() && count < set.size() && set.contains(cursor.getString(cursor.getColumnIndex("word")))) {
            WordRec wordRec = new WordRec();
            wordRec.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            wordRec.setWord(cursor.getString(cursor.getColumnIndex("word")));
            String explan = cursor.getString(cursor.getColumnIndex("explanation"));
            wordRec.setExplanation(explan.substring(explan.indexOf("]") + 1));
            wordRec.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
            word_lists.add(wordRec);
            count++;
        }
        adapter = new TestAdapter(word_lists, this);
        adapter.color = settings.getString("list_key", "");
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        final Button button = (Button) findViewById(R.id.submit);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                button.setVisibility(View.INVISIBLE);
                adapter.show_answer = true;
                adapter.notifyDataSetChanged();
                if (!settings.getBoolean("checkbox_key", true))
                    return;
                for (int i = 0; i < word_lists.size(); ++i) {
                    String word = word_lists.get(i).getWord();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("word", word);
                    contentValues.put("level", word_lists.get(i).getLevel());
                    Cursor cs = resolver.query(testdb, new String[]{"test_count", "correct_count"},
                            "word = ?", new String[]{word}, null);
                    cs.moveToFirst();
                    contentValues.put("test_count", cs.getInt(cs.getColumnIndex("test_count")) + 1);
                    if (adapter.choose.keySet().contains(word) &&
                            word_lists.get(i).getExplanation().equals(adapter.choose.get(word)))
                        contentValues.put("correct_count", cs.getInt(cs.getColumnIndex("correct_count")) + 1);
                    resolver.update(testdb, contentValues, "word = ?", new String[]{word});
                    Log.v("update", contentValues.toString());
                }
            }
        });
    }
}
