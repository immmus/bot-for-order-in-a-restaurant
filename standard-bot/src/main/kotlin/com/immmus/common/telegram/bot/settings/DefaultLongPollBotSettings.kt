package com.immmus.common.telegram.bot.settings

class DefaultLongPollBotSettings(private val botSettings: TelegramBotSettings) : LongPollBotSettings {

    constructor(botUsername: String, token: String) :
            this(
                TelegramBotSettings.WithoutProxyDefaultTelegramBotSettings(botUsername, token)
            )

    constructor(botUsername: String, token: String, proxySettings: ProxySettings) :
            this(
                TelegramBotSettings.WithProxyDefaultTelegramBotSettings(botUsername, token, proxySettings)
            )

    override fun botUsername(): String = botSettings.botUsername()

    override fun token(): String = botSettings.token()

    override fun proxySettings(): ProxySettings? = botSettings.proxySettings()

}