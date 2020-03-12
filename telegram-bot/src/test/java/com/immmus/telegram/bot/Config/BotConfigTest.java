package com.immmus.telegram.bot.Config;

import com.immmus.telegram.bot.Bot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BotConfig.class)
@TestPropertySource(locations = "/test-telegram-bot-config.properties")
public class BotConfigTest {
    @Autowired
    private BotConfig config;
    @Value("${bot.proxy.host}")
    private String proxyHost;
    @Value("${bot.proxy.port}")
    private Integer proxyPort;
    @Value("${bot.proxy.type-version}")
    private String proxyType;


    /** Перед запуском теста необходимо задать параметры в test-config.properties **/
    @Test
    public void testSetProxyOpts() {
        final DefaultBotOptions defaultOpt = config.getDefaultOpt();

        assertThat(defaultOpt.getProxyHost()).isEqualTo(proxyHost);
        assertThat(defaultOpt.getProxyPort()).isEqualTo(proxyPort);
        assertThat(defaultOpt.getProxyType()).isEqualTo(DefaultBotOptions.ProxyType.valueOf(proxyType));
    }

    @Autowired
    @Qualifier(BotConfig.Type.FREE_PROXY_BOT)
    private Bot freeProxyBot;
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;

    @Test
    public void testAutowireBot() {
        final String botToken = freeProxyBot.getBotToken();
        final String botUsername = freeProxyBot.getBotUsername();

        assertThat(botToken).isEqualTo(token);
        assertThat(botUsername).isEqualTo(name);
    }
}
