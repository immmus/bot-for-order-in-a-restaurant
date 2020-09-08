package com.immmus.bot.sample.handlers;

import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class CallbackQueryHandler {

    public static Optional<BotApiMethod<?>> closeButton(Update update) {
        final Long chatId = AbilityUtils.getChatId(update);
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        DeleteMessage message = new DeleteMessage();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        return Optional.of(message);
    }
}