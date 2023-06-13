import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// 버전은 major project 의 gradle.properties, settings.gradle.kts 확인
plugins {
    // JVM 어플리케이션(https://docs.gradle.org/current/userguide/application_plugin.html)
    application

    id(Plugin.Spring.SPRINGBOOT.first) version Plugin.Spring.SPRINGBOOT.second
    id(Plugin.Spring.SPRING_DEPENDENCY_MANAGEMENT.first) version Plugin.Spring.SPRING_DEPENDENCY_MANAGEMENT.second
    kotlin(Plugin.KOTLIN.JVM.first) version Plugin.KOTLIN.JVM.second
    kotlin(Plugin.KOTLIN.SPRING.first) version Plugin.KOTLIN.SPRING.second
    id(Plugin.Spring.ASCII_DOCTOR.first) version Plugin.Spring.ASCII_DOCTOR.second

    // TODO util NoArgsConstructor 옮긴 뒤 의존성 삭제
    id(Plugin.Spring.NO_ARGUMENTS.first) version Plugin.Spring.NO_ARGUMENTS.second
}

group = "com.musinsa"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

@Suppress("PropertyName")
val JAVA_VERSION = "17"

@Suppress("PropertyName")
val MAIN_CLASS = "com.musinsa.stat.StatApplication"

// Ascii Doc Snippet Directory
@Suppress("PropertyName")
val SNIPPETS_DIR by extra { file("build/generated-snippets") }

application {
    mainClass.set(MAIN_CLASS)
}

noArg {
    annotation("com.musinsa.stat.util.NoArgsConstructor")
}

repositories {
    mavenCentral()
}

// TODO 의존성 buildSrc 로 이동
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // 코틀린 리플렉션
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // 유효성 체크
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Databricks JDBC connect
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

    // MockMvc Test 를 사용하는 Spring REST Docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            // 어노테이션을 활용해 결함 탐지. Java 표준에 반영되지 않았음. @NonNull, @CheckForNull 등을 정의
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JAVA_VERSION
        }
    }

    jar {
        // Plain jar 생성 금지
        enabled = false

        // CodeDeploy build script 추가
        copy {
            from("script/code-deploy")
            into("build/libs/script/code-deploy")
        }

        // CodeDeploy build specification 추가
        copy {
            from("./appspec.yml")
            into("build/libs")
        }
    }

    bootJar {
        // jar 파일명 지정
        archiveFileName.set("stat.jar")
    }
}

// Gradle Bug 로 인해 Main Class 를 별도로 지정(Gradle 에서 MainClass 를 kt로 지정 중)
// @see https://stackoverflow.com/questions/73438308/aws-beanstalk-kotlin-spring-boot-nosuchmethodexception-main
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(StringBuilder(MAIN_CLASS).append("Kt").toString())
}

// Ascii Doc Create Tasks
tasks {
    // Test 결과를 snippet Directory
    test {
        outputs.dir(SNIPPETS_DIR)
        useJUnitPlatform()
    }

    asciidoctor {
        // test 가 성공해야만, 아래 Task 실행
        dependsOn(test)

        // 기존에 존재하는 Docs 삭제
        doFirst {
            delete(file("src/main/resources/static/docs"))
        }

        // Directory 설정
        inputs.dir(SNIPPETS_DIR)

        // Ascii Doc 파일 복사
        doLast {
            copy {
                from("build/docs/asciidoc")
                into("src/main/resources/static/docs")
            }
        }
    }

    build {
        // Ascii Doc 파일 생성이 성공해야만, Build 진행
        dependsOn(asciidoctor)
    }
}