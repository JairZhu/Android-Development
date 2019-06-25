package com.example.phonebook;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class EditContactInfoActivity extends AppCompatActivity {
    private EditText newbirthday, newname;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.edit_contact_info_activity);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("编辑联系人");
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name"), birthday = bundle.getString("birthday");
        ArrayList<Map<String, Object>> lists = (ArrayList<Map<String, Object>>) bundle.getSerializable("numberList");
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
            case R.id.add_new_contact:
                this.finish();
                break;
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
