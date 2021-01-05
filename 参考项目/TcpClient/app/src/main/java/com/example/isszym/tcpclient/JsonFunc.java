package com.example.isszym.tcpclient;

import org.json.JSONObject;

/**
 * Created by isszym on 2018/7/28.
 */

public class JsonFunc {
    public static String car2JSON(Car car) {
        JSONObject obj = new JSONObject();
        if(obj==null) return "";
        try {
            obj.put("type", "carinfo");
            obj.put("name", car.name);
            obj.put("speed", car.speed);
            obj.put("good", car.good);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj.toString();
    }

    public static JSONObject parserJSON2Object(String str) {
        JSONObject obj = null;
        try {
            //System.out.println(str);
            obj = new JSONObject(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return obj;
    }

}
