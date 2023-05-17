package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

/**
 * 업체별 매출통계
 */
data class Partner(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 업체코드
     */
    val partnerId: String,

    /**
     * 업체명
     */
    val partnerName: String,

    /**
     * 업체구분
     */
    val partnerType: String
) : SalesStatisticsMetric(rs)