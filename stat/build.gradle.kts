import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // 스프링부트
    id("org.springframework.boot")

    // 스프링부트 버전에 맞는 의존성을 가져오도록 도와주는 플러그인
    id("io.spring.dependency-management")

    // Kotlin JVM plugin
    kotlin("jvm")

    // 특정한 어노테이션이 붙은 클래스의 접근 제한자를 open 으로 모두 변경(https://kotlinlang.org/docs/all-open-plugin.html#spring-support)
    kotlin("plugin.spring")

    // JVM 어플리케이션(https://docs.gradle.org/current/userguide/application_plugin.html)
    application
}

group = "com.musinsa"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

@Suppress("PropertyName")
val JAVA_VERSION: String by project

@Suppress("PropertyName")
val MAIN_CLASS = "com.musinsa.stat.StatApplication"

application {
    mainClass.set(MAIN_CLASS)
}

repositories {
    mavenCentral()
}

// TODO 의존성 buildSrc로 이동
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // 코틀린 리플렉션
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Valid Check
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Databricks JDBC connect
    // TODO 버전관리 중앙화
    implementation("com.databricks:databricks-jdbc:2.6.25-1")

    // SpringBoot Config Annotation 사용을 위해 추가
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Kotlin Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

    // Mockito-Kotlin
    // @see https://github.com/mockito/mockito-kotlin/wiki/Mocking-and-verifying
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        // 어노테이션을 활용해 결함 탐지. Java 표준에 반영되지 않았음. @NonNull, @CheckForNull 등을 정의
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JAVA_VERSION
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    // Plain jar 생성 금지
    enabled = false
}

tasks.bootJar {
    // jar 파일명 지정
    archiveFileName.set("stat.jar")
}

// Gradle Bug로 인해 Main Class를 별도로 지정(Gradle에서 MainClass를 kt로 지정 중)
// @see https://stackoverflow.com/questions/73438308/aws-beanstalk-kotlin-spring-boot-nosuchmethodexception-main
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(StringBuilder(MAIN_CLASS).append("Kt").toString())
}