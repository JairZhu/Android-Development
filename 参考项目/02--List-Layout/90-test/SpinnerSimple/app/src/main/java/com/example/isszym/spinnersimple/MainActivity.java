package com.example.isszym.spinnersimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        int[] images = { R.drawable.cwt, R.drawable.dlrb,
                R.drawable.lh, R.drawable.wjk, R.drawable.ym,
                R.drawable.zly, R.drawable.zyx};
        String[] names = { "陈伟霆", "迪丽热巴", "鹿晗",
                           "王俊凯", "杨幂", "赵丽颖", "张艺兴" };
        int[] ages = { 18, 21, 22, 32, 31, 18, 20, 25 };
        ArrayList<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", images[i]);
            map.put("name", names[i]);
            map.put("age", ages[i]);

            list.add(map);

        }
        /** 参数：context(上下文对象), datasource(数据源), itemlayout(每个Item的布局页面),
         *         from String[] 数据源中key的数组, to int[] 布局页面中id的数组  **/
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.spinner_item, new String[] { "icon", "name", "age" },
                new int[] { R.id.icon, R.id.name, R.id.age });

        spinner.setAdapter(adapter);
    }
}
