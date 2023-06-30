package com.musinsa.stat.sales.domain

/**
 * 매출 통계 유형
 */
enum class Metric(val description: String) {
    DAILY("일별"),
    MONTLY("월별"),
    PARTNER("업체별"),
    BRAND("브랜드별"),
    BRAND_PARTNER("브랜드업체별"),
    GOODS("상품별"),
    AD("광고별"),
    COUPON("쿠폰별"),
    CATEGORY("카테고리별")
}
