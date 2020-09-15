package com.immmus.standard.telegram.bot;

import com.immmus.standard.telegram.bot.settings.WebHookBotSettings;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
final public class WebHookTelegramBotService extends TelegramBotService<TelegramWebhookBot> {
    private final TelegramWebhookBot client;

    public WebHookTelegramBotService(WebHookBotSettings settings, DefaultBotOptions defaultBotOptions) {
        this.client = new TelegramBot(settings, defaultBotOptions);
    }

    public WebHookTelegramBotService(WebHookBotSettings settings) {
        this(settings, settings.defaultBotOptions());
    }

    @Override
    public TelegramWebhookBot client() {
        return this.client;
    }

    @Override
    public void close() {
        log.info("{} is terminated", getClass().getSimpleName());
    }

    private class TelegramBot extends TelegramWebhookBot {
        private final String botUsername;
        private final String token;
        private final String webHookPath;

        public TelegramBot(WebHookBotSettings settings, DefaultBotOptions defaultBotOptions) {
            super(defaultBotOptions);
            this.botUsername = settings.botUsername();
            this.token = settings.token();
            this.webHookPath = settings.webHookPath();
        }

        @Override
        public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
            BotApiMethod<?> result = update(update).orElse(null);
            log.debug("Update: {}. Message: {}. Successfully sent", update, result);
            return result;
        }

        @Override
        public String getBotUsername() {
            return this.botUsername;
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