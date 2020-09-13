package com.immmus.standard.telegram.bot.handlers;

import org.jetbrains.annotations.NotNull;

public final class UpdateProcesses {
    private static TelegramBotUpdateProcessing updateProcessing;

    public static void addUpdateProcesses(@NotNull TelegramBotUpdateProcessing updateProcessing) {
        UpdateProcesses.updateProcessing = updateProcessing;
    }

    public static void clearUpdateProcessing() { UpdateProcesses.updateProcessing = null; }

    private static TelegramBotUpdateProcessing staticInstance() {
        if (updateProcessing == null) {
            throw new IllegalStateException("The static API can only be used within a updateProcesses() call.");
        }
        return updateProcessing;
    }

    public static TelegramBotUpdateProcessing callBackQuery(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return staticInstance().callBackQuery(trigger, handler);
    }

    public static TelegramBotUpdateProcessing message(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return staticInstance().message(trigger, handler);
    }

    public TelegramBotUpdateProcessing inlineQuery(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return staticInstance().inlineQuery(trigger, handler);
    }

    public static TelegramBotUpdateProcessing chosenInlineQuery(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return staticInstance().chosenInlineQuery(trigger, handler);
    }
}