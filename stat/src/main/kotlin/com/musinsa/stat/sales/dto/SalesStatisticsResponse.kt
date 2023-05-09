package com.musinsa.stat.sales.dto

/**
 * 매출통계 API 응답
 */
data class SalesStatisticsResponse<T>(
    val sum: T,
    val average: T,
    val content: List<T>
)