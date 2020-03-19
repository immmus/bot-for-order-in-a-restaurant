package com.immmus.telegram.bot;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.repository.MenuPosition;
import com.immmus.infrastructure.api.service.ChatContext;
import com.immmus.telegram.bot.dto.TMenu;
import com.immmus.telegram.bot.factories.KeyboardFactory;
import com.immmus.telegram.bot.repository.MenuPositionRepository;
import com.immmus.telegram.bot.service.Sender;
import com.immmus.telegram.bot.service.TChatContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

import static org.telegram.abilitybots.api.objects.Flag.CALLBACK_QUERY;

public class Bot extends TelegramLongPollingBot {
    private String name;
    private String token;
    protected MessageSender messageSender;
    protected Sender sender;

    public Bot(String name, String token) {
        super();
        this.name = name;
        this.token = token;
    }

    public Bot(DefaultBotOptions options, String name, String token) {
        super(options);
        this.name = name;
        this.token = token;
        this.messageSender = new DefaultSender(this);
        this.sender = new Sender(messageSender);
    }

    @Override
    public void onUpdateReceived(Update update) {
        final Position soup = MenuPosition.builder()
                .name("Борщ")
                .price(21.50)
                .composition("картофель", "свекла", "говядина", "сметана")
                .category(Position.Category.FOOD)
                .description("Good soup")
                .create();

        final Position meat = MenuPosition.builder()
                .name("Стейк на гриле")
                .price(150)
                .composition("мраморная говядина", "специи", "помидорки черри")
                .category(Position.Category.FOOD)
                .description("Сочная мраморная говядина, приготовленная на гриле с максимально ароматными специями и помидорками черри!")
                .create();

        final Position vine = MenuPosition.builder()
                .name("Вино")
                .price(250)
                .category(Position.Category.BAR)
                .description("Good Vine")
                .create();

        Menu<Position> positionMenu = new TMenu(List.of(soup, meat, vine));
        final TChatContext context = ChatContext.createContext(positionMenu, TChatContext.class);
        final Long chatId = AbilityUtils.getChatId(update);
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/start")) {
                SendMessage message = new SendMessage();

                message.enableHtml(true);
                message.setChatId(chatId);
                message.setText("<i><b>Выберете катеригори меню.</b></i>");
                message.setReplyMarkup(KeyboardFactory.positionCategoryButtons());

                sender.execute(message);
            }
        } else if (CALLBACK_QUERY.test(update)) {
            String callData = update.getCallbackQuery().getData();
            final Position.Category category = Position.Category.valueOf(callData);
            Optional.ofNullable(context.positionsToString(category))
                    .ifPresentOrElse(
                            text -> sender.sendMd(text, chatId),
                            () -> sender.send("К сожаление позиции данной категории отсутствуют.", chatId));
        }
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
