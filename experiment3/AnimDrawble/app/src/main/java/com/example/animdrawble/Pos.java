package com.example.animdrawble;

public class Pos {
    private float x, y;
    private boolean toRight;
    public Pos(float x, float y, boolean toRight) {
        this.x = x;
        this.y = y;
        this.toRight = toRight;
    }
    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isToRight() { return toRight; }
}
