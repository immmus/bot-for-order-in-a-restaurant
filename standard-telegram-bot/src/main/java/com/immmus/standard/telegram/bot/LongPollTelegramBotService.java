package com.immmus.standard.telegram.bot;

import com.immmus.standard.telegram.bot.settings.LongPollBotSettings;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.*;

@Slf4j
final public class LongPollTelegramBotService extends TelegramBotService<TelegramLongPollingBot> {
    private final ExecutorService executor;
    private final TelegramLongPollingBot client;

    public LongPollTelegramBotService(LongPollBotSettings settings,
                                      DefaultBotOptions defaultBotOptions,
                                      ExecutorService executorService) {
        this.executor = executorService;
        this.client =  new TelegramBot(settings, defaultBotOptions);
    }

    public LongPollTelegramBotService(LongPollBotSettings settings, ExecutorService executorService) {
        this(settings, settings.defaultBotOptions(), executorService);
    }

    public LongPollTelegramBotService(LongPollBotSettings settings) {
        this(settings, settings.defaultBotOptions(), Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    @Override
    public TelegramLongPollingBot client() {
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
        log.info("{} is terminated", getClass().getSimpleName());
    }

    private class TelegramBot extends TelegramLongPollingBot {
        private final String botUsername;
        private final String token;

        public TelegramBot(LongPollBotSettings settings, DefaultBotOptions defaultBotOptions) {
            super(defaultBotOptions);
            this.botUsername = settings.botUsername();
            this.token = settings.token();
        }

        @Override
        public void onUpdateReceived(Update update) {
            CompletableFuture.runAsync(() ->
                    update(update).ifPresent(result -> {
                        try {
                            client().execute(result);
                            log.debug("Update: {}. Message: {}. Successfully sent", update, result);
                        } catch (TelegramApiException e) {
                            log.error("Update: {}. Can not send message {} to telegram: ", update, result, e);
                        }
                    }), executor);
        }

        @Override
        public String getBotUsername() {
            return this.botUsername;
        }

        @Override
        public String getBotToken() {
            return this.token;
        }
    }
}
