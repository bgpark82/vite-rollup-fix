/**
 * 웹서버에서 자주 사용되는 의존성 선언
 */
plugins {
    // Java 플러그인 필수
    id("java")

    // JVM 어플리케이션(https://docs.gradle.org/current/userguide/application_plugin.html)
    application
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17

@Suppress("PropertyName")
val JAVA_VERSION_17 = "17"

// Ascii Doc Snippet Directory
@Suppress("PropertyName")
val SNIPPETS_DIR by extra { file("build/generated-snippets") }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // 코틀린 리플렉션
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // 유효성 체크
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // SpringBoot Config Annotation 사용을 위해 추가
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Kotlin Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

    // Mockito-Kotlin
    // @see https://github.com/mockito/mockito-kotlin/wiki/Mocking-and-verifying
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    // Spring Web
    implementation("org.springframework:spring-web:6.0.13")

}

tasks {
    System.setProperty("JAVA_VERSION", JAVA_VERSION_17)
    project.setProperty("SNIPPETS_DIR", SNIPPETS_DIR)
}
