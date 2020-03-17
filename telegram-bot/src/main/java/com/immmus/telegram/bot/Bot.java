package com.immmus.telegram.bot;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.repository.MenuPosition;
import com.immmus.infrastructure.api.service.ChatContext;
import com.immmus.telegram.bot.dto.TMenu;
import com.immmus.telegram.bot.repository.MenuPositionRepository;
import com.immmus.telegram.bot.service.TChatContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.telegram.abilitybots.api.objects.Flag.CALLBACK_QUERY;

public class Bot extends TelegramLongPollingBot {
    @Autowired
    private MenuPositionRepository repo;

    private String name;
    private String token;
    protected MessageSender sender;
    protected SilentSender silent;

    public Bot(String name, String token) {
        super();
        this.name = name;
        this.token = token;
    }

    public Bot(DefaultBotOptions options, String name, String token) {
        super(options);
        this.name = name;
        this.token = token;
        this.sender = new DefaultSender(this);
        this.silent = new SilentSender(sender);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Position.Builder<MenuPosition> POSITION_BUILDER = MenuPosition.builder();
        final Position soup = POSITION_BUILDER
                .name("Борщ")
                .price(21.50)
                .composition("картофель", "свекла", "говядина", "сметана")
                .category(Position.Category.FOOD)
                .description("Good soup")
                .create();

        final Position meat = POSITION_BUILDER
                .name("Стейк на гриле")
                .price(150)
                .composition("мраморная говядина", "специи", "помидорки черри")
                .category(Position.Category.FOOD)
                .description("Сочная мраморная говядина, приготовленная на гриле с максимально ароматными специями и помидорками черри!")
                .create();

        final Position vine = POSITION_BUILDER
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

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = List.of(
                        Arrays.stream(Position.Category.values())
                                .map(category -> new InlineKeyboardButton()
                                        .setText(category.name())
                                        .setCallbackData(category.name()))
                                .collect(Collectors.toList())
                );
                markupInline.setKeyboard(rowsInline);

                message.enableHtml(true);
                message.setChatId(chatId);
                message.setText("<i><b>Выберете катеригори меню.</b></i>");
                message.setReplyMarkup(markupInline);

                silent.execute(message);
            }
        } else if (CALLBACK_QUERY.test(update)) {
            String callData = update.getCallbackQuery().getData();
            Arrays.stream(Position.Category.values())
                    .filter(category -> callData.equals(category.name()))
                    .findFirst()
                    .map(context::positionsToString)
                    .ifPresentOrElse(
                            text -> {
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setChatId(chatId);
                                sendMessage.enableHtml(true);
                                sendMessage.setText(text);
                                silent.execute(sendMessage);
                            },
                            () -> silent.send("К сожаление позиции данной категории отсутствуют.", chatId)
                    );
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
