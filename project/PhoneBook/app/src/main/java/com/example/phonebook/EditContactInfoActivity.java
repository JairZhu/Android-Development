package com.example.phonebook;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class EditContactInfoActivity extends AppCompatActivity {
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private EditText newbirthday, newname;
    private TableLayout tableLayout;
    private ArrayList<Map<String, Object>> lists;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.edit_contact_info_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("编辑联系人");
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name"), birthday = bundle.getString("birthday");
        lists = (ArrayList<Map<String, Object>>) bundle.getSerializable("numberList");
        tableLayout = (TableLayout) findViewById(R.id.new_contact_tablelayout);
        newname = (EditText) findViewById(R.id.new_name);
        newbirthday = (EditText) findViewById(R.id.new_birthday);
        newname.setText(name);
        newbirthday.setText(birthday);
        newbirthday.setInputType(InputType.TYPE_NULL);
        newbirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickerDialog();
                }
            }
        });
        newbirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        ImageButton ibutton = (ImageButton) findViewById(R.id.add_button);
        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow tableRow = (TableRow) LayoutInflater.from(EditContactInfoActivity.this).inflate(R.layout.single_number_row, null);
                ImageButton imageButton = (ImageButton) tableRow.findViewById(R.id.delete_button);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TableRow row = (TableRow) view.getParent();
                        tableLayout.removeView(row);
                    }
                });
                tableLayout.addView(tableRow);
            }
        });
        for (int i = 0; i < lists.size(); ++i) {
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.single_number_row, null);
            EditText newnumber = (EditText) tableRow.findViewById(R.id.new_number);
            newnumber.setText(lists.get(i).get("number").toString());
            ImageButton imageButton = (ImageButton) tableRow.findViewById(R.id.delete_button);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TableRow row = (TableRow) view.getParent();
                    tableLayout.removeView(row);
                }
            });
            tableLayout.addView(tableRow);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.check:
                changeInformation();
                this.finish();
                break;
        }
        return true;
    }

    private void changeInformation() {
        ContentResolver resolver = getContentResolver();
        String name = newname.getText().toString();
        String birthday = newbirthday.getText().toString();
        for (int i = 0; i < lists.size(); ++i) {
            String number = lists.get(i).get("number").toString();
            resolver.delete(contactUri, "number = ?", new String[]{number});
            ContentValues values = new ContentValues();
            values.put("name", number);
            resolver.update(callRecordUri, values, "number = ?", new String[]{number});
        }
        int count = tableLayout.getChildCount();
        if (name.isEmpty() && (count <= 3 || checkNull())) {
            Toast.makeText(this, "联系人已删除", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 3; i < count; ++i) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            EditText newnumber = (EditText) row.getChildAt(1);
            String number = newnumber.getText().toString();
            if (number.isEmpty())
                continue;
            String tmpName = name;
<<<<<<< HEAD
            String pinyin = tmpName;
            if (tmpName.isEmpty()) {
                tmpName = number;
                pinyin = number;
            }
            else if (CharacterToPinyin.isChinese(tmpName))
                pinyin = CharacterToPinyin.toPinyin(tmpName);
            ContentValues values = new ContentValues();
            values.put("name", tmpName);
            values.put("pinyin", pinyin);
=======
            if (tmpName.isEmpty())
                tmpName = number;
            ContentValues values = new ContentValues();
            values.put("name", tmpName);
>>>>>>> master
            values.put("birthday", birthday);
            values.put("number", number);
            values.put("attribution", new QueryAttribution(number).getAttribution());
            resolver.insert(contactUri, values);
            values.clear();
            values.put("name", tmpName);
            resolver.update(callRecordUri, values, "number = ?", new String[]{number});
        }
    }

    private boolean checkNull() {
        for (int i = 3; i < tableLayout.getChildCount(); ++i) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            EditText newnumber = (EditText) row.getChildAt(1);
            String number = newnumber.getText().toString();
            if (!number.isEmpty())
                return false;
        }
        return true;
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                newbirthday.setText(year + "-" + (month + 1) + "-" + day);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
}
