package com.example.phonebook;

import com.github.promeg.pinyinhelper.Pinyin;

public class CharacterToPinyin {
    public static String toPinyin(String str) {
        return Pinyin.toPinyin(str, "");
    }

    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); ++i)
            if (Pinyin.isChinese(str.charAt(i)))
                return true;
        return false;
    }
}
