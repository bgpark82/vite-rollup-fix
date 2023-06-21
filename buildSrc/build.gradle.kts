plugins {
    // Kotlin DSL 설정 필수
    `kotlin-dsl`
}

repositories {
    // 외부 플러그인을 precompiler plugin 적용을 위해선 반드시 선언 필요
    gradlePluginPortal()
}