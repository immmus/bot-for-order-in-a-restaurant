package com.immmus.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

@Slf4j
@SpringBootApplication
public class ApplicationRunner implements CommandLineRunner {
    private final TelegramBotService bot;

    @Autowired
    public ApplicationRunner(TelegramBotService bot) {
        this.bot = bot;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(ApplicationRunner.class, args);
    }

    @Override
    public void run(String... args) {
        DefaultAbsSender client = bot.getClient();
        String botName = null;
        if (client instanceof LongPollingBot) {
            botName = ((LongPollingBot) client).getBotUsername();
        }

        if (client instanceof WebhookBot) {
           botName = ((WebhookBot) client).getBotUsername();
        }

        log.info("{} is running.", botName);
    }
}