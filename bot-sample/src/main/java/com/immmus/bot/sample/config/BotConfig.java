package com.immmus.bot.sample.config;

import com.immmus.standard.telegram.bot.TelegramBotService;
import com.immmus.standard.telegram.bot.settings.TelegramBotSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultAbsSender;

@PropertySource(value = "${bot.config.source}")
abstract public class BotConfig<Bot extends DefaultAbsSender, BotSettings extends TelegramBotSettings> {
    @Value("${bot.name:unknown}")
    protected String botName;
    @Value("${bot.token:unknown}")
    protected String botToken;
    @Value("${bot.proxy.username:unknown}")
    protected String username;
    @Value("${bot.proxy.password:unknown}")
    protected String password;
    @Value("${bot.proxy.host:0.0.0.1}")
    protected String proxyHost;
    @Value("${bot.proxy.port:666}")
    protected Integer proxyPort;
    @Value("${bot.proxy.type-version:SOCKS5}")
    protected String proxyType;
    @Value("${bot.type:DEFAULT_LONG_POLLING_BOT}")

    abstract public TelegramBotService<Bot> registerBot(BotSettings settings);
}