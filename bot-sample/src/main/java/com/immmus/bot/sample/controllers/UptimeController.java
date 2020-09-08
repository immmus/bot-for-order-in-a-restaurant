package com.immmus.bot.sample.controllers;

import com.immmus.bot.sample.config.profiles.Heroku;
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
import java.util.StringJoiner;

@Heroku
@RestController
@PropertySource(value = "${bot.config.source}")
@ConditionalOnExpression("${uptime.controller.controller.enabled}")
public class UptimeController {
    private final static LocalDateTime startTime = LocalDateTime.now();
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    @Value("${url.app:http://localhost:8080}") private String url_app;
    @Value("${bot.name}") private String botName;

    @GetMapping
    public String infoApp() {
        return new InfoBuilder()
                .addInfo("link1" , "https://t.me/" + botName)
                .addInfo("link2" , "https://tele.click/" + botName)
                .addInfo("See_uptime_bot"  ,  url_app + "/uptime")
                .toString();
    }

    @GetMapping("/uptime")
    public String checkUptime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return new InfoBuilder()
                .addInfo("Application_started_date" , startTime)
                .addInfo("Uptime" , ChronoUnit.MINUTES.between(startTime, currentTime) + " minutes")
                .toString();
    }

    @Scheduled(fixedDelay = 300_000)
    void pingApp() {
        restTemplate.getForObject(url_app + "/uptime", String.class);
    }

    private static class InfoBuilder {
        private final StringJoiner j = new StringJoiner(",", "{", "}");
        private final static String NODE_FORMAT = "\"%s\":\"%s\"";

        private InfoBuilder () {}

        public InfoBuilder addInfo(Object key, Object value) {
            this.j.add(String.format(NODE_FORMAT, key, value));
            return this;
        }

        @Override
        public String toString() {
            return this.j.toString();
        }
    }
}
