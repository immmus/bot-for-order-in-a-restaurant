import Versions.projectVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
}

plugins {
    id("org.springframework.boot") version Versions.springBootVersion apply false
    `maven-publish`
    kotlin("jvm") version Versions.kotlinVersion
    kotlin("plugin.spring") version Versions.kotlinVersion
}

allprojects {
    group = "com.immmus"
    version = projectVersion

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}