import org.gradle.kotlin.dsl.application
import org.gradle.kotlin.dsl.repositories

plugins {
    // JVM 어플리케이션(https://docs.gradle.org/current/userguide/application_plugin.html)
    application
}

repositories {
    mavenCentral()
}