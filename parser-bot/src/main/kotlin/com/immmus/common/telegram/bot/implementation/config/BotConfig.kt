package com.immmus.common.telegram.bot.implementation.config

import com.immmus.common.telegram.bot.TelegramBotService
import com.immmus.common.telegram.bot.settings.TelegramBotSettings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

@PropertySource(value = ["\${bot.config.source}"])
abstract class BotConfig<Bot : DefaultAbsSender, BotSettings : TelegramBotSettings> {
    @Value("\${bot.name:unknown}")
    protected lateinit var botName: String

    @Value("\${bot.token:unknown}")
    protected lateinit var botToken: String

    @Value("\${bot.proxy.username:unknown}")
    protected lateinit var username: String

    @Value("\${bot.proxy.password:unknown}")
    protected lateinit var password: String

    @Value("\${bot.proxy.host:0.0.0.1}")
    protected lateinit var proxyHost: String

    @Value("\${bot.proxy.port:666}")
    protected lateinit var proxyPort: String

    @Value("\${bot.proxy.type-version:SOCKS5}")
    protected lateinit var proxyType: String

    @Throws(TelegramApiRequestException::class)
    abstract fun createBot(settings: BotSettings): TelegramBotService<Bot>
}