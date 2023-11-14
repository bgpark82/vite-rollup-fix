/**
 * Web Mvc 의존성
 */
plugins {
    // Java 플러그인 필수
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // MockMvc Test 를 사용하는 Spring REST Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}
