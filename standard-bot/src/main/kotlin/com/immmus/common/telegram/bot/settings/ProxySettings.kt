package com.immmus.common.telegram.bot.settings

import org.telegram.telegrambots.bots.DefaultBotOptions

interface ProxySettings {
    companion object {
        var DEFAULT_PROXY_TYPE = DefaultBotOptions.ProxyType.SOCKS5
    }

    fun host(): String
    fun port(): Int
    fun type(): DefaultBotOptions.ProxyType

    fun username(): String?
    fun password(): String?

    class Free(
        private val port: Int,
        private val host: String,
        private val type: DefaultBotOptions.ProxyType = DEFAULT_PROXY_TYPE
    ) : ProxySettings {

        override fun host(): String = host

        override fun port(): Int = port

        override fun type(): DefaultBotOptions.ProxyType = type

        override fun username(): String? = null

        override fun password(): String? = null

        override fun toString(): String {
            return "FreeProxy{" +
                    "port=" + port +
                    ", host='" + host + '\'' +
                    ", type=" + type +
                    '}'
        }
    }

    class Private(
        private val port: Int,
        private val host: String,
        private val username: String,
        private val password: String,
        private val type: DefaultBotOptions.ProxyType = DEFAULT_PROXY_TYPE
    ) : ProxySettings {

        override fun host(): String = host

        override fun port(): Int =  port

        override fun type(): DefaultBotOptions.ProxyType = type

        override fun username(): String = username

        override fun password(): String = password

        override fun toString(): String {
            return "PrivateProxySettings{" +
                    "port=" + port +
                    ", host='" + host + '\'' +
                    ", type=" + type +
                    ", username='" + username + '\'' +
                    '}'
        }
    }
}
