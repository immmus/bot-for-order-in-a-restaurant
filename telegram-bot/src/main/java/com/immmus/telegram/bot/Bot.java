package com.immmus.telegram.bot;

import org.telegram.abilitybots.api.sender.DefaultSender;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private String name;
    private String token;
    protected MessageSender sender;
    protected SilentSender silent;

    public Bot(String name, String token) {
        super();
        this.name = name;
        this.token = token;
    }

    public Bot(DefaultBotOptions options, String name, String token) {
        super(options);
        this.name = name;
        this.token = token;
        this.sender = new DefaultSender(this);
        this.silent = new SilentSender(sender);
    }

    @Override
    public void onUpdateReceived(Update update) { }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
