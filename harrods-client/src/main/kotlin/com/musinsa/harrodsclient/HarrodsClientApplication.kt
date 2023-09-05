package com.musinsa.harrodsclient

import com.musinsa.common.aws.HealthCheckController
import com.musinsa.common.config.CorsConfig
import com.musinsa.common.error.RestControllerAdviceExceptionHandler
import com.musinsa.common.redis.config.LocalRedisDataSourceConfig
import com.musinsa.common.redis.config.LocalRedisServer
import com.musinsa.common.redis.config.RedisDataSourceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication(
    exclude = [
        DataSourceAutoConfiguration::class
    ]
)
@Import(
    value = [
        CorsConfig::class, HealthCheckController::class, RestControllerAdviceExceptionHandler::class,
        LocalRedisServer::class, LocalRedisDataSourceConfig::class, RedisDataSourceConfig::class
    ]
)
class HarrodsClientApplication

fun main(args: Array<String>) {
    runApplication<HarrodsClientApplication>(*args)
}
