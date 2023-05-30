package com.musinsa.stat.aws

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerResponse

@RestController
@RequestMapping("/aws")
class HealthCheckController {
    /**
     * AWS Auto Scaling Group Health check 용도
     */
    @GetMapping("/health")
    fun healthCheck(): ServerResponse {
        return ServerResponse.ok().build()
    }
}