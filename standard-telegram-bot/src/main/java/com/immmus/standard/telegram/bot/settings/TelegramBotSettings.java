package com.immmus.standard.telegram.bot.settings;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Optional;

public interface TelegramBotSettings {
    /**
     * @return Bot username.
     */
    String botUsername();

    /**
     * @return Bot token.
     */
    String token();

    /**
     * This is optional parameter.
     * @return {@link ProxySettings} if this option is set
     */
    Optional<ProxySettings> proxySettings();

    default DefaultBotOptions defaultBotOptions() {
        final var defaultBotOptions = ApiContext.getInstance(DefaultBotOptions.class);
        if (proxySettings().isPresent()) {
            final ProxySettings proxySettings = proxySettings().get();
            defaultBotOptions.setProxyHost(proxySettings.host());
            defaultBotOptions.setProxyPort(proxySettings.port());
            defaultBotOptions.setProxyType(proxySettings.type());

            if (proxySettings.username().isPresent()
                    && proxySettings.password().isPresent()) {
                final String username = proxySettings.username().get();
                final char[] password = proxySettings.password().get().toCharArray();
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
            }
        }
        return defaultBotOptions;
    }

    final class WithoutProxyDefaultTelegramBotSettings implements TelegramBotSettings {
        private final String botUsername;
        private final String token;

        public WithoutProxyDefaultTelegramBotSettings(String botUsername, String token) {
            this.botUsername = botUsername;
            this.token = token;
        }

        @Override
        public String botUsername() {
            return this.botUsername;
        }

        @Override
        public String token() {
            return this.token;
        }

        @Override
        public Optional<ProxySettings> proxySettings() {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return "WithoutProxyDefaultTelegramBotSettings{" +
                    "botUsername='" + botUsername + '\'' +
                    '}';
        }
    }

    final class WithProxyDefaultTelegramBotSettings implements TelegramBotSettings {
        private final String botUsername;
        private final String token;
        private final ProxySettings proxySettings;

        public WithProxyDefaultTelegramBotSettings(String botUsername, String token, ProxySettings proxySettings) {
            this.botUsername = botUsername;
            this.token = token;
            this.proxySettings = proxySettings;
        }

        @Override
        public String botUsername() {
            return this.botUsername;
        }

        @Override
        public String token() {
            return this.token;
        }

        @Override
        public Optional<ProxySettings> proxySettings() {
            return Optional.of(this.proxySettings);
        }

        @Override
        public String toString() {
            return "WithProxyDefaultTelegramBotSettings{" +
                    "botUsername='" + botUsername + '\'' +
                    ", proxySettings=" + proxySettings +
                    '}';
        }
    }
 }