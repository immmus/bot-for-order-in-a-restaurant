package com.immmus.bot.sample.handlers.actions;

import com.immmus.standard.telegram.bot.buttons.InlineButton;
import com.immmus.standard.telegram.bot.factories.InlineKeyboard;
import com.immmus.standard.telegram.bot.handlers.UpdateHandler;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Optional;

public enum ActionInlineButtons {
    CLOSE("CLOSE", "CLOSE_BUTTON",
            (update) -> {
                final Long chatId = AbilityUtils.getChatId(update);
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                return Optional.of(new DeleteMessage(chatId, messageId));
            }),
    ACCEPT("ACCEPT", "ACCEPT_BUTTON",
            (update) -> {
                final Long chatId = AbilityUtils.getChatId(update);
                return Optional.of(new SendMessage()
                        .enableHtml(true)
                        .setChatId(chatId)
                        .setText("<i><b>Well!!!</b></i>")
                        .setReplyMarkup(
                                new InlineKeyboard()
                                        .with(new InlineButton.UrlLink(
                                                "Go to my git.",
                                                "https://github.com/immmus").button())
                                        .markup()
                        )
                );
            }
    );

    public final String callbackData;
    public final String name;
    private final UpdateHandler action;

    ActionInlineButtons(String name, String callbackData, UpdateHandler action) {
        this.name = name;
        this.callbackData = callbackData;
        this.action = action;
    }

    public InlineKeyboardButton button() {
        return new InlineButton.CallBack(this.name, this.callbackData).button();
    }

    public UpdateHandler action() {
        return this.action;
    }
}
