package com.musinsa.stat.sales.dto

/**
 * 매출통계 API 응답
 */
data class SalesStatisticsResponse<T>(
    // 결과값 합계
    val sum: T,

    // 결과값 평균
//    val average: T,

    // 결과값
    val content: List<T>
)