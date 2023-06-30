package com.musinsa.stat

import com.musinsa.common.aws.HealthCheckController
import com.musinsa.common.config.CorsConfig
import com.musinsa.common.databricks.config.DatabricksDataSourceConfig
import com.musinsa.common.databricks.service.DatabricksClient
import com.musinsa.common.error.RestControllerAdviceExceptionHandler
import com.musinsa.common.util.HttpClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.search.config.SearchQueryStore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@EnableConfigurationProperties(value = [QueryStore::class, SearchQueryStore::class]) // @ConstructorBinding 사용시 필수
@Import(
    value = [
        CorsConfig::class, HealthCheckController::class, HttpClient::class, RestControllerAdviceExceptionHandler::class,
        DatabricksDataSourceConfig::class, DatabricksClient::class
    ]
)
class StatApplication

fun main(args: Array<String>) {
    runApplication<StatApplication>(*args)
}
