package com.immmus.telegram.bot.handlers;

import com.immmus.telegram.bot.factories.KeyboardFactory;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class CallbackQueryHandler implements UpdateHandler {
    private final Update update;

    public CallbackQueryHandler(Update update) {
        this.update = update;
    }

    @Override
    public Optional<BotApiMethod<?>> execute() {
        final var callData = update.getCallbackQuery();
        final Long chatId = AbilityUtils.getChatId(update);
        if (callData.getData().equalsIgnoreCase(KeyboardFactory.ButtonActions.CLOSE.callbackData)) {
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            DeleteMessage message = new DeleteMessage();
            message.setChatId(chatId);
            message.setMessageId(messageId);
            return Optional.of(message);
        } else {
//            final Position.Category category = Position.Category.valueOf(callData.getData());
//            var format = menuService.getMenuContextService().defaultFormat(MenuContextService.Language.RUSSIAN);
//            //TODO попробовать переписать подобный функционал на Spring Webflux or P Reactor
//            Optional.ofNullable(menuService.getMenuContextService().positionsToString(category, format))
//                    .ifPresentOrElse(text -> bot.send(text, chatId, inlineOf(closeButton())),
//                            () -> bot.send("К сожаление позиции данной категории отсутствуют.", chatId, inlineOf(closeButton())));
        }
        return Optional.empty();
    }
}