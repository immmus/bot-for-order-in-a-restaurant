package com.immmus.common.telegram.bot

import com.immmus.common.telegram.bot.settings.LongPollBotSettings
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LongPollTelegramBotService(
    private val settings: LongPollBotSettings,
    private val executor: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
    defaultBotOptions: DefaultBotOptions? = settings.defaultBotOptions()
) : TelegramBotService<TelegramLongPollingBot>() {

    private val client: TelegramLongPollingBot

    init {
        client = TelegramBot(settings, defaultBotOptions)
    }

    constructor(
        settings: LongPollBotSettings,
        executorService: ExecutorService
    ) :
            this(settings, executorService, settings.defaultBotOptions())

    override fun client(): TelegramLongPollingBot = client

    override fun close() {
        executor.shutdown()
        var terminated = false
        try {
            terminated = executor.awaitTermination(5, TimeUnit.SECONDS)
            if (!terminated) {
                log.error("Bot executor did not terminated in 5 seconds")
            }
        } catch (e: InterruptedException) {
            log.error("Bot executor service termination awaiting failed", e)
        }
        if (!terminated) {
            val droppedTasks = executor.shutdownNow().size
            log.error("Executor was abruptly shut down. {} tasks will not be executed", droppedTasks)
        }
        log.info("{} is terminated", this::class.simpleName)
    }

    private inner class TelegramBot(
        private val settings: LongPollBotSettings,
        defaultBotOptions: DefaultBotOptions?
    ) :
        TelegramLongPollingBot(defaultBotOptions) {

        override fun <T : java.io.Serializable, Method : BotApiMethod<T>>
                execute(method: Method): T = super.execute(method)

        override fun onUpdateReceived(update: Update) {
            CompletableFuture.runAsync({
                update(update)?.let {
                    try {
                        execute(it)
                        log.debug("Update: {}. Message: {}. Successfully sent", update, it)
                    } catch (e: TelegramApiException) {
                        log.error("Update: {}. Can not send message {} to telegram: ", update, it, e)
                    }
                }
            }, executor)
        }

        override fun getBotUsername(): String = settings.botUsername()

        override fun getBotToken(): String = settings.token()
    }
}