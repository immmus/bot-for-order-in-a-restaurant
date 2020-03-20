package com.immmus.telegram.bot;

import com.immmus.telegram.bot.Config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.telegram.telegrambots.ApiContextInitializer;

@Slf4j
@SpringBootApplication
@EntityScan(basePackages = "com.immmus.infrastructure.api")
public class ApplicationRunner implements CommandLineRunner {
    private final Bot bot;

    @Autowired
    public ApplicationRunner(@Qualifier(BotConfig.Type.PRIVATE_PROXY_BOT) Bot bot) {
        this.bot = bot;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(ApplicationRunner.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("{} is running.", bot.getBotUsername());
    }
}