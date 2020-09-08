package com.immmus.standard.telegram.bot.handlers;

import org.jetbrains.annotations.NotNull;

public final class UpdateProcessesBuilder {
    private static TelegramBotUpdateProcessing updateProcessing;

    public static void setUpdateProcessing(@NotNull TelegramBotUpdateProcessing updateProcessing) {
        UpdateProcessesBuilder.updateProcessing = updateProcessing;
    }

    public static void clearUpdateProcessing() { UpdateProcessesBuilder.updateProcessing = null; }

    private static TelegramBotUpdateProcessing staticInstance() {
        if (updateProcessing == null) {
            throw new IllegalStateException("The static API can only be used within a updateProcesses() call.");
        }
        return updateProcessing;
    }

    public static TelegramBotUpdateProcessing callBackQuery(@NotNull String flag, @NotNull UpdateHandler handler) {
        return staticInstance().callBackQuery(flag, handler);
    }

    public static TelegramBotUpdateProcessing message(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return staticInstance().message(trigger, handler);
    }

    public TelegramBotUpdateProcessing inlineQuery(@NotNull String flag, @NotNull UpdateHandler handler) {
        return staticInstance().inlineQuery(flag, handler);
    }

    public static TelegramBotUpdateProcessing chosenInlineQuery(@NotNull String flag, @NotNull UpdateHandler handler) {
        return staticInstance().chosenInlineQuery(flag, handler);
    }
}