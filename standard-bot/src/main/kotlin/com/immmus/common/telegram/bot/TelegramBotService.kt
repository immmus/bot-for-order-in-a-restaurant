package com.immmus.common.telegram.bot

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.bots.DefaultAbsSender
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

abstract class TelegramBotService<Bot : DefaultAbsSender> : AutoCloseable {
    protected val log: Logger = LoggerFactory.getLogger(this::class.java)

    abstract fun client(): Bot

    fun update(update: Update): BotApiMethod<out BotApiObject>? {
        val chatId = update.message.chatId
        return SendMessage.builder()
            .chatId(chatId.toString())
            .parseMode(ParseMode.HTML)
            .text("<i><b>Hi! I am Bot. My name is immmus. Let's go play?)))</b></i>")
            .build()
    }
}