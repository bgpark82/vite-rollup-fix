package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

/**
 * 일별/월별 매출통계
 */
data class DailyAndMontly(
    @JsonIgnore
    val rs: ResultSet,
    /**
     * 8자리 일자
     * ex) 20230502
     */
    val date: String
) : SalesStatisticsMetric(rs)