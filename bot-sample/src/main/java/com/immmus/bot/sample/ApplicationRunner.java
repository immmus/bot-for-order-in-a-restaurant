package com.immmus.bot.sample;

import com.immmus.bot.sample.handlers.CallbackQueryHandler;
import com.immmus.bot.sample.handlers.MessageHandler;
import com.immmus.standard.telegram.bot.TelegramBotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import static com.immmus.bot.sample.factories.KeyboardFactory.ButtonActions.CLOSE;
import static com.immmus.standard.telegram.bot.handlers.UpdateProcessesBuilder.callBackQuery;
import static com.immmus.standard.telegram.bot.handlers.UpdateProcessesBuilder.message;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class ApplicationRunner implements CommandLineRunner {
    private final TelegramBotService bot;

    @Autowired
    public ApplicationRunner(TelegramBotService bot) {
        this.bot = bot;
        bot.processes(() -> {
            message("/start", MessageHandler::greeting);
            callBackQuery(CLOSE.callbackData, CallbackQueryHandler::closeButton);
        });
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