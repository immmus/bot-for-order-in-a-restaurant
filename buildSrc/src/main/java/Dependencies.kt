import Versions.kotlinVersion
import Versions.logbackVersion
import Versions.slf4jVersion
import Versions.springBootVersion
import Versions.telegramBotsVersion

object Versions {
    const val kotlinVersion = "1.4.20"
    const val telegramBotsVersion = "5.1.1"
    const val projectVersion = "0.0.1"
    const val springBootVersion = "2.4.1"
    const val logbackVersion = "1.2.3"
    const val slf4jVersion = "1.7.30"
    const val junitVersion = "5.6.2"
    const val mockkVersion = "1.10.3-jdk8"
}

object Dependencies {
    const val TELEGRAM_BOTS = "org.telegram:telegrambots:$telegramBotsVersion"
    const val LOGBACK = "ch.qos.logback:logback-classic:$logbackVersion"
    const val SLF4J = "org.slf4j:slf4j-api:$slf4jVersion"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    const val SPRING_BOOT_WEB = "org.springframework.boot:spring-boot-starter-web:$springBootVersion"
}