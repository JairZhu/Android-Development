package com.example.isszym.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ContactTest {
    static long ind = 0;
    private static final String TAG = "测试";   // 准备好TAG标识用于LOG输出
    Uri uri;
    Contact contact;
    @Before
    public void createUri() throws Throwable {
        Context appContext = InstrumentationRegistry.getTargetContext();
        uri = Uri.parse("content://com.android.contacts/contacts");
        contact = new Contact(appContext);
        ind = contact.getCount();
    }

    @Test
    public void testBatchInsert() throws Throwable {
        int count = 10;
        for(int i= 0;i<count;i++){
            testInsert();
        }
    }
        @Test
    public void testInsert() throws Throwable {
        ind++;
        contact.addContact("136427830"+ind,"王"+ind,"w"+ind+"@123.com");
        Log.d(TAG, "插入成功---136427830"+ind);
    }

    @Test
    public void testUpdate() throws Throwable {
        contact.updatePhoneNumber(10,"13600010005");
        Log.d(TAG, "修改成功");
    }

    @Test
    public void testDelete() throws Throwable {
        contact.deleteContact("王29");
        Log.d(TAG, "删除成功");
    }

    @Test
    public void testDeleteAll() throws Throwable {
        contact.deleteAllContacts();
        Log.d(TAG, "删除成功");
    }

    @Test
    public void testGetCount() throws Throwable {
       long count = contact.getCount();
       Log.d(TAG, "总记录数："+count);
    }

    @Test
    public void testQuery() throws Throwable {
        contact.queryAllContacts();
    }
}
