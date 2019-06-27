package com.example.phonebook;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {
    private Uri uri = Uri.parse("content://com.example.providers.ContactDB/");
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private EditText newname, newnumber, newbirthday;
    private ImageButton newConactImage;
    private CheckBox newwhitelist;
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.new_contact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("新建联系人");
        actionBar.setDisplayHomeAsUpEnabled(true);
        resolver = getContentResolver();
        newname = (EditText) findViewById(R.id.new_name);
        newnumber = (EditText) findViewById(R.id.new_number);
        newbirthday = (EditText) findViewById(R.id.new_birthday);
        newConactImage = (ImageButton) findViewById(R.id.new_contact_image);
        newwhitelist = (CheckBox) findViewById(R.id.add_white_list);
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
        newnumber.setText(getIntent().getStringExtra("number"));
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(AddContactActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                newbirthday.setText(year + "-" + (month + 1) + "-" + day);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
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
                String number = newnumber.getText().toString();
                Cursor cur= resolver.query(uri, new String[]{"number"}, null, null, null);
                while (cur != null && cur.moveToNext())
                    if (number.equals(cur.getString(cur.getColumnIndex("number"))))
                        this.finish();
                if (checkInfo()) {
                    ContentValues contentValues = new ContentValues();
                    String name = newname.getText().toString();
                    String pinyin = name;
                    if (name.isEmpty()) {
                        name = number;
                        pinyin = number;
                    }
                    else if (CharacterToPinyin.isChinese(name))
                        pinyin = CharacterToPinyin.toPinyin(name);
                    contentValues.put("name", name);
                    contentValues.put("pinyin", pinyin);
                    contentValues.put("number", number);
                    contentValues.put("attribution", new QueryAttribution(number).getAttribution());
                    Cursor cursor = resolver.query(uri, new String[]{"birthday"}, "name = ?",
                            new String[]{name}, null);
                    contentValues.put("birthday", newbirthday.getText().toString());
                    while (cursor != null && cursor.moveToNext()) {
                        String day = cursor.getString(cursor.getColumnIndex("birthday"));
                        if (!day.isEmpty()) {
                            contentValues.put("birthday", day);
                            ContentValues values = new ContentValues();
                            values.put("birthday", day);
                            resolver.update(uri, values, "name = ?", new String[]{name});
                            break;
                        }
                    }
                    if (newwhitelist.isChecked())
                        contentValues.put("whitelist", 1);
                    else
                        contentValues.put("whitelist", 0);
                    resolver.insert(uri, contentValues);
                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    resolver.update(callRecordUri, values, "number = ?", new String[]{number});
                    this.finish();
                }
                else
                    Toast.makeText(this, "请输入电话号码！", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private boolean checkInfo() {
        if (newnumber.getText() == null || newnumber.getText().toString().isEmpty())
            return false;
        else
            return true;
    }
}
