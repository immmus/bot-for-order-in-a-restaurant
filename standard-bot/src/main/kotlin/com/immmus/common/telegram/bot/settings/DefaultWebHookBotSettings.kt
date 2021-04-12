package com.immmus.common.telegram.bot.settings

class DefaultWebHookBotSettings(
    override val webHookPath: String,
    private val botSettings: TelegramBotSettings
) : WebHookBotSettings {

    constructor(webHookPath: String, botUsername: String, token: String) :
            this(
                webHookPath,
                TelegramBotSettings.WithoutProxyDefaultTelegramBotSettings(
                    botUsername,
                    token
                )
            )

    constructor(webHookPath: String, botUsername: String, token: String, proxySettings: ProxySettings) :
            this(
                webHookPath,
                TelegramBotSettings.WithProxyDefaultTelegramBotSettings(
                    botUsername,
                    token,
                    proxySettings
                )
            )

    override fun botUsername(): String {
        return botSettings.botUsername()
    }

    override fun token(): String {
        return botSettings.token()
    }

    override fun proxySettings(): ProxySettings? = botSettings.proxySettings()
}