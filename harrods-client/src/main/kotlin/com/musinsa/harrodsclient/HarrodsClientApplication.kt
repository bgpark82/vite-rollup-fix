package com.musinsa.harrodsclient

import com.musinsa.common.aws.HealthCheckController
import com.musinsa.common.config.CorsConfig
import com.musinsa.common.error.RestControllerAdviceExceptionHandler
import com.musinsa.common.redis.config.LocalReactiveRedisDataSourcePoolConfig
import com.musinsa.common.redis.config.LocalRedisServer
import com.musinsa.common.redis.config.ReactiveRedisDataSourcePoolConfig
import com.musinsa.common.redis.service.LocalReactiveRedisConnection
import com.musinsa.common.redis.service.ReactiveRedisConnection
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
        LocalRedisServer::class, LocalReactiveRedisDataSourcePoolConfig::class, ReactiveRedisDataSourcePoolConfig::class,
        LocalReactiveRedisConnection::class, ReactiveRedisConnection::class
    ]
)
class HarrodsClientApplication

fun main(args: Array<String>) {
    runApplication<HarrodsClientApplication>(*args)
}
