package com.immmus.telegram.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class InlineQueryHandler implements UpdateHandler {
    public InlineQueryHandler(Update update) {
    }

    @Override
    public Optional<BotApiMethod<?>> execute() {
        return Optional.empty();
    }
}
