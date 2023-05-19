package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

data class Ad(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 광고코드
     */
    val adCode: String,

    /**
     * 광고구분
     */
    val adType: String,

    /**
     * 광고명
     */
    val adName: String
) : SalesStatisticsMetric(rs)
