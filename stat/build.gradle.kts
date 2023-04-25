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
}

group = "com.musinsa"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val javaVersion: String by project

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// 코틀린 리플렉션
	implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		// 어노테이션을 활용해 결함 탐지. Java 표준에 반영되지 않았음. @NonNull, @CheckForNull 등을 정의
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = javaVersion
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
