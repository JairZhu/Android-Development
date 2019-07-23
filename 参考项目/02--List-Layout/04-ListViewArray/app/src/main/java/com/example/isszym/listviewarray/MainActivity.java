package com.example.isszym.listviewarray;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.isszym.listviewarray.R.layout.custom_simple_list_item_1;

public class MainActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 实现适配器，利用系统定义的样式，加载数据源
        ArrayList list = new ArrayList<String>();
        list.add("广州");   list.add("上海");     list.add("北京");
        list.add("深圳");   list.add("天津");     list.add("重庆");
        String list1[]={"广州","上海","北京","深圳","天津","重庆"};
        String[] list2 = getResources().getStringArray(R.array.city);
//      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                custom_simple_list_item_1,list2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,list2);
        // android.R.layout.simple_list_item_1
        // android.R.layout.simple_list_item_multiple_choice
        // android.R.layout.simple_list_item_single_choice
        // custom_simple_list_item_1
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
        //CHOICE_MODE_SINGLE,CHOICE_MODE_MULTIPLE,CHOICE_MODE_MULTIPLE_MODAL,CHOICE_MODE_NONE
         lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lv.setPadding(20, 0, 20, 0);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray arr = lv.getCheckedItemPositions();
                String selected = "";
                for (int i = 0; i < arr.size(); i++) {
                    if(arr.valueAt(i))  //int pos = arr.keyAt(i);
                       selected = selected + (selected.isEmpty() ? "" : ", ") + arr.keyAt(i);
                }
                Toast.makeText(MainActivity.this,
                        "第" + selected + "项被选择. 共" + lv.getCount() + "项", Toast.LENGTH_LONG).show();

            }
        });

    }
}
