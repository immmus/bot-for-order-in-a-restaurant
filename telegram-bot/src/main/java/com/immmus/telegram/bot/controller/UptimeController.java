package com.immmus.telegram.bot.controller;

import com.immmus.telegram.bot.Config.profiles.Heroku;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Heroku
@RestController
@PropertySource(value = "${bot.config.source}")
@ConditionalOnExpression("${uptime.controller.controller.enabled}")
public class UptimeController {
    private final static LocalDateTime startTime = LocalDateTime.now();
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    @Value("${url.app:http://localhost:8080}") private String url_app;

    @GetMapping("/uptime")
    public String checkUptime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return "Uptime " + ChronoUnit.MINUTES.between(startTime, currentTime) + " minutes";
    }

    @Scheduled(fixedDelay = 100_000)
    void pingApp() {
        restTemplate.getForObject(url_app + "/uptime", String.class);
    }
}
