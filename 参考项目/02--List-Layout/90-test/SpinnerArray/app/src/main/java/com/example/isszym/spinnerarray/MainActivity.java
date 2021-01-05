package com.example.isszym.spinnerarray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList list = new ArrayList<String>();
        list.add("广州");   list.add("上海");     list.add("北京");
        list.add("深圳");   list.add("天津");     list.add("重庆");
       // String[] cities ={"上海","广州","北京"};
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1, cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        // android.R.layout.simple_list_item_1  或 R.layout.my_simple_list_item_1
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPadding(40, 40, 40, 40);
    }
}
