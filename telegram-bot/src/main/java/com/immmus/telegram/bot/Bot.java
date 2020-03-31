package com.immmus.telegram.bot;

import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.service.ChatContext;
import com.immmus.telegram.bot.factories.KeyboardFactory;
import com.immmus.telegram.bot.factories.KeyboardFactory.ButtonActions;
import com.immmus.telegram.bot.repository.MenuPositionRepository;
import com.immmus.telegram.bot.service.TChatContext;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import javax.annotation.Nullable;
import java.util.Optional;

import static com.immmus.telegram.bot.factories.KeyboardFactory.closeButton;
import static com.immmus.telegram.bot.factories.KeyboardFactory.inlineOf;
import static org.telegram.abilitybots.api.objects.Flag.CALLBACK_QUERY;

public class Bot extends TelegramLongPollingBot {
    private String name;
    private String token;
    protected MenuPositionRepository repository;
    protected MessageSender messageSender;
    protected SilentSender sender;

    public Bot(DefaultBotOptions options, MenuPositionRepository repository, String name, String token) {
        super(options);
        this.name = name;
        this.token = token;
        this.messageSender = new DefaultSender(this);
        this.sender = new SilentSender(messageSender);
        this.repository = repository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        //TODO реализовать кэшируемость меню
        final Long chatId = AbilityUtils.getChatId(update);
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/start")) {
                send("<i><b>Выберете катеригори меню.</b></i>",
                        chatId,
                        KeyboardFactory.positionCategoryButtons());
            }
        } else if (CALLBACK_QUERY.test(update)) {
            String callData = update.getCallbackQuery().getData();
            if (callData.equalsIgnoreCase(ButtonActions.CLOSE.callbackData)) {
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                DeleteMessage message = new DeleteMessage();
                message.setChatId(chatId);
                message.setMessageId(messageId);
                sender.execute(message);
            } else {
                final TChatContext context = ChatContext.createContext(repository.getMenu(), TChatContext.class);
                final Position.Category category = Position.Category.valueOf(callData);
                Optional.ofNullable(context.positionsToString(category))
                        .ifPresentOrElse(
                                text -> send(text, chatId, inlineOf(closeButton())),
                                () -> send("К сожаление позиции данной категории отсутствуют.", chatId, inlineOf(closeButton())));
            }
        }
    }

    void send(String text, Long chatId) {
        send(text, chatId, null);
    }

    void send(String text, Long chatId, @Nullable ReplyKeyboard keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableHtml(true);
        if (keyboard != null) message.setReplyMarkup(keyboard);

        sender.execute(message);
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
