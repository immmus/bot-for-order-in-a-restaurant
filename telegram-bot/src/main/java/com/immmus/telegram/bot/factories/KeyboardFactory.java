package com.immmus.telegram.bot.factories;

import com.immmus.infrastructure.api.domain.Position;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KeyboardFactory {
    public static ReplyKeyboard positionCategoryButtons() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = List.of(
                Arrays.stream(Position.Category.values())
                        .map(category -> new InlineKeyboardButton()
                                .setText(category.name())
                                .setCallbackData(category.name()))
                        .collect(Collectors.toList())
        );
        return markupInline.setKeyboard(rowsInline);
    }
}
