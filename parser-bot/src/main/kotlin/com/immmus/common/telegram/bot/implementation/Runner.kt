package com.immmus.common.telegram.bot.implementation

import com.immmus.common.telegram.bot.TelegramBotService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Runner

fun main(args: Array<String>) {
    val context = runApplication<Runner>(*args)
    context.getBean(TelegramBotService::class.java)
}