package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

data class Brand(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 브랜드
     */
    val brandId: String,

    /**
     * 브랜드명
     */
    val brandName: String
) : SalesStatisticsMetric(rs)
