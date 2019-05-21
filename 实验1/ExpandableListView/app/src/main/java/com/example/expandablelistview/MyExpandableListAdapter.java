package com.example.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class Item {
    int image;
    String card;
    String price;
}

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    String[] mgroupstrings;
    Context mcontext;
    LayoutInflater minflater = null;
    List<List<Item>> mdata = null;

    private class GroupViewHolder { TextView mgroupname; }

    private class ChildViewHolder {
        ImageView micon;
        TextView mchildname;
        TextView mprice;
    }

    public MyExpandableListAdapter(Context context,  List<List<Item>> list, String[] places){
        mcontext = context;
        mdata = list;
        minflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mgroupstrings = places;
    }

    @Override
    public int getGroupCount() { return mdata.size(); }

    @Override
    public int getChildrenCount(int groupPosition) { return mdata.get(groupPosition).size(); }

    @Override
    public List<Item> getGroup(int groupPosition) { return mdata.get(groupPosition); }

    @Override
    public Item getChild(int groupPosition, int childPosition) { return mdata.get(groupPosition).get(childPosition); }

    @Override
    public long getGroupId(int groupPosition) { return groupPosition; }

    @Override
    public long getChildId(int groupPosition, int childPosition) { return childPosition; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    @Override
    public boolean hasStableIds() { return true; }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = minflater.inflate(R.layout.child, null);
        ChildViewHolder holder = new ChildViewHolder();
        holder.micon = (ImageView) convertView.findViewById(R.id.icon);
        holder.micon.setBackgroundResource(getChild(groupPosition, childPosition).image);
        holder.mchildname = (TextView) convertView.findViewById(R.id.card);
        holder.mchildname.setText(getChild(groupPosition, childPosition).card);
        holder.mprice = (TextView) convertView.findViewById(R.id.price);
        holder.mprice.setText(getChild(groupPosition, childPosition).price);
        return convertView;
    }

    public View getGroupView(int groupPosition, boolean IsExpanded,  View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = minflater.inflate(R.layout.group, null);
        GroupViewHolder holder = new GroupViewHolder();
        holder.mgroupname = (TextView) convertView.findViewById(R.id.place);
        holder.mgroupname.setText(mgroupstrings[groupPosition]);
        return convertView;
    }
}
