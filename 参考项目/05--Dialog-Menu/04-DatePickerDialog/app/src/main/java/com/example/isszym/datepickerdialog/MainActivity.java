package com.example.isszym.datepickerdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button dateBn = (Button)findViewById(R.id.dateBn);
        Button timeBn = (Button)findViewById(R.id.timeBn);
        //为“设置日期”按钮绑定监听器
        dateBn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source)  {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {   // 绑定监听器
                            @Override
                            public void onDateSet(DatePicker dp, int year,
                                                  int month, int dayOfMonth) {
                                EditText show = (EditText) findViewById(R.id.show);
                                show.setText("您选择了：" + year + "年" + (month + 1)
                                        + "月" + dayOfMonth + "日");
                            }
                        }
                        //设置初始日期
                        , c.get(Calendar.YEAR)
                        , c.get(Calendar.MONTH)
                        , c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //为“设置时间”按钮绑定监听器
        timeBn.setOnClickListener(new OnClickListener()  {
            @Override
            public void onClick(View source)
            {
                Calendar c = Calendar.getInstance();
                // 创建一个TimePickerDialog实例，并把它显示出来
                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {  // 绑定监听器
                            @Override
                            public void onTimeSet(TimePicker tp, int hourOfDay,
                                                  int minute) {
                                EditText show = (EditText) findViewById(R.id.show);
                                show.setText("您选择了：" + hourOfDay + "时"
                                        + minute + "分");
                            }
                        }
                        //设置初始时间
                        , c.get(Calendar.HOUR_OF_DAY)
                        , c.get(Calendar.MINUTE)
                        //true表示采用24小时制
                        , true).show();
            }
        });
    }
}
