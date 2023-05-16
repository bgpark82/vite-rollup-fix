package com.musinsa.stat.sales.dto

/**
 * 매출통계 API 응답
 */
class SalesStatisticsResponse(jdbcQueryResult: List<SalesStatisticsMetric>) {
    // 합계
    val sum: SalesStatisticsMetric

    // 결과값
    val content: List<SalesStatisticsMetric>

    init {
        sum = jdbcQueryResult.first { it.isGrouping }
        content = jdbcQueryResult.filter { !it.isGrouping }
    }
}