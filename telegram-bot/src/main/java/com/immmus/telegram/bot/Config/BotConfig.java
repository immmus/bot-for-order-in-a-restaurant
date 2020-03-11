package com.immmus.telegram.bot.Config;

import com.immmus.telegram.bot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

import static org.telegram.telegrambots.bots.DefaultBotOptions.ProxyType;

@Configuration
@PropertySource(value = "classpath:/telegram-bot-config.properties")
public class BotConfig {
    private static final String defaultProxyType = ProxyType.SOCKS5.name();
    private static final Logger log = LoggerFactory.getLogger(BotConfig.class);

    public static class Type {
        public final static String FREE_PROXY_BOT = "BotWithFreeProxy";
        public final static String PRIVATE_PROXY_BOT = "BotWithPrivateProxy";
        public final static String DEFAULT_BOT = "defaultBot";
    }

    @Value("${bot.proxy.username}")
    private String username;
    @Value("${bot.proxy.password}")
    private String password;
    @Value("${bot.proxy.host}")
    private String proxyHost;
    @Value("${bot.proxy.port}")
    private Integer proxyPort;
    @Value("${bot.proxy.type-version}")
    private String proxyType;

    @Lazy
    @Bean(name = Type.PRIVATE_PROXY_BOT)
    public Bot privateProxyBot() throws TelegramApiRequestException {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });

        final var api = new TelegramBotsApi();
        final Bot privateProxyBot = new Bot(getDefaultOpt());
        api.registerBot(privateProxyBot);

        return privateProxyBot;
    }

    @Lazy
    @Bean(name = Type.FREE_PROXY_BOT)
    public Bot freeProxyBot() throws TelegramApiRequestException {
        final var api = new TelegramBotsApi();
        final Bot freeProxyBot = new Bot(getDefaultOpt());
        api.registerBot(freeProxyBot);

        return freeProxyBot;
    }

    @Bean(name = Type.DEFAULT_BOT)
    public Bot defaultBot() {
        return new Bot();
    }

    DefaultBotOptions getDefaultOpt() {
        final var defaultBotOptions = ApiContext.getInstance(DefaultBotOptions.class);
        defaultBotOptions.setProxyHost(proxyHost);
        defaultBotOptions.setProxyPort(proxyPort);

        log.info("Set {} proxy host", proxyHost);
        log.info("Set {} proxy port", proxyPort);

        if (proxyType.equals(defaultProxyType)) {
            defaultBotOptions.setProxyType(ProxyType.SOCKS5);
        } else if (proxyType.equals(ProxyType.SOCKS4.name())) {
            defaultBotOptions.setProxyType(ProxyType.SOCKS4);
        } else if (proxyType.equals(ProxyType.HTTP.name())) {
            defaultBotOptions.setProxyType(ProxyType.HTTP);
        } else {
            log.warn("Set incorrect proxy type - {}.", proxyType);
            log.warn("Please use one of types - {}", List.of(ProxyType.SOCKS5, ProxyType.SOCKS4, ProxyType.HTTP));

            return ApiContext.getInstance(DefaultBotOptions.class);
        }

        log.info("Set {} proxy type", proxyType);
        return defaultBotOptions;
    }
}