package com.musinsa.stat

import com.musinsa.common.aws.HealthCheckController
import com.musinsa.common.config.CorsConfig
import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.search.config.SearchQueryStore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication

// @ConstructorBinding 사용시 필수
@EnableConfigurationProperties(value = [DatabricksHttpConnectionConfig::class, QueryStore::class, SearchQueryStore::class])

@Import(value = [CorsConfig::class, HealthCheckController::class])
class StatApplication

fun main(args: Array<String>) {
    runApplication<StatApplication>(*args)
}