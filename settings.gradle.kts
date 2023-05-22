rootProject.name = "dataplatform"
include("stat")

pluginManagement {
    @Suppress("LocalVariableName") val SPRING_BOOT_VERSION: String by settings
    @Suppress("LocalVariableName") val SPRING_BOOT_DEPENDENCY_MANAGEMENT_VERSION: String by settings
    @Suppress("LocalVariableName") val KOTLIN_VERSION: String by settings
    @Suppress("LocalVariableName") val NO_ARG_VERSION: String by settings
    @Suppress("LocalVariableName") val ASCII_DOC_JVM_VERSION: String by settings

    plugins {
        id("org.springframework.boot") version SPRING_BOOT_VERSION
        id("io.spring.dependency-management") version SPRING_BOOT_DEPENDENCY_MANAGEMENT_VERSION
        kotlin("jvm") version KOTLIN_VERSION
        kotlin("plugin.spring") version KOTLIN_VERSION
        id("org.jetbrains.kotlin.plugin.noarg") version NO_ARG_VERSION
        id("org.asciidoctor.jvm.convert") version ASCII_DOC_JVM_VERSION
    }
}