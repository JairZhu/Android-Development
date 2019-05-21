package com.example.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class testarrayadapter extends ArrayAdapter<String> {
    Context mcontext;
    String mstringarray[];

    public testarrayadapter(Context context, String[] stringarray) {
        super(context, android.R.layout.simple_spinner_item, stringarray);
        mcontext = context;
        mstringarray = stringarray;
    }

    @Override
    public View getDropDownView(int position, View convertview, ViewGroup parent) {
        if (convertview == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertview = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView tv = (TextView) convertview.findViewById(android.R.id.text1);
        tv.setText(mstringarray[position]);
        tv.setTextSize(22f);
        return convertview;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        if (convertview == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertview = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        TextView tv = (TextView) convertview.findViewById(android.R.id.text1);
        tv.setText(mstringarray[position]);
        tv.setTextSize(25f);
        return convertview;
    }
}
