package com.delose.minesweeper.core.util.config;

import java.util.ResourceBundle;

public class MessageProvider {
    private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

    private MessageProvider() {
        throw new IllegalStateException("MessageProvider class");
      }
    public static String getMessage(String key) {
        return messages.getString(key);
    }
}