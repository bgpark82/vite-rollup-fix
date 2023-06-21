import gradle.kotlin.dsl.accessors._372cb98cdeef2700378e7f0f3752e174.testImplementation
import org.gradle.kotlin.dsl.dependencies

/**
 * 테스트 의존성 선언
 */
plugins {
    // Java 플러그인 필수
    id("java")
}


dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Mockito-Kotlin
    // @see https://github.com/mockito/mockito-kotlin/wiki/Mocking-and-verifying
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    // MockMvc Test 를 사용하는 Spring REST Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}