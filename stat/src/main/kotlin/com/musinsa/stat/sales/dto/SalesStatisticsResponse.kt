package com.musinsa.stat.sales.dto

/**
 * 매출통계 API 응답
 */
data class SalesStatisticsResponse(val jdbcQueryResult: List<SalesStatistics>) {
    // 합계
    val sum: SalesStatistics = jdbcQueryResult.first { it.isGrouping }

    // 결과값
    val content: List<SalesStatistics> =
        jdbcQueryResult.filter { !it.isGrouping }

}