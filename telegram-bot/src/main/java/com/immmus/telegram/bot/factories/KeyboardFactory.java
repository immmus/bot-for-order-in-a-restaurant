package com.immmus.telegram.bot.factories;

import com.immmus.infrastructure.api.domain.Position;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KeyboardFactory {
    enum ButtonActions { CLOSE }
    public static List<InlineKeyboardButton> closeButton(){
        String name = ButtonActions.CLOSE.name();
        return List.of(new InlineKeyboardButton()
                .setText(name)
                .setCallbackData(name + "_BUTTON")
        );
    }

    public static ReplyKeyboard positionCategoryButtons() {
        return inlineOf(Arrays.stream(Position.Category.values())
                .map(category -> new InlineKeyboardButton()
                        .setText(category.name())
                        .setCallbackData(category.name()))
                .collect(Collectors.toList()), closeButton());
    }

    @SafeVarargs
    public static ReplyKeyboard inlineOf(List<InlineKeyboardButton>... buttons) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = List.of(buttons);
        return markupInline.setKeyboard(rowsInline);
    }
}
