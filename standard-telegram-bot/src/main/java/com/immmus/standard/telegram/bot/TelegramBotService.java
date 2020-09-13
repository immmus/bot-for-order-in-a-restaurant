package com.immmus.standard.telegram.bot;

import com.immmus.standard.telegram.bot.handlers.UpdateHandler;
import com.immmus.standard.telegram.bot.handlers.TelegramBotUpdateProcessing;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.*;

@Slf4j
public abstract class TelegramBotService<Client extends DefaultAbsSender> implements TelegramBotUpdateProcessing, AutoCloseable {
    private final ConcurrentHashMap<String, UpdateHandler> messageUpdateHandlers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UpdateHandler> callbackQueryHandlers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UpdateHandler> inlineQueryHandlers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, UpdateHandler> chosenInlineQueryHandlers = new ConcurrentHashMap<>();

    public abstract Client client();

    @Override
    public TelegramBotUpdateProcessing callBackQuery(@NotNull String trigger, @NotNull UpdateHandler handler) {
        if (null != callbackQueryHandlers.putIfAbsent(trigger, handler)) {
            log.error("This callbackQuery handler '{}' already is exist.", trigger);
        }
        return this;
    }

    @Override
    public TelegramBotUpdateProcessing message(@NotNull String trigger, @NotNull UpdateHandler handler) {
        if (null != messageUpdateHandlers.putIfAbsent(trigger, handler)) {
            log.error("This message handler '{}' already is exist.", trigger);
        }
        return this;
    }

    @Override
    public TelegramBotUpdateProcessing inlineQuery(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return this;
    }

    @Override
    public TelegramBotUpdateProcessing chosenInlineQuery(@NotNull String trigger, @NotNull UpdateHandler handler) {
        return this;
    }

    static final Predicate<Update> MESSAGE_CONDITION =
            MESSAGE.or(EDITED_MESSAGE).or(CHANNEL_POST).or(EDITED_CHANNEL_POST);
    @Override
    public Optional<BotApiMethod<?>> update(Update update) {
        if (MESSAGE_CONDITION.test(update)) {
            return messageUpdateHandlers
                    .get(update.getMessage().getText())
                    .execute(update);
        } else if (CALLBACK_QUERY.test(update)) {
            return callbackQueryHandlers
                    .get(update.getCallbackQuery().getData())
                    .execute(update);
        } else if (INLINE_QUERY.test(update)) {
            return inlineQueryHandlers
                    .get(update.getInlineQuery().getQuery())
                    .execute(update);
        } else if (CHOSEN_INLINE_QUERY.test(update)) {
            return chosenInlineQueryHandlers
                    .get(update.getChosenInlineQuery().getQuery())
                    .execute(update);
        } else {
            return Optional.empty();
        }
    }
}