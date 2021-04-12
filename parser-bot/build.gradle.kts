plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

apply(plugin = "org.springframework.boot")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

dependencies {
    implementation(project(":standard-bot")) {
        api(Dependencies.TELEGRAM_BOTS)
    }
    implementation(Dependencies.SPRING_BOOT_WEB)
    implementation(Dependencies.KOTLIN_STDLIB)
}
