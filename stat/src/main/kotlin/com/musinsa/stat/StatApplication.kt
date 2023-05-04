package com.musinsa.stat

import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DatabricksHttpConnectionConfig::class)   // @ConstructorBinding 사용시 필수
class StatApplication

fun main(args: Array<String>) {
    runApplication<StatApplication>(*args)
}