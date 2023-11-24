import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // @see version in root/settings.gradle.kts
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa") version "1.9.20"
    id("org.asciidoctor.jvm.convert")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jlleitschuh.gradle.ktlint-idea")

    // @see buildSrc/src/main/kotlin/default.gradle.kts
    id("default")

    // @see buildSrc/src/main/kotlin/webmvc.gradle.kts
    id("webmvc")
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
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    // EhCache 의존성
    implementation("org.ehcache:ehcache:3.10.8")
    // EhCache에서 JSR-107 API를 지원하는 의존성
    implementation("javax.cache:cache-api:1.1.0")
    // https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    // https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-core
    implementation("org.glassfish.jaxb:jaxb-core:2.3.0.1")
    // https://mvnrepository.com/artifact/com.sun.xml.bind/jaxb-impl
    implementation("com.sun.xml.bind:jaxb-impl:2.3.1")

    // cron 표현식 의존성 (https://github.com/jmrozanec/cron-utils)
    implementation("com.cronutils:cron-utils:9.2.0")
    // json 컬럼 컨버터 (https://github.com/vladmihalcea/hypersistence-utils)
    implementation("io.hypersistence:hypersistence-utils-hibernate-60:3.5.2")

    // common-mvc 의존성
    implementation(project(":common-mvc"))

    // common-mvc testFixture 의존성
    testImplementation(testFixtures(project(":common-mvc")))
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

        when (System.getProperties().getProperty("build_stage")) {
            // 개발
            "dev" -> {
                copy {
                    from("script/code-deploy-dev")
                    into("build/libs/script/code-deploy")
                }
            }
            // 운영
            else -> {
                copy {
                    from("script/code-deploy-prod")
                    into("build/libs/script/code-deploy")
                }
            }
        }

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
