package com.immmus.standard.telegram.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@FunctionalInterface
public interface UpdateHandler {
    Optional<BotApiMethod<?>> execute(Update update);

    static UpdateHandler emptyAction() {
        return new EmptyActionHandler();
    }

    class EmptyActionHandler implements UpdateHandler {

        @Override
        public Optional<BotApiMethod<?>> execute(Update update) {
            return Optional.empty();
        }
    }
}