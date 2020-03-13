package com.immmus.telegram.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private String name;
    private String token;

    public Bot(String name, String token) {
        super();
        this.name = name;
        this.token = token;
    }

    public Bot(DefaultBotOptions options, String name, String token) {
        super(options);
        this.name = name;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.hasMessage();
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
