package com.immmus.standard.telegram.bot.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@RequiredArgsConstructor
@SuppressWarnings("UnusedReturnValue")
public class ProxySettings {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final DefaultBotOptions.ProxyType type;

    public static ProxySettingsBuilder builder() {
        return new ProxySettingsBuilder();
    }

    @Override
    public String toString() {
        return "{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + "This is confidential information. Check your config." + '\'' +
                ", type=" + type +
                '}';
    }

    public static class ProxySettingsBuilder {
        private static final String defaultProxyType = DefaultBotOptions.ProxyType.SOCKS5.name();

        private String proxyHost;
        private int proxyPort;
        private String proxyUserName;
        private String proxyPassword;
        private DefaultBotOptions.ProxyType proxyType;

        /**
         * @param proxyHost - For example 0.0.0.1 or example.com
         */
        public ProxySettingsBuilder host(String proxyHost) {
            this.proxyHost = proxyHost;
            return this;
        }

        public ProxySettingsBuilder port(int proxyPort) {
            this.proxyPort = proxyPort;
            return this;
        }

        /**
         * It is not required if not set password.
         *
         * @param proxyUserName - username for proxy connection
         */
        public ProxySettingsBuilder username(String proxyUserName) {
            Objects.requireNonNull(proxyUserName, "Proxy username cannot be null!");
            this.proxyUserName = proxyUserName;
            return this;
        }

        /**
         * It is not required if not set username
         *
         * @param proxyPassword - password for proxy connection
         */
        public ProxySettingsBuilder password(String proxyPassword) {
            Objects.requireNonNull(proxyUserName, "Proxy password cannot be null!");
            this.proxyPassword = proxyPassword;
            return this;
        }

        public ProxySettingsBuilder proxyType(DefaultBotOptions.ProxyType proxyType) {
            if (this.proxyType != null) return this;
            this.proxyType = proxyType;
            return this;
        }

        /**
         * @param proxyType - One of (HTTP, SOCKS4, SOCKS5) or (http, sock4, sock5). {@link DefaultBotOptions.ProxyType without NO_PROXY}
         */
        public ProxySettingsBuilder proxyType(String proxyType) {
            if (this.proxyType != null) return this;
            if (proxyType.equalsIgnoreCase(defaultProxyType)) {
                return proxyType(DefaultBotOptions.ProxyType.SOCKS5);
            } else if (proxyType.equalsIgnoreCase(DefaultBotOptions.ProxyType.SOCKS4.name())) {
                return proxyType(DefaultBotOptions.ProxyType.SOCKS4);
            } else if (proxyType.equalsIgnoreCase(DefaultBotOptions.ProxyType.HTTP.name())) {
                return proxyType(DefaultBotOptions.ProxyType.HTTP);
            } else {
                log.error("Set incorrect proxy type - {}.", proxyType);
                log.error("Please use one of types - {}", List.of(DefaultBotOptions.ProxyType.SOCKS5, DefaultBotOptions.ProxyType.SOCKS4, DefaultBotOptions.ProxyType.HTTP));
                throw new IllegalArgumentException();
            }
        }

        public ProxySettings build() {
            return new ProxySettings(proxyHost, proxyPort, proxyUserName, proxyPassword, proxyType);
        }
    }
}
