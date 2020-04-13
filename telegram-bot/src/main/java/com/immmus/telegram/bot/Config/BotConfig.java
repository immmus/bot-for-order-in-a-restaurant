package com.immmus.telegram.bot.Config;

import com.immmus.telegram.bot.Config.profiles.Common;
import com.immmus.telegram.bot.Config.profiles.Heroku;
import com.immmus.telegram.bot.LongPollingTelegramBotService;
import com.immmus.telegram.bot.TelegramBotService;
import com.immmus.telegram.bot.WebHookTelegramBotService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import javax.annotation.PostConstruct;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.EnumSet;
import java.util.Set;

@Configuration
@PropertySource(value = "${bot.config.source}")
public class BotConfig {
    private static final Logger log = LoggerFactory.getLogger(BotConfig.class);
    private static final Set<Type> types = EnumSet.allOf(BotConfig.Type.class);

    @Value("${bot.name:unknown}")
    private String botName;
    @Value("${bot.token:unknown}")
    private String botToken;
    @Value("${bot.proxy.username:unknown}")
    private String username;
    @Value("${bot.proxy.password:unknown}")
    private String password;
    @Value("${bot.proxy.host:0.0.0.1}")
    private String proxyHost;
    @Value("${bot.proxy.port:666}")
    private Integer proxyPort;
    @Value("${bot.proxy.type-version:SOCKS5}")
    private String proxyType;
    @Value("${bot.type:DEFAULT_LONG_POLLING_BOT}")
    private String stringBotType;
    @Value("${bot.webhook.path:unknown}")
    private String webHookPath;

    @Value("${spring.profiles.active:unknown}")
    private String activeProfile;
    private Type type;

    @PostConstruct
    public void convertType() {
        if (!activeProfile.equals(Heroku.profile)) {
            this.type = types.stream()
                    .filter(t -> t.name().equals(stringBotType))
                    .findFirst()
                    .orElseThrow(() -> {
                        log.error("Unknown or incorrect type bot - '{}'. " +
                                "Please check or set config 'bot.type'. " +
                                "One of types {}.", stringBotType, types);
                        throw new IllegalArgumentException();
                    });
        } else {
            this.type = Type.DEFAULT_LONG_POLLING_BOT;
        }
    }

    @Bean
    @Common
    public TelegramBotService telegramBot(TelegramBotBuilder builder) {
        return createService(builder);
    }

    @Bean
    @Heroku
    public TelegramBotService herokuTelegramBot(TelegramBotBuilder builder) {
        return createService(builder);
    }

    @Bean
    public TelegramBotBuilder telegramBotBuilder() {
        var telegramBotBuilder = TelegramBotBuilder.of()
                .token(botToken)
                .username(botName);

        if (Type.DEFAULT_LONG_POLLING_BOT.equals(type)) {
            return telegramBotBuilder;
        }

        if(Type.DEFAULT_WEBHOOK_BOT.equals(type)) {
            return telegramBotBuilder
                    .webHookPath(webHookPath + "/" + botToken);
        }

        var proxySettingsBuilder = ProxySettings.builder()
                .host(proxyHost)
                .port(proxyPort)
                .proxyType(proxyType);

        if (Type.FREE_PROXY_LONG_POLLING_BOT.equals(type)) {
            return telegramBotBuilder
                    .withProxy(proxySettingsBuilder.build());
        } else if (Type.PRIVATE_PROXY_LONG_POLLING_BOT.equals(type)) {
            proxySettingsBuilder
                    .username(username)
                    .password(password);
            return telegramBotBuilder
                    .withProxy(proxySettingsBuilder.build());
        } else {
            log.warn("Failed to determine bot type. Unknown type - {}. Value get DEFAULT.", stringBotType);
            return telegramBotBuilder;
        }
    }

    private TelegramBotService createService(TelegramBotBuilder builder) {
        final var telegramBotService = type.createBot(builder);
        try {
            registerBot(telegramBotService.getClient());
        } catch (TelegramApiRequestException e) {
            log.error("There was a problem registering the bot. With {}", builder, e);
            System.exit(1);
        }
        return telegramBotService;
    }

    private void registerBot(DefaultAbsSender bot) throws TelegramApiRequestException {
        final var api = new TelegramBotsApi();
        if (bot instanceof LongPollingBot) {
            LongPollingBot pollingBot = (LongPollingBot) bot;
            if (checkUsernameAndToken(pollingBot.getBotUsername(), pollingBot.getBotToken())){
                api.registerBot(pollingBot);
            }
        }

        if (bot instanceof WebhookBot) {
            WebhookBot webhookBot = (WebhookBot) bot;
            if (checkUsernameAndToken(webhookBot.getBotUsername(), webhookBot.getBotToken())) {
                api.registerBot((WebhookBot) bot);
            }
        }
    }

    private boolean checkUsernameAndToken(String botUsername, String botToken) {
        if (!StringUtils.isEmpty(botUsername)
                && !StringUtils.isEmpty(botToken)
                && !botUsername.equals("unknown")
                && !botToken.equals("unknown")) {
            return true;
        } else {
            log.error("This bot were'nt registered! Check bot_name and bot_token.");
            return false;
        }
    }

    @Getter
    public enum Type {
        FREE_PROXY_LONG_POLLING_BOT {
            @Override
            TelegramBotService createBot(TelegramBotBuilder builder) {
                return new LongPollingTelegramBotService(builder);
            }
        },
        PRIVATE_PROXY_LONG_POLLING_BOT {
            @Override
            TelegramBotService createBot(TelegramBotBuilder builder) {
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                builder.getProxySettings().getUsername(),
                                builder.getProxySettings().getPassword().toCharArray());
                    }
                });
                return new LongPollingTelegramBotService(builder);
            }
        },
        DEFAULT_LONG_POLLING_BOT {
            @Override
            TelegramBotService createBot(TelegramBotBuilder builder) {
                return new LongPollingTelegramBotService(builder);
            }
        },
        DEFAULT_WEBHOOK_BOT {
            @Override
            TelegramBotService createBot(TelegramBotBuilder builder) {
                return new WebHookTelegramBotService(builder);
            }
        };

        abstract TelegramBotService createBot(TelegramBotBuilder builder);
    }
}