/**
 * 데이터 브릭스 연결 시 사용하는 의존성
 */
plugins {
    // Java 플러그인 필수
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    // Databricks JDBC connect
    implementation("com.databricks:databricks-jdbc:2.6.25-1")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}