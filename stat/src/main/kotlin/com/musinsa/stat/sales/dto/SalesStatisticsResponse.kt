package com.musinsa.stat.sales.dto

/**
 * 매출통계 API 응답
 */
data class SalesStatisticsResponse(val jdbcQueryResult: List<SalesStatisticsMetric>) {
    // 합계
    val sum: SalesStatisticsMetric = jdbcQueryResult.first { it.isGrouping }

    // 결과값
    val content: List<SalesStatisticsMetric> =
        jdbcQueryResult.filter { !it.isGrouping }

}