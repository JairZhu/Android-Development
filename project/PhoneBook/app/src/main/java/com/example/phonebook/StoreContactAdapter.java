package com.example.phonebook;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StoreContactAdapter extends RecyclerView.Adapter<StoreContactAdapter.ContactsViewHolder>  {
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private List<Contact> contacts;
    private int layoutId;
    private Context context;
    private String number, attribution;
    private ContentResolver resolver;

    public StoreContactAdapter(Context context, List<Contact> contacts, int layoutId, String number, String attribution) {
        this.contacts = contacts;
        this.layoutId = layoutId;
        this.context = context;
        this.number = number;
        this.attribution = attribution;
        this.resolver = context.getContentResolver();
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view.findViewById(R.id.contact_name);
                String birthday = "", name = textView.getText().toString();
                Cursor cursor = resolver.query(contactUri, new String[]{"birthday"}, "name = ?", new String[]{name}, null);
                while (cursor != null && cursor.moveToNext()) {
                    birthday = cursor.getString(cursor.getColumnIndex("birthday"));
                    if (birthday != null && !birthday.isEmpty())
                        break;
                }
                ContentValues values = new ContentValues();
                values.put("number", number);
                values.put("name", name);
                values.put("attribution", attribution);
                values.put("birthday", birthday);
                resolver.insert(contactUri, values);
                Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
            }
        });
        ContactsViewHolder holder = new ContactsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        if (position == 0 || !contacts.get(position-1).getIndex().equals(contact.getIndex())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getIndex());
        }
        else {
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIndex;
        public ImageView ivAvatar;
        public TextView tvName;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.sort_key);
            ivAvatar = (ImageView) itemView.findViewById(R.id.contact_image);
            tvName = (TextView) itemView.findViewById(R.id.contact_name);
        }
    }
}
