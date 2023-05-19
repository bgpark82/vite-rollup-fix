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
     * 8자리 일자 혹은 6자리 개월
     * ex) 20230502, 202305
     */
    val date: String
) : SalesStatisticsMetric(rs)