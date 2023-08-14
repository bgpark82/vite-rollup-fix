import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // @see version in root/settings.gradle.kts
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.asciidoctor.jvm.convert")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jlleitschuh.gradle.ktlint-idea")

    // @see buildSrc/src/main/kotlin/default.gradle.kts
    id("default")

    // @see buildSrc/src/main/kotlin/databricks.gradle.kts
    id("databricks")
}

group = "com.musinsa"
version = "0.0.1-SNAPSHOT"

@Suppress("PropertyName")
val MAIN_CLASS = "com.musinsa.harrods.HarrodsApplication"

application {
    mainClass.set(MAIN_CLASS)
}

repositories {
    mavenCentral()
}

dependencies {
    // common 의존성
    implementation(project(":common"))

    // common testFixture 의존성
    testImplementation(testFixtures(project(":common")))

    // Lettuce Client(Redis 연결)
    implementation("io.lettuce:lettuce-core:6.2.5.RELEASE")

    // 내장 Redis
    // 0.7.3 버전의 경우 logback 중복 의존성으로 빌드 실패
    implementation("it.ozimov:embedded-redis:0.7.2")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            // 어노테이션을 활용해 결함 탐지. Java 표준에 반영되지 않았음. @NonNull, @CheckForNull 등을 정의
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = System.getProperty("JAVA_VERSION")
        }
    }

    jar {
        // Plain jar 생성 금지
        enabled = false
//
//        // CodeDeploy build script 추가
//        copy {
//            from("script/code-deploy")
//            into("build/libs/script/code-deploy")
//        }
//
//        // CodeDeploy build specification 추가
//        copy {
//            from("./appspec.yml")
//            into("build/libs")
//        }
    }

    bootJar {
        // jar 파일명 지정
        archiveFileName.set("harrods.jar")
    }
}

// Gradle Bug 로 인해 Main Class 를 별도로 지정(Gradle 에서 MainClass 를 kt로 지정 중)
// @see https://stackoverflow.com/questions/73438308/aws-beanstalk-kotlin-spring-boot-nosuchmethodexception-main
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(StringBuilder(MAIN_CLASS).append("Kt").toString())
}

// Ascii Doc Create Tasks
tasks {
    // Ktlint 체크
    ktlintFormat

    // Test 결과를 snippet Directory
    test {
        // Kt Lint Format 재정렬 후, Test 진행
        dependsOn(ktlintFormat)

        outputs.dir(project.property("SNIPPETS_DIR")!!)
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
        inputs.dir(project.property("SNIPPETS_DIR")!!)

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
