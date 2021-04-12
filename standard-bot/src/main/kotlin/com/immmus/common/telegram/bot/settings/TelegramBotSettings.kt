package com.immmus.common.telegram.bot.settings

import org.telegram.telegrambots.bots.DefaultBotOptions
import java.net.Authenticator
import java.net.PasswordAuthentication

interface TelegramBotSettings {
    /**
     * @return Bot username.
     */
    fun botUsername(): String

    /**
     * @return Bot token.
     */
    fun token(): String

    /**
     * This is optional parameter.
     * @return [ProxySettings] if this option is set
     */
    fun proxySettings(): ProxySettings?

    fun defaultBotOptions(): DefaultBotOptions {
        val defaultBotOptions = DefaultBotOptions()
        proxySettings()?.let {
            defaultBotOptions.proxyHost = it.host()
            defaultBotOptions.proxyPort = it.port()
            defaultBotOptions.proxyType = it.type()

            if (it.username() != null && it.password() != null) {
                val charPass = it.password()?.toCharArray()
                Authenticator.setDefault(object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(it.username(), charPass)
                    }
                })
            }
        }
        return defaultBotOptions
    }

    class WithoutProxyDefaultTelegramBotSettings(
        private val botUsername: String,
        private val token: String
    ) : TelegramBotSettings {

        override fun botUsername(): String = botUsername

        override fun token(): String = token

        override fun proxySettings(): ProxySettings? = null

        override fun toString(): String {
            return "WithoutProxyDefaultTelegramBotSettings{" +
                    "botUsername='" + botUsername + '\'' +
                    '}'
        }
    }

    class WithProxyDefaultTelegramBotSettings(
        private val botUsername: String,
        private val token: String,
        private val proxySettings: ProxySettings
    ) : TelegramBotSettings {

        override fun botUsername(): String = botUsername

        override fun token(): String = token

        override fun proxySettings(): ProxySettings = proxySettings

        override fun toString(): String {
            return "WithProxyDefaultTelegramBotSettings{" +
                    "botUsername='" + botUsername + '\'' +
                    ", proxySettings=" + proxySettings +
                    '}'
        }

    }
}