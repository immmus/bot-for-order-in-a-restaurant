package com.immmus.telegram.bot.handlers;

import com.immmus.telegram.bot.factories.KeyboardFactory;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class MessageHandler implements UpdateHandler {
    private final Update update;

    public MessageHandler(Update update) {
        this.update = update;
    }

    @Override
    public Optional<BotApiMethod<?>> execute() {
        final Long chatId = AbilityUtils.getChatId(update);
        if (update.getMessage().getText().equals("/start")) {
            return Optional.of(new SendMessage()
                    .enableHtml(true)
                    .setChatId(chatId)
                    .setText("<i><b>Выберете катеригори меню.</b></i>")
                    .setReplyMarkup(KeyboardFactory.positionCategoryButtons())
            );
        }
        return Optional.empty();
    }
}
