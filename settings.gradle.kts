rootProject.name = "dataplatform"
include("stat")

pluginManagement {
    val springBootVersion: String by settings
    val springBootDependencyManagementVersion: String by settings
    val kotlinVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springBootDependencyManagementVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}