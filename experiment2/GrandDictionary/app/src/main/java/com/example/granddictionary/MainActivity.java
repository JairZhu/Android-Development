package com.example.granddictionary;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Uri uri = Uri.parse("content://com.example.providers.DictProvider/");
    ContentResolver resolver;
    AlertDialog alertDialog;
    AlertDialog change_alertDialog;
    ArrayList<Map<String, Object>> lists = new ArrayList<>();
    SimpleAdapter adapter = null;
    ProgressDialog progressDialog;
    int last_id = -999999, progressstatus;
    boolean show_explan = false;

    Comparator<Map<String, Object>> comparator = new Comparator<Map<String, Object>>() {
        @Override
        public int compare(Map<String, Object> stringObjectMap, Map<String, Object> t1) {
            if (stringObjectMap.get("word").toString().toLowerCase().compareTo(t1.get("word").toString().toLowerCase()) < 0)
                return -1;
            else
                return 1;
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            if (message.what == 0)
                progressDialog.setProgress(progressstatus);
            else if (message.what == 1) {
                Collections.sort(lists, comparator);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("简明英文词典");
        actionBar.setSubtitle("中山大学");
        listView = (ListView) findViewById(R.id.listview);
        resolver = getContentResolver();
        display();
        createchildlinearlayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.downloadwords:
                getJson();
                break;
            case R.id.displaymeaning:
                if (item.isChecked()) {
                    item.setChecked(false);
                    show_explan = false;
                    setListView();
                }
                else {
                    TextView textView = (TextView) findViewById(R.id.word_information);
                    textView.setVisibility(View.INVISIBLE);
                    ViewGroup.LayoutParams params = listView.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    listView.setLayoutParams(params);
                    item.setChecked(true);
                    show_explan = true;
                    setListView();
                }
                break;
            case R.id.search_button:
                search_word();
                break;
            case R.id.add_button:
                add_word();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createchildlinearlayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
        final String[] strings = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for (int i = 0; i < strings.length; ++i) {
            final TextView tv = new TextView(this);
            tv.setText(strings[i]);
            tv.setTextSize(18);
            tv.setPadding(2,0,2,0);
            tv.setHeight(50);
            tv.setWidth(30);
            tv.setClickable(true);
            tv.setId(i);
            tv.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tvlp.setMargins(0,0,4,0);
            tv.setBackgroundColor(getResources().getColor(R.color.peru));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView;
                    if (last_id != -999999) {
                        textView = (TextView) findViewById(last_id);
                        textView.setBackgroundColor(getResources().getColor(R.color.peru));
                    }
                    last_id = view.getId();
                    textView = (TextView) findViewById(view.getId());
                    textView.setBackgroundColor(getResources().getColor(R.color.cyan));
                    Cursor cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"},
                            "word like ?", new String[]{strings[view.getId()] + "%"},
                            "word");
                    change_lists(cursor);
                    adapter.notifyDataSetChanged();
                }
            });
            linearLayout.addView(tv, tvlp);
        }
    }

    public void display() {
        Cursor cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"},
                null, null, "word");
        change_lists(cursor);
        setListView();
    }

    public void change_lists(Cursor cursor) {
        lists.clear();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("word", cursor.getString(cursor.getColumnIndex("word")));
            map.put("explanation", cursor.getString(cursor.getColumnIndex("explanation")));
            map.put("level", cursor.getString(cursor.getColumnIndex("level")));
            lists.add(map);
        }
        Collections.sort(lists, comparator);
    }


    public void setListView() {
        Collections.sort(lists, comparator);
        if (show_explan)
            adapter = new SimpleAdapter(this, lists, R.layout.list_meaning, new String[]{"word", "explanation"}, new int[]{R.id.word_explanation, R.id.explanation});
        else
            adapter = new SimpleAdapter(this, lists, R.layout.list_item, new String[]{"word"}, new int[]{R.id.word});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (show_explan) return;
                TextView textView = (TextView) findViewById(R.id.word_information);
                textView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = 680;
                listView.setLayoutParams(params);
                String str = lists.get(i).get("word").toString();
                str += "\n" + lists.get(i).get("explanation").toString();
                textView.setText(str);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.long_click_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.change:
                                change_word(i);
                                break;
                            case R.id.delete:
                                delete_word(i);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    public void delete_word(final int position) {
        RelativeLayout word_form = new RelativeLayout(this);
        TextView textView = new TextView(this);
        textView.setText("是否确定要删除单词'" + lists.get(position).get("word").toString() +"'?");
        textView.setTextSize(18);
        textView.setPadding(30, 40, 0, 0);
        textView.setTextColor(Color.BLACK);
        word_form.addView(textView);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        change_alertDialog = builder.setIcon(R.drawable.dict)
                .setTitle("删除单词")
                .setView(word_form)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resolver.delete(uri, "word = ?", new String[]{lists.get(position).get("word").toString()});
                        lists.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        change_alertDialog.show();
    }

    public void change_word(final int position) {
        TableLayout word_form = (TableLayout) getLayoutInflater().inflate(R.layout.change_word, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        change_alertDialog = builder.setIcon(R.drawable.dict)
                .setTitle("修改单词")
                .setView(word_form)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText newword = (EditText) change_alertDialog.findViewById(R.id.change_word);
                        EditText newexplan = (EditText) change_alertDialog.findViewById(R.id.change_explanation);
                        EditText newlevel = (EditText) change_alertDialog.findViewById(R.id.change_level);
                        Cursor cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"}, "word = ?",
                                new String[]{lists.get(position).get("word").toString()}, null);
                        cursor.moveToFirst();
                        int index = cursor.getInt(cursor.getColumnIndex("_id"));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_id", index);
                        contentValues.put("word", newword.getText().toString());
                        contentValues.put("explanation", newexplan.getText().toString());
                        contentValues.put("level", newlevel.getText().toString());
                        resolver.update(uri, contentValues, "_id = ?", new String[]{Integer.toString(index)});
                        for (int j = 0; j < lists.size(); ++j) {
                            if (lists.get(j).get("word").toString().equals(cursor.getString(cursor.getColumnIndex("word")))) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("word", newword.getText().toString());
                                map.put("explanation", newexplan.getText().toString());
                                map.put("level", newlevel.getText().toString());
                                map.put("_id", index);
                                lists.remove(j);
                                lists.add(map);
                                break;
                            }
                        }
                        Collections.sort(lists, comparator);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        change_alertDialog.show();
    }

    public void getJson() {
        showProgress(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String address = "http://172.18.187.9:8080/dict/";
                    URL url = new URL(address);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                        stringBuilder.append(line);
                    String str = stringBuilder.toString();
                    JSONArray jsonArray = new JSONArray(str);
                    Cursor cursor = resolver.query(uri, new String[]{"word"}, null, null, null);
                    Set set = new HashSet();
                    while (cursor.moveToNext())
                        set.add(cursor.getString(cursor.getColumnIndex("word")));
                    lists.clear();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        WordRec wordRec = parserJSON(jsonArray.get(i).toString());
                        ContentValues values = new ContentValues();
                        values.put("_id", i);
                        values.put("word", wordRec.getWord());
                        values.put("explanation", wordRec.getExplanation());
                        values.put("level", wordRec.getLevel());
                        Map<String, Object> map = new HashMap<>();
                        map.put("word", wordRec.getWord());
                        map.put("explanation", wordRec.getExplanation());
                        map.put("level", wordRec.getLevel());
                        map.put("_id", i);
                        lists.add(map);
                        if (set.contains(wordRec.getWord()))
                            resolver.update(uri, values, "word = ?", new String[]{wordRec.getWord()});
                        else
                            resolver.insert(uri, values);
                        progressstatus = 100 * (i + 1) / jsonArray.length();
                        handler.sendEmptyMessage(0);
                    }
                    progressDialog.dismiss();
                    handler.sendEmptyMessage(1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void showProgress(int max_length) {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(max_length);
        progressDialog.setTitle("下载单词");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

    }

    public static WordRec parserJSON(String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        WordRec wordRec = new WordRec();
        wordRec.setWord(jsonObject.optString("word"));
        wordRec.setExplanation(jsonObject.optString("explanation"));
        wordRec.setLevel(Integer.parseInt(jsonObject.optString("level")));
        return wordRec;
    }

    public void add_word() {
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
                        Cursor cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"}, "word = ?",
                                new String[]{newword.getText().toString()}, null);
                        if (cursor.getCount() == 0) {
                            Log.v("out:", "insert");
                            cursor = resolver.query(uri, new String[]{"count(*)"}, null,
                                    null, null);
                            cursor.moveToFirst();
                            int index = cursor.getInt(0);
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("_id", index + 1);
                            contentValues.put("word", newword.getText().toString());
                            contentValues.put("explanation", newexplan.getText().toString());
                            contentValues.put("level", newlevel.getText().toString());
                            resolver.insert(uri, contentValues);
                            Map<String, Object> map = new HashMap<>();
                            map.put("word", newword.getText().toString());
                            map.put("explanation", newexplan.getText().toString());
                            map.put("level", newlevel.getText().toString());
                            lists.add(map);
                            Collections.sort(lists, comparator);
                            adapter.notifyDataSetChanged();
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
                            for (int j = 0; j < lists.size(); ++j) {
                                if (lists.get(j).get("word").toString().equals(cursor.getString(cursor.getColumnIndex("word")))) {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("word", newword.getText().toString());
                                    map.put("explanation", newexplan.getText().toString());
                                    map.put("level", newlevel.getText().toString());
                                    map.put("_id", index);
                                    lists.remove(j);
                                    lists.add(map);
                                    break;
                                }
                            }
                            Collections.sort(lists, comparator);
                            adapter.notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "新增单词失败", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
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
                        Cursor cursor = resolver.query(uri, new String[]{"_id", "word", "explanation", "level"},
                                "word like ?", new String[]{"%" + searchword.getText().toString() + "%"},
                                "word");
                        change_lists(cursor);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
        alertDialog.show();
    }

    public void toggle(View view) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        checkedTextView.toggle();
    }
}

