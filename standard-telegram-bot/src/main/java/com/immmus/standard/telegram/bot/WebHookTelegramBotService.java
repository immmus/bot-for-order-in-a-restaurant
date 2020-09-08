package com.immmus.standard.telegram.bot;

import com.immmus.standard.telegram.bot.utils.TelegramBotBuilder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class WebHookTelegramBotService extends TelegramBotService {
    private final TelegramWebhookBot client;

    public WebHookTelegramBotService(TelegramBotBuilder builder) {
        this.client = new TelegramBot(builder);
    }

    @Override
    public TelegramWebhookBot getClient() {
        return this.client;
    }

    @Override
    public void close() {
        //nothing
    }

    private class TelegramBot extends TelegramWebhookBot {
        private final String name;
        private final String token;
        private final String webHookPath;

        public TelegramBot(TelegramBotBuilder builder) {
            super(builder.getDefaultBotOptions());
            this.name = builder.getUsername();
            this.token = builder.getToken();
            this.webHookPath = builder.getWebHookPath();
        }

        @Override
        public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
            BotApiMethod<?> result = updateProcess(update).orElse(null);
            log.debug("Update: {}. Message: {}. Successfully sent", update, result);
            return result;
        }

        @Override
        public String getBotUsername() {
            return this.name;
        }

        @Override
        public String getBotToken() {
            return this.token;
        }

        @Override
        public String getBotPath() {
            return this.webHookPath;
        }
    }
}
