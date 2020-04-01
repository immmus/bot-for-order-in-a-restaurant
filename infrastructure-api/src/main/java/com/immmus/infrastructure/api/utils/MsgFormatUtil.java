package com.immmus.infrastructure.api.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_PROPERTIES;
import static java.util.ResourceBundle.Control.getNoFallbackControl;
import static java.util.ResourceBundle.getBundle;

public class MsgFormatUtil {

    public static String getLocalizedMessage(String messageCode, Object...arguments){
        return getLocalizedMessage(messageCode, null, arguments);
    }

    public static String getLocalizedMessage(String messageCode, Locale locale, Object...arguments) {
        ResourceBundle bundle;
        if (locale == null) {
            bundle = getBundle("messages", Locale.ROOT);
        } else {
            try {
                bundle = getBundle(
                        "messages",
                        locale,
                        getNoFallbackControl(FORMAT_PROPERTIES));
            } catch (MissingResourceException e) {
                bundle = getBundle("messages", Locale.ROOT);
            }
        }
        String message = bundle.getString(messageCode);
        return MessageFormat.format(message, arguments);
    }
}
