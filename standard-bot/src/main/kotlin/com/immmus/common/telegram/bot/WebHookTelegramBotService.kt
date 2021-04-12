package com.immmus.common.telegram.bot

import com.immmus.common.telegram.bot.settings.WebHookBotSettings
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

abstract class WebHookTelegramBotService(
    settings: WebHookBotSettings,
    defaultBotOptions: DefaultBotOptions? = settings.defaultBotOptions()
) : TelegramBotService<TelegramWebhookBot>() {
    private val client: TelegramWebhookBot

    init {
        client = TelegramBot(settings, defaultBotOptions)
    }

    override fun client(): TelegramWebhookBot = client

    override fun close() {
        log.info("{} is terminated", javaClass.simpleName)
    }

    private inner class TelegramBot(
        private val settings: WebHookBotSettings,
        defaultBotOptions: DefaultBotOptions?
    ) :
        TelegramWebhookBot(defaultBotOptions) {

        override fun onWebhookUpdateReceived(update: Update): BotApiMethod<out BotApiObject>? {
            val result = update(update)
            log.debug("Update: {}. Message: {}. Successfully sent", update, result)
            return result
        }

        override fun getBotUsername(): String = settings.botUsername()

        override fun getBotToken(): String = settings.token()

        override fun getBotPath(): String = settings.webHookPath
    }
}