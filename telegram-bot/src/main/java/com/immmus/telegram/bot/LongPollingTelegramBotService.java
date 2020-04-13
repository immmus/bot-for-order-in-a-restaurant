package com.immmus.telegram.bot;

import com.immmus.telegram.bot.config.TelegramBotBuilder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LongPollingTelegramBotService extends TelegramBotService {
    private final ExecutorService executor;
    private final TelegramLongPollingBot client;

    public LongPollingTelegramBotService(TelegramBotBuilder builder) {
        this.executor = Executors.newCachedThreadPool();
        this.client =  new TelegramBot(builder);
    }

    @Override
    public TelegramLongPollingBot getClient() {
        return this.client;
    }

    @Override
    public void close() {
        executor.shutdown();
        boolean terminated = false;
        try {
            terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
            if (!terminated) {
                log.error("Bot executor did not terminated in 5 seconds");
            }
        } catch (InterruptedException e) {
            log.error("Bot executor service termination awaiting failed", e);
        }

        if (!terminated) {
            int droppedTasks = executor.shutdownNow().size();
            log.error("Executor was abruptly shut down. {} tasks will not be executed", droppedTasks);
        }
    }

    private class TelegramBot extends TelegramLongPollingBot {
        private String name;
        private String token;

        public TelegramBot(TelegramBotBuilder builder) {
            super(builder.getDefaultBotOptions());
            this.name = builder.getUsername();
            this.token = builder.getToken();
        }

        @Override
        public void onUpdateReceived(Update update) {
            CompletableFuture.runAsync(() ->
                    updateProcess(update).ifPresent(result -> {
                        try {
                            getClient().execute(result);
                            log.debug("Update: {}. Message: {}. Successfully sent", update, result);
                        } catch (TelegramApiException e) {
                            log.error("Update: {}. Can not send message {} to telegram: ", update, result, e);
                        }
                    }), executor);
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
}
