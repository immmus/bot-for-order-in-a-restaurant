package com.immmus.bot.sample;

import com.immmus.bot.sample.handlers.MessageHandler;
import com.immmus.standard.telegram.bot.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

import static com.immmus.bot.sample.handlers.actions.ActionInlineButtons.ACCEPT;
import static com.immmus.bot.sample.handlers.actions.ActionInlineButtons.CLOSE;
import static com.immmus.standard.telegram.bot.handlers.UpdateProcesses.callBackQuery;
import static com.immmus.standard.telegram.bot.handlers.UpdateProcesses.message;

@Slf4j
@EnableScheduling
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
        this.bot.addProcesses(() -> {
            message("/start", MessageHandler::greeting);
            callBackQuery(CLOSE.callbackData, CLOSE.action());
            callBackQuery(ACCEPT.callbackData, ACCEPT.action());
        });
    }
}