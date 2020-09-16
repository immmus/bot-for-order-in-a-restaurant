package com.immmus.standard.telegram.bot.buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public abstract class InlineButton implements Button<InlineKeyboardButton> {
    private final InlineKeyboardButton button;

    public InlineButton(InlineKeyboardButton button) {
        this.button = button;
    }

    @Override
    public InlineKeyboardButton button() {
        return this.button;
    }

    public static final class UrlLink extends InlineButton {
        public UrlLink(String text, String url) {
            super(new InlineKeyboardButton(text).setUrl(url));
        }

        public UrlLink(String url) {
            this(url, url);
        }
    }

    public static final class CallBack extends InlineButton {
        public CallBack(String text, String callbackData) {
            super(new InlineKeyboardButton(text).setCallbackData(callbackData));
        }
    }
}