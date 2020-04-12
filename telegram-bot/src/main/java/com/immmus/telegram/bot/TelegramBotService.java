package com.immmus.telegram.bot;

import com.immmus.telegram.bot.handlers.*;
import com.immmus.telegram.bot.utils.UpdateConditionsFlow;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static org.telegram.abilitybots.api.objects.Flag.*;

public abstract class TelegramBotService implements AutoCloseable {

    public abstract DefaultAbsSender getClient();
    public Optional<BotApiMethod<?>> updateProcess(Update update) {
        final var messageCondition = MESSAGE.or(EDITED_MESSAGE).or(CHANNEL_POST).or(EDITED_CHANNEL_POST);

        return UpdateConditionsFlow.of(update)
                .check(messageCondition).then(new MessageHandler(update))
                .orCheck(CALLBACK_QUERY).then(new CallbackQueryHandler(update))
                .orCheck(INLINE_QUERY).then(new InlineQueryHandler(update))
                .orCheck(CHOSEN_INLINE_QUERY).then(new ChosenInlineQueryHandler(update))
                .orElse(UpdateHandler.emptyAction());
    }
}
