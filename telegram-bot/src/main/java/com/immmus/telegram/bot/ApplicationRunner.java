package com.immmus.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        String botName = null;
        try {
            botName = bot.getClient().getMe().getFirstName();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        log.info("{} is running.", botName);
    }
}