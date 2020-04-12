package com.immmus.telegram.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.Optional;

public interface UpdateHandler {

    Optional<BotApiMethod<?>> execute();
    static UpdateHandler emptyAction() {
        return new EmptyAction();
    }

    class EmptyAction implements UpdateHandler {

        @Override
        public Optional<BotApiMethod<?>> execute() {
            return Optional.empty();
        }
    }
}
