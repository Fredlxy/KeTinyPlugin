package com.home.link.util;

import javax.annotation.Nullable;

/**
 * Created by alvince on 17-6-29.
 *
 * @author alvince.zy@gmail.com
 * @version 1.0, 6/28/2017
 * @since 1.0
 */
public class StringUtil {

    public static final String EMPTY = "";

    public static boolean isNotEmpty(@Nullable String text) {
        return text != null && text.length() > 0;
    }

    public static boolean isNotBlank(String text) {
        return isNotEmpty(text) && text.trim().length() > 0;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
}
