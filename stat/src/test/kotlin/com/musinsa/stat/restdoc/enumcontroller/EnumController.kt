package com.musinsa.stat.restdoc.enumcontroller

import com.musinsa.stat.databricks.error.DatabricksError
import com.musinsa.stat.error.CommonError
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.error.SalesError
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
    fun getMetricValues(): Map<String, String> {
        return METRIC_VALUES()
    }

    @GetMapping("/order-by")
    fun getOrderByValues(): Map<String, String> {
        return ORDER_BY_VALUES()
    }

    @GetMapping("/sales-start")
    fun getSalesStartValues(): Map<String, String> {
        return SALES_START_VALUES()
    }

    @GetMapping("/error")
    fun getErrorValues(): Map<String, String> {
        return ERROR_VALUES()
    }
}

fun METRIC_VALUES(): Map<String, String> {
    return Metric.values().associate { Pair(it.name, it.description) }
}

fun ORDER_BY_VALUES(): Map<String, String> {
    return OrderBy.values().associate { Pair(it.name, it.alias) }
}

fun SALES_START_VALUES(): Map<String, String> {
    return SalesStart.values().associate { Pair(it.name, it.description) }
}

// 도메인에서 사용중인 모든 에러를 가져온다.
fun ERROR_VALUES(): Map<String, String> {
    val errors = mutableMapOf<String, String>()
    errors.putAll(
        CommonError.values().associate { Pair(it.name, it.message) })
    errors.putAll(
        DatabricksError.values().associate { Pair(it.name, it.message) })
    errors.putAll(
        SalesError.values().associate { Pair(it.name, it.message) })
    return errors
}