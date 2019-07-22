package com.example.spacebattle;

import android.content.Context;
import android.graphics.Canvas;

public class Buttons {
    Button[] arrButtons;

    Buttons(Context context) {
        arrButtons = new Button[4];
        String[] texts = {"开始", "开火", "自动", "关闭"};
        for (int i = 0; i < arrButtons.length; i++) {
            arrButtons[i] = new Button(context);
            arrButtons[i].text = texts[i];
        }
    }

    // 定位所有按钮
    void pos() {
        arrButtons[0].centerX = 135;
        arrButtons[1].centerX = 405;
        arrButtons[2].centerX = 675;
        arrButtons[3].centerX = 945;
        for (int i = 0; i < arrButtons.length; ++i) {
            arrButtons[i].centerY = 1840;
        }
    }

    // 绘制所有按钮
    void draw(Canvas canvas) {
        for (Button button : arrButtons) {
            button.draw(canvas);
        }
    }

    // 判断哪个按钮被点击
    String getPressedButton(float x, float y) {
        String str = null;
        for (Button button : arrButtons)
            if (button.getPressed(x, y))
                str = button.text;
        return str;
    }

}
