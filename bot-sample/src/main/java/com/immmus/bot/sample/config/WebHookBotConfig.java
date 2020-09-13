package com.immmus.bot.sample.config;

import com.immmus.standard.telegram.bot.TelegramBotService;
import com.immmus.standard.telegram.bot.WebHookTelegramBotService;
import com.immmus.standard.telegram.bot.settings.DefaultWebHookBotSettings;
import com.immmus.standard.telegram.bot.settings.ProxySettings;
import com.immmus.standard.telegram.bot.settings.WebHookBotSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@Configuration
@Profile({"DEFAULT_WEBHOOK_BOT", "FREE_PROXY_WEBHOOK_BOT", "PRIVATE_PROXY_WEBHOOK_BOT"})
public class WebHookBotConfig extends BotConfig<TelegramWebhookBot, WebHookBotSettings> {
    @Value("${bot.webhook.path:unknown}")
    protected String webHookPath;

    @Override
    public TelegramBotService<TelegramWebhookBot> registerBot(@Qualifier("WebHookBotSettings") WebHookBotSettings settings) {
        final var api = new TelegramBotsApi();
        final var telegramBotService = new WebHookTelegramBotService(settings);
        try {
            api.registerBot(telegramBotService.client());
            log.info("{} is registered.", telegramBotService.client().getBotUsername());
        } catch (TelegramApiRequestException e) {
            log.error("There was a problem registering the bot. With {}", settings, e);
            System.exit(1);
        }
        return telegramBotService;
    }

    @Bean("WebHookBotSettings")
    @Profile("DEFAULT_WEBHOOK_BOT")
    public WebHookBotSettings botSettingsWithoutProxy() {
        return new DefaultWebHookBotSettings(webHookPath + "/" + botToken, botName, botToken);
    }

    @Bean("WebHookBotSettings")
    @Profile("FREE_PROXY_WEBHOOK_BOT")
    public WebHookBotSettings botSettingsWithFreeProxy() {
        final ProxySettings.Free proxySettings = new ProxySettings.Free(proxyPort, proxyHost, DefaultBotOptions.ProxyType.valueOf(proxyType));
        log.debug(proxySettings.toString());
        return new DefaultWebHookBotSettings(webHookPath + "/" + botToken, botName, botToken, proxySettings);
    }

    @Bean("WebHookBotSettings")
    @Profile("PRIVATE_PROXY_WEBHOOK_BOT")
    public WebHookBotSettings botSettingsWithPrivateProxy() {
        final ProxySettings.Private proxySettings = new ProxySettings.Private(
                proxyPort, proxyHost, username, password,
                DefaultBotOptions.ProxyType.valueOf(proxyType));
        log.debug(proxySettings.toString());
        return new DefaultWebHookBotSettings(webHookPath + "/" + botToken, botName, botToken, proxySettings);
    }
}