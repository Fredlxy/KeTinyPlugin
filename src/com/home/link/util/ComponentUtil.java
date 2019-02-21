package com.home.link.util;

import javax.swing.text.JTextComponent;

/**
 * Created by alvince on 17-6-29.
 *
 * @author alvince.zy@gmail.com
 * @version 1.0, 6/28/2017
 * @since 1.0
 */
public class ComponentUtil {

    public static String getInputText(JTextComponent textComponent) {
        return getInputText(textComponent, true);
    }

    public static String getInputText(JTextComponent textComponent, boolean trim) {
        if (textComponent == null) return StringUtil.EMPTY;

        String text = textComponent.getText();
        if (trim) text = text.trim();
        return StringUtil.isNotEmpty(text) ? text : StringUtil.EMPTY;
    }
}
