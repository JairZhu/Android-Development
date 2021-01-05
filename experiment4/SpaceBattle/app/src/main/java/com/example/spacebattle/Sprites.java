package com.example.spacebattle;

import android.content.Context;
import android.graphics.Canvas;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Sprites {
    Context context;
    ConcurrentHashMap<String, Sprite> hmSprites;    // 所有精灵(与HashMap用法相同，用于多线程环境)
    String myName;                               // 本精灵的名称
    Sprite mySprite;                            // 本精灵（用于区分其他精灵）

    Sprites(Context context, String myName) {
        this.context = context;
        hmSprites = new ConcurrentHashMap<String, Sprite>();
        this.myName = myName;
    }

    void add(String spName, float x, float y, float dir, float step, boolean hit, boolean active) {
    }

    void draw(Canvas canvas, long intl) {
        Iterator<String> it = hmSprites.keySet().iterator();
        while (it.hasNext()) {
            Sprite sprite = hmSprites.get(it.next());
            if (sprite.active) {
                sprite.draw(canvas, intl);
            }
        }
    }

    Sprite remove(String spName) {
        return hmSprites.remove(spName);
    }
}
