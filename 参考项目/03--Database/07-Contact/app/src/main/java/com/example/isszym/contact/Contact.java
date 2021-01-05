package com.example.isszym.contact;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by isszym on 2019/4/22.
 */

public class Contact {
    Context context;
    ContentResolver resolver;
    String TAG = "测试";
    Contact(Context context){
        this.context = context;
        resolver = context.getContentResolver();
    }

    // 直接添加联系人：先往raw_contacts中插入一个联系人，然后再往data中加入姓名和电话号码
    public void addContact(String name,String phone,String email){
        //插入raw_contacts表，并获取_id属性
        Uri uri1 = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentValues values = new ContentValues();
        Uri rawContactUri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        //Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;
        long rawContactId = ContentUris.parseId(rawContactUri);

        //插入data表
        Uri uri2 = Uri.parse("content://com.android.contacts/data");
        //add Name
        values.put("raw_contact_id", rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE,"vnd.android.cursor.item/name");
        values.put("data2", name);
        values.put("data1", name);
        resolver.insert(uri2, values);
        values.clear();
        //add Phone
        values.put("raw_contact_id", rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE,"vnd.android.cursor.item/phone_v2");
        values.put("data2", "2");
        values.put("data1", phone);
        resolver.insert(uri2, values);
        values.clear();
        //add email
        values.put("raw_contact_id", rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE,"vnd.android.cursor.item/email_v2");
        values.put("data2", "2");
        values.put("data1", email);
        resolver.insert(uri2, values);
    }

    public void updatePhoneNumber(int id,String phoneNumber)throws Exception{
        Uri uri = Uri.parse("content://com.android.contacts/data");   // 表data
        ContentValues values = new ContentValues();
        values.put("data1", phoneNumber);
        resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2",""+id});
    }

    public void deleteAllContacts()throws Exception {
        Uri uri1 = Uri.parse("content://com.android.contacts/raw_contacts");  // 表raw_contacts
        Uri uri2 = Uri.parse("content://com.android.contacts/data");
        resolver.delete(uri2, null, null);
        resolver.delete(uri1, null, null);
        //Cursor cursor = resolver.query(uri1, new String[]{ContactsContract.Contacts.Data._ID},null,
        //     null, null);
        //while(cursor.moveToNext()){
        //  int id = cursor.getInt(0);
        //    resolver.delete(uri1, "_id=", new String[]{""+id});
        //    resolver.delete(uri2, "raw_contact_id=?", new String[]{""+id});
        //}
    }

    public void deleteContact(String display_name)throws Exception{
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");  // 表raw_contacts

        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Contacts.Data._ID},
                                             "display_name=?",new String[]{display_name},null);
        if(cursor.moveToNext()){
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{display_name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{""+id});
        }
    }

    //查询指定电话的联系人姓名，邮箱
    public void queryNameByNumber(String phoneNumber) throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + phoneNumber);
        Cursor cursor = resolver.query(uri, new String[]{"display_name"}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            Log.i(TAG, name);
        }
        cursor.close();
    }

    //查询所有联系人的姓名，电话，邮箱
    public void queryAllContacts() throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
        while (cursor.moveToNext()) {
            int rawContactId = cursor.getInt(0);
            StringBuilder sb = new StringBuilder("contractID=");
            sb.append(rawContactId);
            uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor cursor1 = resolver.query(uri, new String[]{"mimetype", "data1", "data2"}, null, null, null);
            while (cursor1.moveToNext()) {
                String data1 = cursor1.getString(cursor1.getColumnIndex("data1"));
                String mimeType = cursor1.getString(cursor1.getColumnIndex("mimetype"));
                if ("vnd.android.cursor.item/name".equals(mimeType)) { //姓名
                    sb.append(",name=" + data1);
                } else if ("vnd.android.cursor.item/email_v2".equals(mimeType)) { //邮件
                    sb.append(",email=" + data1);
                } else if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) { //手机号
                    sb.append(",phone=" + data1);
                }
            }
            cursor1.close();
            Log.i(TAG, sb.toString());
        }
        cursor.close();
    }

    // 获得联系人个数
    public long getCount() throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
        long count = cursor.getCount();
        cursor.close();
        return count;
    }

    // 使用事务添加联系人
    public void addContactByTransaction(String name,String phoneNumber,String email) throws Exception {
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        // 用ContentProviderOperation批量增加联系人
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
        ContentProviderOperation op1 = ContentProviderOperation.newInsert(uri)
                .withValue("account_name", null)
                .build();
        operations.add(op1);

        uri = Uri.parse("content://com.android.contacts/data");
        ContentProviderOperation op2 = ContentProviderOperation.newInsert(uri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/name")
                .withValue("data2", name)
                .build();
        operations.add(op2);

        ContentProviderOperation op3 = ContentProviderOperation.newInsert(uri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                .withValue("data1", phoneNumber)
                .withValue("data2", "2")
                .build();
        operations.add(op3);

        ContentProviderOperation op4 = ContentProviderOperation.newInsert(uri)
                .withValueBackReference("raw_contact_id", 0)
                .withValue("mimetype", "vnd.android.cursor.item/email_v2")
                .withValue("data1", email)
                .withValue("data2", "2")
                .build();
        operations.add(op4);

        resolver.applyBatch("com.android.contacts", operations);
    }

    // 采用系统常量添加联系人
    public void insertContact(String familyName,String givenName,String phoneNumber) {
        //  ContactsContract.RawContacts.CONTENT_URI="content://com.android.contacts/raw_contacts"
        ContentValues values = new ContentValues();
        Uri rawContactUri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        //插入姓名：ContactsContract.Data.CONTENT_URI="content://com.android.contacts/data"
        //                  .StructuredName.CONTENT_ITEM_TYPE = "vnd.android.cursor.item/name"
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, givenName);    // .GIVEN_NAME="data2"
        values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, familyName);  // .FAMILY_NAME="data3"
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);

        //插入电话号码   .Phone.CONTENT_ITEM_TYPE="vnd.android.cursor.item/phone_v2"
        //               .Phone.TYPE_MOBILE = 2
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);             //  data1,-
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);//data2,2
        resolver.insert(ContactsContract.Data.CONTENT_URI, values);
    }

    //查询联系人
    public void showAllContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        // String[] projection = {ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._COUNT};
        // String selection = ContactsContract.Contacts.DISPLAY_NAME +"=?";
        //  String[] selectionArgs = {"aaa"};
        String sortOrder = ContactsContract.Contacts._ID;
        Cursor c = resolver.query(uri, null, null, null, sortOrder);
        while(c.moveToNext()) {
            String rawContactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            Log.i(TAG, "========== _ID="+rawContactId+" ==========");
            Log.i(TAG, "DISPLAY_NAME="+c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            int phoneCount = c.getInt(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            if(phoneCount>0) {
                Cursor phones = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = " + rawContactId, null, null);
                while(phones.moveToNext()){
                    Log.i(TAG, "NUMBER="+phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }

            }
        }
    }





}
