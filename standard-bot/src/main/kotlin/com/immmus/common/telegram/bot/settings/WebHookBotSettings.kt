package com.immmus.common.telegram.bot.settings

interface WebHookBotSettings : TelegramBotSettings {
    val webHookPath: String
}