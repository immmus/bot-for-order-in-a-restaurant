package com.immmus.telegram.bot.config;

import com.immmus.telegram.bot.config.profiles.Common;
import com.immmus.telegram.bot.TelegramBotService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import static org.assertj.core.api.Assertions.assertThat;

/** Перед запуском теста необходимо задать параметры в test-config.properties **/
@ActiveProfiles(value = Common.profile)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BotConfig.class)
@TestPropertySource(locations = "/test-private-telegram-bot-config.properties")
public class BotConfigTest {
    @Autowired
    private TelegramBotService service;
    @Autowired
    private TelegramBotBuilder botBuilder;

    @Value("${bot.proxy.host}")
    private String proxyHost;
    @Value("${bot.proxy.port}")
    private Integer proxyPort;
    @Value("${bot.proxy.type-version}")
    private String proxyType;

    @Test
    public void testSetProxyOpts() {
        final DefaultBotOptions defaultOpt = botBuilder.getDefaultBotOptions();

        assertThat(defaultOpt.getProxyHost()).isEqualTo(proxyHost);
        assertThat(defaultOpt.getProxyPort()).isEqualTo(proxyPort);
        assertThat(defaultOpt.getProxyType()).isEqualTo(DefaultBotOptions.ProxyType.valueOf(proxyType));
    }

    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;

    @Test
    public void testAutowireBot() {
        LongPollingBot serviceClient = (LongPollingBot) service.getClient();
        final String privateBotToken = serviceClient.getBotToken();
        final String privateBotUsername = serviceClient.getBotUsername();

        assertThat(privateBotToken).isEqualTo(token);
        assertThat(privateBotUsername).isEqualTo(name);
    }
}