package com.immmus.bot.sample.handlers;

import com.immmus.standard.telegram.bot.factories.InlineKeyboard;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static com.immmus.bot.sample.handlers.actions.ActionInlineButtons.ACCEPT;
import static com.immmus.bot.sample.handlers.actions.ActionInlineButtons.CLOSE;

public class MessageHandler {

    public static Optional<BotApiMethod<?>> greeting(Update update) {
            final Long chatId = AbilityUtils.getChatId(update);
            return Optional.of(new SendMessage()
                    .enableHtml(true)
                    .setChatId(chatId)
                    .setText("<i><b>Hi! I am Bot. My name is immmus. Let's go play?)))</b></i>")
                    .setReplyMarkup(
                            new InlineKeyboard()
                                    .with(ACCEPT.button())
                                    .with(CLOSE.button())
                                    .markup()
            ));
    }
}