package com.example.grandwordremember;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestAdapter extends BaseAdapter {
    private ArrayList<WordRec> lists;
    private Context context;
    private SparseIntArray checked = new SparseIntArray();
    LayoutInflater inflater;
    Map<String, String> choose = new HashMap<>();
    boolean show_answer = false;
    String color = "Black";
    Map<Object, ArrayList<Object>> maps = new HashMap<>();

    public TestAdapter(ArrayList<WordRec> lists, Context context) {
        super();
        this.lists = lists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() { return lists.size(); }
    @Override
    public Object getItem(int position) { return lists.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    public class ViewHolder{
        TextView textView;
        RadioGroup radioGroup;
        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.word);
            holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.radio_group);
            holder.radioButton1 = (RadioButton) convertView.findViewById(R.id.radio_button1);
            holder.radioButton2 = (RadioButton) convertView.findViewById(R.id.radio_button2);
            holder.radioButton3 = (RadioButton) convertView.findViewById(R.id.radio_button3);
            holder.radioButton4 = (RadioButton) convertView.findViewById(R.id.radio_button4);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(lists.get(position).getWord());
        switch (color) {
            case "Black":
                holder.textView.setTextColor(Color.BLACK);
                break;
            case "Red":
                holder.textView.setTextColor(Color.RED);
                break;
            case "Blue":
                holder.textView.setTextColor(Color.BLUE);
                break;
            case "Green":
                holder.textView.setTextColor(Color.GREEN);
                break;
        }
        ArrayList<Object> list = new ArrayList<>();
        if (!maps.containsKey(position)) {
            list.add(position);
            for (int i = 0; i < 3; ++i) {
                int num = position;
                while (list.contains(num)) num = (int) (Math.random() * lists.size());
                list.add(num);
            }
            Collections.shuffle(list);
            maps.put(position, list);
        }

        else
            list = maps.get(position);
        holder.radioButton1.setText(lists.get((int)list.get(0)).getExplanation());
        holder.radioButton2.setText(lists.get((int)list.get(1)).getExplanation());
        holder.radioButton3.setText(lists.get((int)list.get(2)).getExplanation());
        holder.radioButton4.setText(lists.get((int)list.get(3)).getExplanation());
        holder.radioButton1.setBackgroundColor(Color.WHITE);
        holder.radioButton2.setBackgroundColor(Color.WHITE);
        holder.radioButton3.setBackgroundColor(Color.WHITE);
        holder.radioButton4.setBackgroundColor(Color.WHITE);
        if (show_answer)
            switch (list.indexOf(position)) {
                case 0:
                    holder.radioButton1.setBackgroundColor(Color.rgb(240, 240, 240));
                    break;
                case 1:
                    holder.radioButton2.setBackgroundColor(Color.rgb(240, 240, 240));
                    break;
                case 2:
                    holder.radioButton3.setBackgroundColor(Color.rgb(240, 240, 240));
                    break;
                case 3:
                    holder.radioButton4.setBackgroundColor(Color.rgb(240, 240, 240));
                    break;
            }
        holder.radioGroup.setOnCheckedChangeListener(null);
        holder.radioGroup.clearCheck();
        if (checked.indexOfKey(position) > -1)
            holder.radioGroup.check(checked.get(position));
        else
            holder.radioGroup.clearCheck();
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i > -1) {
                    checked.put(position, i);
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                    choose.put(holder.textView.getText().toString(), radioButton.getText().toString());
                }
                else if (checked.indexOfKey(position) > -1)
                    checked.removeAt(checked.indexOfKey(position));
            }
        });
        return convertView;
    }
}
