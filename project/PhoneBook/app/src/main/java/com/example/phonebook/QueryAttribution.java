package com.example.phonebook;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class QueryAttribution {
    private String attribution = "";

    public String getAttribution(final String phoneNumber) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + phoneNumber);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                        stringBuilder.append(line);
                    String str = stringBuilder.toString();
                    int start = str.indexOf("carrier:'");
                    int end = str.lastIndexOf('\'');
                    if (start != -1 && end != -1)
                        attribution = str.substring(start + 9, end);
                    else
                        attribution = "未知号码";
                    Log.v("getAttribution", attribution);
                } catch (Exception e) {
                    e.printStackTrace();
                    attribution = "未知号码";
                }
            }
        });
        thread.start();
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return attribution;
    }
}
