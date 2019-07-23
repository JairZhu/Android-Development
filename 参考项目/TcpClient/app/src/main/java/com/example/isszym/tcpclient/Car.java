package com.example.isszym.tcpclient;

/**
 * Created by isszym on 2019/7/16.
 */

public class Car {
    String name;
    int speed;
    boolean good;
    @Override
    public String toString(){
        return "name=" + name + ",speed=" + speed + ",good=" + (good?"true":"false");
    }
}
