package com.example.phonebook;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
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

import java.util.Calendar;

public class AddContactActivity extends AppCompatActivity {
    private Uri uri = Uri.parse("content://com.example.providers.ContactDB/");
    private EditText newname, newnumber, newattribution, newbirthday;
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
        newattribution = (EditText) findViewById(R.id.new_attribution);
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
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", newname.getText().toString());
                contentValues.put("number", newnumber.getText().toString());
                contentValues.put("attribution", newattribution.getText().toString());
                contentValues.put("birthday", newbirthday.getText().toString());
                if (newwhitelist.isChecked())
                    contentValues.put("whitelist", 1);
                else
                    contentValues.put("whitelist", 0);
                resolver.insert(uri, contentValues);
                this.finish();
                break;
        }
        return true;
    }
}
