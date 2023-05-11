package com.musinsa.stat.sales.dto

/**
 * 매출통계 API 응답
 */
class SalesStatisticsResponse(jdbcQueryResult: List<SalesStatistics>) {
    // 합계
    val sum: SalesStatistics

    // 결과값
    val content: List<SalesStatistics>

    /**
     * 합계와, 결과값 필드를 만든다.
     */
    init {
        sum = jdbcQueryResult.first { it.isGrouping }
        content = jdbcQueryResult.filter { !it.isGrouping }
    }

}