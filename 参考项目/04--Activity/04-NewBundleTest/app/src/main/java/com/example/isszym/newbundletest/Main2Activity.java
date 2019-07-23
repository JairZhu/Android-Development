package com.example.isszym.newbundletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView name = (TextView) findViewById(R.id.name);
        TextView passwd = (TextView) findViewById(R.id.passwd);
        TextView gender = (TextView) findViewById(R.id.gender);
        // 获取启动该Result的Intent
        Intent intent = getIntent();
        // 直接通过Intent取出它所携带的Bundle数据包中的数据
        Person p = (Person) intent.getSerializableExtra("person");
        name.setText("您的用户名为：" + p.getName());
        passwd.setText("您的密码为：" + p.getPass());
        gender.setText("您的性别为：" + p.getGender());
    }
}
