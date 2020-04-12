package com.immmus.telegram.bot.Config;

import com.immmus.telegram.bot.LongPollingTelegramBotService;
import com.immmus.telegram.bot.TelegramBotService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource(value = "classpath:/telegram-bot-config.properties")
public class BotConfig {
    private static final Logger log = LoggerFactory.getLogger(BotConfig.class);
    private static final Set<Type> types = EnumSet.allOf(BotConfig.Type.class);

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
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
    @Value("${bot.type}")
    private String stringBotType;

    private Type type;

    @PostConstruct
    public void convertType() {
        this.type = types.stream()
                .filter(t -> t.name().equals(stringBotType))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Unknown or incorrect type bot - '{}'. " +
                            "Please check or set config 'bot.type'. " +
                            "One of types {}.", stringBotType, types);
                    throw new IllegalArgumentException();
                });
    }

    @Lazy
    @Bean
    public TelegramBotService telegramBot(TelegramBotBuilder builder) {
        final var telegramBotService = type.createBot(builder);
        try {
            registerBot(telegramBotService.getClient());
        } catch (TelegramApiRequestException e) {
            log.error("There was a problem registering the bot. With {}", builder, e);
            System.exit(1);
        }
        return telegramBotService;
    }

    @Bean
    public TelegramBotBuilder telegramBotBuilder() {
        var telegramBotBuilder = TelegramBotBuilder.of()
                .token(botToken)
                .username(botName);

        if (Type.DEFAULT_LONG_POLLING_BOT.equals(type)) {
            return telegramBotBuilder;
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

    private void registerBot(DefaultAbsSender bot) throws TelegramApiRequestException {
        if (bot instanceof LongPollingBot) {
            registerLongPollingBot((LongPollingBot) bot);
        }

        if (bot instanceof WebhookBot) {
            registerWebHookBot((WebhookBot) bot);
        }
    }

    private void registerWebHookBot(WebhookBot bot) {
        throw new UnsupportedOperationException();
    }

    private void registerLongPollingBot(LongPollingBot bot) throws TelegramApiRequestException {
        final String botUsername = bot.getBotUsername();
        final String botToken = bot.getBotToken();

        if (!StringUtils.isEmpty(botUsername)
                && !StringUtils.isEmpty(botToken)
                && !botUsername.equals("unknown")
                && !botToken.equals("unknown")) {

            final var api = new TelegramBotsApi();
            api.registerBot(bot);
        } else {
            log.error("This bot were'nt registered! Check bot_name and bot_token.");
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
        };

        abstract TelegramBotService createBot(TelegramBotBuilder builder);
    }
}