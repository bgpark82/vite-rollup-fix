package com.musinsa.stat.restdoc

import com.musinsa.common.restdoc.COMMON_ERROR_VALUES
import com.musinsa.stat.sales.domain.GoodsKind
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.PartnerType
import com.musinsa.stat.sales.domain.SalesFunnel
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.error.SalesError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// TODO 해롯 클라이언트 컨트롤러와 통합
/**
 * Metric Enum 아스키독 노출을 위한 테스트 전용 컨트롤러
 */
@RestController
@RequestMapping("/test")
internal class EnumController {
    @GetMapping("/error")
    fun getErrorValues(): Map<String, String> {
        return ERROR_VALUES()
    }

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

    @GetMapping("/order-direction")
    fun getOrderDirectionValues(): Map<String, String> {
        return ORDER_DIRECTION_VALUES()
    }

    @GetMapping("/partner-type")
    fun getPartnerTypeValues(): Map<String, String> {
        return PARTNER_TYPE_VALUES()
    }

    @GetMapping("/goods-kind")
    fun getGoodsKindValues(): Map<String, String> {
        return GOODS_KIND_VALUES()
    }

    @GetMapping("/sales-funnel")
    fun getSalesFunnelValues(): Map<String, String> {
        return SALES_FUNNEL_VALUES()
    }
}

fun ERROR_VALUES(): Map<String, String> {
    val errors = COMMON_ERROR_VALUES()
    // 도메인에서 사용중인 에러 추가
    errors.putAll(
        SalesError.values().associate { Pair(it.name, it.message) }
    )
    return errors
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

fun ORDER_DIRECTION_VALUES(): Map<String, String> {
    return OrderDirection.values().associate { Pair(it.name, it.description) }
}

fun PARTNER_TYPE_VALUES(): Map<String, String> {
    return PartnerType.values().associate {
        Pair(
            it.name,
            "DB값: ".plus(it.code.toString()).plus(", 설명: ").plus(it.description)
        )
    }
}

fun GOODS_KIND_VALUES(): Map<String, String> {
    return GoodsKind.values().associate { Pair(it.name, it.description) }
}

fun SALES_FUNNEL_VALUES(): Map<String, String> {
    return SalesFunnel.values().associate { Pair(it.name, it.description) }
}
