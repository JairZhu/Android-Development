package com.example.gridviewsimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    int[] images = {R.drawable.cwt, R.drawable.dlrb, R.drawable.lh, R.drawable.wjk, R.drawable.ym, R.drawable.zly, R.drawable.zyx};
    String[] names = {"陈伟霆", "迪丽热巴", "鹿晗", "王俊凯", "杨幂", "赵丽颖", "张艺兴"};
    int[] ages = {18, 21, 22, 32, 31, 18, 20};
    ArrayList list = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < names.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", images[i]);
            map.put("name", names[i]);
            map.put("age", ages[i]);
            list.add(map);
        }

        gridView = (GridView) findViewById(R.id.gridview);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.picture_item, new String[]{"icon", "name", "age"}, new int[]{R.id.icon, R.id.name, R.id.age});
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String output = list.get(i).toString();
                output = "Short Click: " + output.substring(output.indexOf('=') + 1, output.indexOf(','));
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String output = list.get(i).toString();
                output = "Long Click: " + output.substring(output.indexOf('=') + 1, output.indexOf(','));
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
