package com.example.phonebook;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;


class CustomPhoneStateListener extends PhoneStateListener {
    // 重写电话状态改变时触发的方法
    Context context;
    Cursor cursor;
    int checked = 0;
    int judge = 0;
    int beginTime = 0;
    int endTime = 0;
    private Uri callRecordUri = Uri.parse("content://com.example.providers.RecordDB/");
    private Uri contactUri = Uri.parse("content://com.example.providers.ContactDB/");
    private ContentResolver resolver;
    private static final String TAG = "MyPhoneCallListener";
    /**
     * 返回电话状态
     *
     * CALL_STATE_IDLE 无任何状态时
     * CALL_STATE_OFFHOOK 接起电话时
     * CALL_STATE_RINGING 电话进来时
     */
    CustomPhoneStateListener(Context context,ContentResolver resolver){
        this.context = context;
        this.resolver = resolver;
    }

    public void setChecked(int i){
        checked = i;
    }

    public int getJudge(){
        return judge;
    }

    public void setJudge(int i){
        judge = i;
    }

    public void setBeginTime(int i){beginTime = i;}

    public void setEndTime(int i){endTime = i;}

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:// 电话挂断
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: //电话通话的状态
                judge = 1;
                break;
            case TelephonyManager.CALL_STATE_RINGING: //电话响铃的状态
                judge = 1;
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                int time = Integer.parseInt(String.valueOf(cal.get(Calendar.HOUR)) + String.valueOf((cal.get(Calendar.MINUTE))));
                if(checked == 0 || time < beginTime || time > endTime){
                    break;
                }
                cursor = resolver.query(contactUri, new String[]{"number","whitelist"},"number = ?",new String[]{incomingNumber},null);
                if(cursor != null && cursor.getCount()!= 0){
                    cursor.moveToNext();
                    int judge = cursor.getInt(cursor.getColumnIndex("whitelist"));
                    if(judge == 0){
                        try {
                            // 延迟5秒后自动挂断电话
                            // 首先拿到TelephonyManager
                            TelephonyManager telMag = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            Class<TelephonyManager> c = TelephonyManager.class;

                            // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
                            Method mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
                            //允许访问私有方法
                            mthEndCall.setAccessible(true);
                            Object obj = mthEndCall.invoke(telMag, (Object[]) null);

                            // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
                            Method mt = obj.getClass().getMethod("endCall");
                            //允许访问私有方法
                            mt.setAccessible(true);
                            mt.invoke(obj);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    try {
                        // 延迟5秒后自动挂断电话
                        // 首先拿到TelephonyManager
                        TelephonyManager telMag = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        Class<TelephonyManager> c = TelephonyManager.class;

                        // 再去反射TelephonyManager里面的私有方法 getITelephony 得到 ITelephony对象
                        Method mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
                        //允许访问私有方法
                        mthEndCall.setAccessible(true);
                        Object obj = mthEndCall.invoke(telMag, (Object[]) null);

                        // 再通过ITelephony对象去反射里面的endCall方法，挂断电话
                        Method mt = obj.getClass().getMethod("endCall");
                        //允许访问私有方法
                        mt.setAccessible(true);
                        mt.invoke(obj);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
        super.onCallStateChanged(state, incomingNumber);
    }
}
