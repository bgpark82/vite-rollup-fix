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

    // Spring REST Docs(MockMvc)
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}
