package com.immmus.standard.telegram.bot.settings;


import java.util.Optional;

final public class DefaultLongPollBotSettings implements LongPollBotSettings {
    private final TelegramBotSettings botSettings;

    public DefaultLongPollBotSettings(TelegramBotSettings botSettings) {
        this.botSettings = botSettings;
    }

    public DefaultLongPollBotSettings(String botUsername, String token) {
        this(new TelegramBotSettings.WithoutProxyDefaultTelegramBotSettings(botUsername, token));
    }

    public DefaultLongPollBotSettings(String botUsername, String token, ProxySettings proxySettings) {
        this(new TelegramBotSettings.WithProxyDefaultTelegramBotSettings(botUsername, token, proxySettings));
    }

    @Override
    public String botUsername() {
        return this.botSettings.botUsername();
    }

    @Override
    public String token() {
        return this.botSettings.token();
    }

    @Override
    public Optional<ProxySettings> proxySettings() {
        return this.botSettings.proxySettings();
    }
}