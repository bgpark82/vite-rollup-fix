package com.musinsa.commonwebflux.aws

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

/**
 * AWS TargetGroup 헬스체크를 위한 컨트롤러
 * Import 하여 사용한다.
 */
@RestController
@RequestMapping("/aws")
class ReactiveHealthCheckController {
    /**
     * AWS Auto Scaling Group Health check 용도
     */
    @GetMapping("/health")
    suspend fun healthCheck(): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }
}
