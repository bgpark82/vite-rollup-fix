package com.musinsa.stat

import com.musinsa.common.databricks.config.StatDatabricksDataSourceConfig
import com.musinsa.common.databricks.service.StatDatabricksClient
import com.musinsa.common.error.RestControllerAdviceExceptionHandler
import com.musinsa.common.util.HttpClient
import com.musinsa.commonmvc.aws.HealthCheckController
import com.musinsa.commonmvc.config.CorsConfig
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
        StatDatabricksDataSourceConfig::class, StatDatabricksClient::class
    ]
)
class StatApplication

fun main(args: Array<String>) {
    runApplication<StatApplication>(*args)
}
