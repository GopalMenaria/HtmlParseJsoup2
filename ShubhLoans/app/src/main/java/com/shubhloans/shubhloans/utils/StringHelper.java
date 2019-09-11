package com.shubhloans.shubhloans.utils;

import com.shubhloans.shubhloans.model.Word;

import java.util.HashMap;

/**
 * Created by 310211834 on 11-09-2019.
 */

public class StringHelper {

    public static String getEveryNthCharacter(String str, int n, int range) {
        String result = "";
        for (int i = n - 1; i < range; i = i + n) {
            result += str.charAt(i);
        }
        return result;
    }

    /**
     *
     * @param str
     * @return map class with unique word with occurrences.
     */
    public static HashMap<Word, Integer> getUniqueWordCountFromStringArray(String[] str) {
        HashMap<Word, Integer> map = new HashMap<>();
        for (String aStr : str) {
            Word word = new Word(aStr);
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                map.put(word, map.get(word) + 1);
            }
        }
        return map;
    }


}
