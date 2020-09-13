package com.immmus.standard.telegram.bot.settings;

import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.Optional;

public interface ProxySettings {
    DefaultBotOptions.ProxyType DEFAULT_PROXY_TYPE = DefaultBotOptions.ProxyType.SOCKS5;

    String host();
    int port();
    DefaultBotOptions.ProxyType type();

    Optional<String> username();
    Optional<String> password();

    final class FreeProxy implements ProxySettings {
        private final int port;
        private final String host;
        private final DefaultBotOptions.ProxyType type;

        public FreeProxy(int port, String host, DefaultBotOptions.ProxyType type) {
            this.port = port;
            this.host = host;
            this.type = type;
        }

        public FreeProxy(int port, String host) {
            this(port, host, DEFAULT_PROXY_TYPE);
        }

        @Override
        public String host() {
            return this.host;
        }

        @Override
        public int port() {
            return this.port;
        }

        @Override
        public DefaultBotOptions.ProxyType type() {
            return type;
        }

        @Override
        public Optional<String> username() {
            return Optional.empty();
        }

        @Override
        public Optional<String> password() {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return "FreeProxy{" +
                    "port=" + port +
                    ", host='" + host + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    final class PrivateProxySettings implements ProxySettings {
        private final int port;
        private final String host;
        private final DefaultBotOptions.ProxyType type;
        private final String username;
        private final String password;

        public PrivateProxySettings(int port,
                                    String host,
                                    String username, String password,
                                    DefaultBotOptions.ProxyType type) {
            this.port = port;
            this.host = host;
            this.type = type;
            this.username = username;
            this.password = password;
        }

        public PrivateProxySettings(int port, String host, String username, String password) {
            this(port, host, username, password, DEFAULT_PROXY_TYPE);
        }

        @Override
        public String host() {
            return this.host;
        }

        @Override
        public int port() {
            return this.port;
        }

        @Override
        public DefaultBotOptions.ProxyType type() {
            return this.type;
        }

        @Override
        public Optional<String> username() {
            return Optional.of(this.username);
        }

        @Override
        public Optional<String> password() {
            return Optional.of(this.password);
        }

        @Override
        public String toString() {
            return "PrivateProxySettings{" +
                    "port=" + port +
                    ", host='" + host + '\'' +
                    ", type=" + type +
                    ", username='" + username + '\'' +
                    '}';
        }
    }
}