package com.immmus.standard.telegram.bot.handlers;

import org.jetbrains.annotations.NotNull;

public interface HandlerBuilder<Builder> {
    //TODO Think about trigger type.
    Builder callBackQuery(@NotNull String trigger, @NotNull UpdateHandler handler);
    Builder message(@NotNull String trigger, @NotNull UpdateHandler handler);
    //TODO Think about trigger type.
    Builder inlineQuery(@NotNull String trigger, @NotNull UpdateHandler handler);
    //TODO Think about trigger type.
    Builder chosenInlineQuery(@NotNull String trigger, @NotNull UpdateHandler handler);
}
