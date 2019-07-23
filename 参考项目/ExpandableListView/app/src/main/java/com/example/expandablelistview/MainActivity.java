package com.example.expandablelistview;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] places = new String[] {"惠州候鸟", "深圳光明", "华凯乡村"};
    String[][] cards = new String[][] {{"迎奥卡", "候鸟都市精英卡"}, {"二十年卡", "十年卡"}, {"心悦会员卡", "专享会员卡"}};
    String[][] prices = new String[][] {{"29800元", "58000元"},{"13.8万元", "8.8万元"},{"35000元", "80000元"}};
    int[] images = {R.drawable.huizhouhouniao, R.drawable.shenzhenguangming, R.drawable.huakaixiangcun};
    ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ExpandableListView) findViewById(R.id.list_view);
        List<List<Item>> childdata = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            List<Item> ccdata = new ArrayList<>();
            for (int j = 0; j < 2; ++j) {
                Item tmp = new Item();
                tmp.card = cards[i][j];
                tmp.image = images[i];
                tmp.price = prices[i][j];
                ccdata.add(tmp);
            }
            childdata.add(ccdata);
        }
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, childdata, places);
        listView.setAdapter(adapter);
        listView.expandGroup(0);
        listView.expandGroup(1);
    }
}
