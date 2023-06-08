package com.musinsa.stat.sales.domain

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Metric Enum 아스키독 노출을 위한 테스트 전용 컨트롤러
 */
@RestController
@RequestMapping("/test/metric")
internal class MetricController {

    @GetMapping
    fun getMetrics(): Map<String, String> {
        return METRIC_결과()
    }
}

fun METRIC_결과(): Map<String, String> {
    return Metric.values().associate { Pair(it.name, it.description) }
}