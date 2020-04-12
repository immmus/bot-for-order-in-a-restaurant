package com.immmus.telegram.bot.Config;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Slf4j
@Getter
public class TelegramBotBuilder {

    private String username;
    private String token;
    private ProxySettings proxySettings;
    private DefaultBotOptions defaultBotOptions = ApiContext.getInstance(DefaultBotOptions.class);

    private TelegramBotBuilder() { }

    static public TelegramBotBuilder of() {
        return new TelegramBotBuilder();
    }

    /**
     * @param username - Bot username.
     */
    public TelegramBotBuilder username(String username) {
        this.username = username;
        return this;
    }

    /**
     * @param token - Bot token.
     */
    public TelegramBotBuilder token(String token) {
        this.token = token;
        return this;
    }

    /**
     * It is not required.
     */
    public TelegramBotBuilder withProxy(ProxySettings proxySettings) {
        this.proxySettings = proxySettings;
        this.defaultBotOptions.setProxyHost(proxySettings.getHost());
        this.defaultBotOptions.setProxyPort(proxySettings.getPort());
        this.defaultBotOptions.setProxyType(proxySettings.getType());

        log.debug("Set {} proxy host", proxySettings.getHost());
        log.debug("Set {} proxy port", proxySettings.getPort());
        log.debug("Set {} proxy type", proxySettings.getType());
        return this;
    }

    @Override
    public String toString() {
        final var n = System.lineSeparator();
        return n +"TelegramBotBuilder{" +
               n + "username='" + username + '\'' + ", " +
               n + "token='" + "This is confidential information. Check your config." + '\'' + ","  +
               n + "proxySettings=" + proxySettings +
               n + '}';
    }
}
