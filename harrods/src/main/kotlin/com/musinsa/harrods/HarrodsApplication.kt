package com.musinsa.harrods

import com.musinsa.common.error.RestControllerAdviceExceptionHandler
import com.musinsa.commonmvc.aws.HealthCheckController
import com.musinsa.commonmvc.config.CorsConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    value = [
        CorsConfig::class, HealthCheckController::class, RestControllerAdviceExceptionHandler::class
    ]
)
class HarrodsApplication

fun main(args: Array<String>) {
    runApplication<HarrodsApplication>(*args)
}
