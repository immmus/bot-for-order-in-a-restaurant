package com.immmus.standard.telegram.bot.handlers;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public interface TelegramBotUpdateProcessing extends HandlerBuilder<TelegramBotUpdateProcessing> {
    default TelegramBotUpdateProcessing processes(@NotNull HandlersGroup handlersGroup) {
        UpdateProcessesBuilder.setUpdateProcessing(this);
        handlersGroup.addHandlers();
        UpdateProcessesBuilder.clearUpdateProcessing();
        return this;
    }

    Optional<BotApiMethod<?>> updateProcess(Update update);
}