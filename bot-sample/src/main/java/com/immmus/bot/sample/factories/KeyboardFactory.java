package com.immmus.bot.sample.factories;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.immmus.bot.sample.factories.KeyboardFactory.ButtonActions.CLOSE;

public class KeyboardFactory {
   public enum ButtonActions {
        CLOSE("CLOSE", "CLOSE_BUTTON");

        public final String callbackData;
        public final String name;

        ButtonActions(String name, String callbackData) {
            this.name = name;
            this.callbackData = callbackData;
        }
    }

    public static List<InlineKeyboardButton> closeButton(){
        return List.of(new InlineKeyboardButton()
                .setText(CLOSE.name)
                .setCallbackData(CLOSE.callbackData)
        );
    }

    @SafeVarargs
    public static ReplyKeyboard inlineOf(List<InlineKeyboardButton>... buttons) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = List.of(buttons);
        return markupInline.setKeyboard(rowsInline);
    }
}
