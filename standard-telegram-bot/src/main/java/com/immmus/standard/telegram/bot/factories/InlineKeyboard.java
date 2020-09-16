package com.immmus.standard.telegram.bot.factories;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public final class InlineKeyboard {
    private final InlineKeyboardMarkup markupInline;

    public InlineKeyboard(InlineKeyboardMarkup markupInline) {
        this.markupInline = markupInline;
    }

    public InlineKeyboard() {
        this(new InlineKeyboardMarkup());
    }

    public InlineKeyboard with(InlineKeyboardButton... buttons) {
        this.markupInline.getKeyboard().add(List.of(buttons));
        return this;
    }

    public InlineKeyboardMarkup markup() {
        return this.markupInline;
    }
}