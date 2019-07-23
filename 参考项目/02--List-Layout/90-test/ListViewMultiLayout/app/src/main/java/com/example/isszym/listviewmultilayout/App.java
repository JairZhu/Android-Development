package com.example.isszym.listviewmultilayout;

/**
 * Created by Jay on 2015/9/22 0022.
 */
public class App {
    private int aIcon;
    private String aName;

    public App() {
    }

    public App(int aIcon, String aName) {
        this.aIcon = aIcon;
        this.aName = aName;
    }

    public int getaIcon() {
        return aIcon;
    }

    public String getaName() {
        return aName;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }
}
