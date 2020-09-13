package com.immmus.standard.telegram.bot.handlers;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface TelegramBotUpdateProcessing extends HandlerBuilder<TelegramBotUpdateProcessing> {
    default void addProcesses(@NotNull HandlersGroup handlersGroup) {
        UpdateProcesses.addUpdateProcesses(this);
        handlersGroup.addHandlers();
        UpdateProcesses.clearUpdateProcessing();
    }

    Optional<BotApiMethod<?>> update(Update update);
}