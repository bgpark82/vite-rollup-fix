import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // @see version in root/settings.gradle.kts
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.asciidoctor.jvm.convert")

    // @see buildSrc/src/main/kotlin/default.gradle.kts
    id("default")

    // @see buildSrc/src/main/kotlin/databricks.gradle.kts
    id("databricks")

    // @see buildSrc/src/main/kotlin/redis.gradle.kts
    id("redis")

    // Using test fixtures(@see https://docs.gradle.org/current/userguide/java_testing.html#sec:java_test_fixtures)
    `java-test-fixtures`
}

group = "com.musinsa"
version = "1.0.0"

@Suppress("PropertyName")
val MAIN_CLASS = "com.musinsa.common.CommonApplication"

application {
    mainClass.set(MAIN_CLASS)
}

dependencies {
    // testFixtures 에서 사용하는 의존성 선언
    // testFixtures 에서는 web, webflux 구분 없이 의존해도 상관 없다.
    testFixturesImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            // 어노테이션을 활용해 결함 탐지. Java 표준에 반영되지 않았음. @NonNull, @CheckForNull 등을 정의
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = System.getProperty("JAVA_VERSION")
        }
    }
}

// Gradle Bug 로 인해 Main Class 를 별도로 지정(Gradle 에서 MainClass 를 kt로 지정 중)
// @see https://stackoverflow.com/questions/73438308/aws-beanstalk-kotlin-spring-boot-nosuchmethodexception-main
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(StringBuilder(MAIN_CLASS).append("Kt").toString())
}
