package com.musinsa.stat.sales.domain

/**
 * 매출 시점
 */
enum class SalesStart(val description: String) {
    /**
     * 출고요청된건의 금액 - 교환된금액 - 환불된금액
     */
    SHIPPING_REQUEST("출고요청"),

    /**
     * 구매확정된건의 금액 - 교환된금액 - 환불된금액
     */
    PURCHASE_CONFIRM("구매확정")
}