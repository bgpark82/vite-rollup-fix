package com.musinsa.stat.restdoc.enumcontroller

import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Metric Enum 아스키독 노출을 위한 테스트 전용 컨트롤러
 */
@RestController
@RequestMapping("/test")
internal class EnumController {

    @GetMapping("/metric")
    fun getMetrics(): Map<String, String> {
        return METRIC_VALUES()
    }

    @GetMapping("/order-by")
    fun getOrderBy(): Map<String, String> {
        return ORDER_BY_VALUES()
    }
}

fun METRIC_VALUES(): Map<String, String> {
    return Metric.values().associate { Pair(it.name, it.description) }
}

fun ORDER_BY_VALUES(): Map<String, String> {
    return OrderBy.values().associate { Pair(it.name, it.alias) }
}