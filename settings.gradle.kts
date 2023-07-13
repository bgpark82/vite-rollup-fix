rootProject.name = "dataplatform"

// 공통
include("common")

// 매출통계
include("stat")

// 데이터 통합 조회
include("harrods")

pluginManagement {
    val SPRING_BOOT_VERSION: String by settings
    val SPRING_BOOT_DEPENDENCY_MANAGEMENT_VERSION: String by settings
    val KOTLIN_VERSION: String by settings
    val ASCII_DOCTOR: String by settings
    val KTLINT: String by settings

    plugins {
        // 스프링부트
        id("org.springframework.boot") version SPRING_BOOT_VERSION

        // 스프링부트 버전에 맞는 의존성을 가져오도록 도와주는 플러그인
        id("io.spring.dependency-management") version SPRING_BOOT_DEPENDENCY_MANAGEMENT_VERSION

        // Kotlin JVM plugin
        kotlin("jvm") version KOTLIN_VERSION

        // 특정한 어노테이션이 붙은 클래스의 접근 제한자를 open 으로 모두 변경(https://kotlinlang.org/docs/all-open-plugin.html#spring-support)
        kotlin("plugin.spring") version KOTLIN_VERSION

        // Spring REST Docs AsciiDoc
        id("org.asciidoctor.jvm.convert") version ASCII_DOCTOR

        // kotlin lint @see https://github.com/jlleitschuh/ktlint-gradle or https://pinterest.github.io/ktlint/
        id("org.jlleitschuh.gradle.ktlint") version KTLINT

        // kotlin lint @see https://github.com/jlleitschuh/ktlint-gradle or https://pinterest.github.io/ktlint/
        id("org.jlleitschuh.gradle.ktlint-idea") version KTLINT
    }
}
