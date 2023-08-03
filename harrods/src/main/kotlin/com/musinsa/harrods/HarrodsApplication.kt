package com.musinsa.harrods

import com.musinsa.common.aws.HealthCheckController
import com.musinsa.common.config.CorsConfig
import com.musinsa.common.databricks.config.StatDatabricksDataSourceConfig
import com.musinsa.common.databricks.service.StatDatabricksClient
import com.musinsa.common.error.RestControllerAdviceExceptionHandler
import com.musinsa.common.util.HttpClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    value = [
        CorsConfig::class, HealthCheckController::class, HttpClient::class, RestControllerAdviceExceptionHandler::class,
        StatDatabricksDataSourceConfig::class, StatDatabricksClient::class
    ]
)
class HarrodsApplication

fun main(args: Array<String>) {
    runApplication<HarrodsApplication>(*args)
}
