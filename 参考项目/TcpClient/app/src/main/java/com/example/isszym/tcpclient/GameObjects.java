package com.example.isszym.tcpclient;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by isszym on 2019/7/16.
 */

public class GameObjects {
    Context context;
    Car car;
    GameObjects(Context context){
        this.context = context;
        car = new Car();
        car.name = "mars1234";
        car.speed = 12;
        car.good = true;
    }
    void handleRecvData(String data) {
        JSONObject obj = JsonFunc.parserJSON2Object(data);
        if(obj==null) return;
        String type = obj.optString("type");
        if (type.equals("carinfo")) {
            Car car = new Car();
            car.name = obj.optString("name");
            car.speed = obj.optInt("speed");
            car.good = obj.optBoolean("good");
            System.out.println("收到数据:"+car);
        }
        else {
            System.out.println("收到数据的类型错误！");
        }

    }
}
