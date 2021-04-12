
plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Dependencies.TELEGRAM_BOTS)

    //logging
    implementation(Dependencies.SLF4J)
    implementation(Dependencies.LOGBACK)
    implementation(Dependencies.SLF4J)

    //kotlin
    implementation(Dependencies.KOTLIN_STDLIB)
    implementation(Dependencies.KOTLIN_REFLECT)
}
