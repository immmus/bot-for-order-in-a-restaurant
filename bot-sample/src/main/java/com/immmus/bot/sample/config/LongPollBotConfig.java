package com.immmus.bot.sample.config;

import com.immmus.bot.sample.config.profiles.Heroku;
import com.immmus.standard.telegram.bot.LongPollTelegramBotService;
import com.immmus.standard.telegram.bot.TelegramBotService;
import com.immmus.standard.telegram.bot.settings.DefaultLongPollBotSettings;
import com.immmus.standard.telegram.bot.settings.LongPollBotSettings;
import com.immmus.standard.telegram.bot.settings.ProxySettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Slf4j
@Configuration
@Profile({"FREE_PROXY_LONG_POLLING_BOT", "PRIVATE_PROXY_LONG_POLLING_BOT", "DEFAULT_LONG_POLLING_BOT", Heroku.profile})
public class LongPollBotConfig extends BotConfig<TelegramLongPollingBot, LongPollBotSettings> {

    @Bean(destroyMethod = "close")
    @Override
    public TelegramBotService<TelegramLongPollingBot> createBot(@Qualifier("LongPollBotSettings") LongPollBotSettings settings) throws TelegramApiRequestException {
        final var api = new TelegramBotsApi();
        try {
            final var telegramBotService = new LongPollTelegramBotService(settings);
            api.registerBot(telegramBotService.client());
            log.info("{} is registered.", telegramBotService.client().getBotUsername());
            return telegramBotService;
        } catch (TelegramApiRequestException e) {
            log.error("There was a problem registering the bot. With {}", settings);
            throw e;
        }
    }

    @Bean("LongPollBotSettings")
    @Profile({"DEFAULT_LONG_POLLING_BOT", Heroku.profile})
    public LongPollBotSettings botSettingsWithoutProxy() {
        return new DefaultLongPollBotSettings(botName, botToken);
    }

    @Bean("LongPollBotSettings")
    @Profile({"FREE_PROXY_LONG_POLLING_BOT"})
    public LongPollBotSettings botSettingsWithFreeProxy() {
        final ProxySettings.Free proxySettings = new ProxySettings.Free(proxyPort, proxyHost, DefaultBotOptions.ProxyType.valueOf(proxyType));
        log.debug(proxySettings.toString());
        return new DefaultLongPollBotSettings(botName, botToken, proxySettings);
    }

    @Bean("LongPollBotSettings")
    @Profile("PRIVATE_PROXY_LONG_POLLING_BOT")
    public LongPollBotSettings botSettingsWithPrivateProxy() {
        final ProxySettings.Private proxySettings = new ProxySettings.Private(
                proxyPort, proxyHost, username, password,
                DefaultBotOptions.ProxyType.valueOf(proxyType));
        log.debug(proxySettings.toString());
        return new DefaultLongPollBotSettings(botName, botToken, proxySettings);
    }
}