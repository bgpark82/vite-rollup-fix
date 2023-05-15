package com.musinsa.stat.sales.dto

import java.sql.ResultSet

/**
 * 일별매출통계
 */
data class Daily(
    val rs: ResultSet,
    /**
     * 8자리 일자
     * ex) 20230502
     */
    val date: String
) : SalesStatistics(rs)