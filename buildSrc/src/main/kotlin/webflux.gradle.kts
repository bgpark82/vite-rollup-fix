/**
 * Webflux + 코루틴 의존성
 */
plugins {
    // Java 플러그인 필수
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    // 코루틴
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    // Spring-webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // 코틀린 리액터
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Spring REST Docs(WebTestClient)
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
}
