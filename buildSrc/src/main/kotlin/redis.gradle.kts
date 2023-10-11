/**
 * 레디스 연결 시 사용
 */
plugins {
    // Java 플러그인 필수
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    // Lettuce Client(Redis 연결)
    implementation("io.lettuce:lettuce-core:6.2.5.RELEASE")

    // 내장 Redis
    // 0.7.3 버전의 경우 logback 중복 의존성으로 빌드 실패
    implementation("it.ozimov:embedded-redis:0.7.2")

    // Connection Pool
    implementation("org.apache.commons:commons-pool2:2.12.0")
}
