package com.immmus.standard.telegram.bot.settings;

import java.util.Optional;

final public class DefaultWebHookBotSettings implements WebHookBotSettings {
    private final TelegramBotSettings botSettings;
    private final String webHoopPath;

    public DefaultWebHookBotSettings(String webHoopPath, TelegramBotSettings botSettings) {
        this.botSettings = botSettings;
        this.webHoopPath = webHoopPath;
    }

    public DefaultWebHookBotSettings(String webHoopPath, String botUsername, String token) {
        this(webHoopPath, new TelegramBotSettings.WithoutProxyDefaultTelegramBotSettings(botUsername, token));
    }

    public DefaultWebHookBotSettings(String webHoopPath, String botUsername, String token, ProxySettings proxySettings) {
        this(webHoopPath, new TelegramBotSettings.WithProxyDefaultTelegramBotSettings(botUsername, token, proxySettings));
    }

    @Override
    public String webHookPath() {
        return this.webHoopPath;
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