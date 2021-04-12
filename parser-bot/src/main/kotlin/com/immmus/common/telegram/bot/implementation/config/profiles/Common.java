package com.immmus.common.telegram.bot.implementation.config.profiles;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile("DEFAULT_LONG_POLLING_BOT")
public @interface Common {
    String profile = "DEFAULT_LONG_POLLING_BOT";
}
