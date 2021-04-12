package com.immmus.common.telegram.bot.implementation.config

import com.immmus.common.telegram.bot.LongPollTelegramBotService
import com.immmus.common.telegram.bot.TelegramBotService
import com.immmus.common.telegram.bot.implementation.config.profiles.Heroku
import com.immmus.common.telegram.bot.settings.DefaultLongPollBotSettings
import com.immmus.common.telegram.bot.settings.LongPollBotSettings
import com.immmus.common.telegram.bot.settings.ProxySettings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
@Profile("FREE_PROXY_LONG_POLLING_BOT", "PRIVATE_PROXY_LONG_POLLING_BOT", "DEFAULT_LONG_POLLING_BOT", Heroku.profile)
class LongPollBotConfig : BotConfig<TelegramLongPollingBot, LongPollBotSettings>() {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Bean(destroyMethod = "close")
    @Throws(TelegramApiRequestException::class)
    override fun createBot(@Qualifier("LongPollBotSettings") settings: LongPollBotSettings): TelegramBotService<TelegramLongPollingBot> {
        val api = TelegramBotsApi(DefaultBotSession::class.java)
        return try {
            val telegramBotService = LongPollTelegramBotService(settings)
            api.registerBot(telegramBotService.client())
            log.info("{} is registered.", telegramBotService.client().botUsername)
            telegramBotService
        } catch (e: TelegramApiRequestException) {
            log.error("There was a problem registering the bot. With {}", settings)
            throw e
        }
    }

    @Bean("LongPollBotSettings")
    @Profile("DEFAULT_LONG_POLLING_BOT", Heroku.profile)
    fun botSettingsWithoutProxy(): LongPollBotSettings {
        return DefaultLongPollBotSettings(botName, botToken)
    }

    @Bean("LongPollBotSettings")
    @Profile("FREE_PROXY_LONG_POLLING_BOT")
    fun botSettingsWithFreeProxy(): LongPollBotSettings {
        val proxySettings = ProxySettings.Free(proxyPort.toInt(), proxyHost, DefaultBotOptions.ProxyType.valueOf(proxyType))
        log.debug(proxySettings.toString())
        return DefaultLongPollBotSettings(botName, botToken, proxySettings)
    }

    @Bean("LongPollBotSettings")
    @Profile("PRIVATE_PROXY_LONG_POLLING_BOT")
    fun botSettingsWithPrivateProxy(): LongPollBotSettings {
        val proxySettings: ProxySettings.Private = ProxySettings.Private(
            proxyPort.toInt(), proxyHost, username, password,
            DefaultBotOptions.ProxyType.valueOf(proxyType)
        )
        log.debug(proxySettings.toString())
        return DefaultLongPollBotSettings(botName, botToken, proxySettings)
    }
}