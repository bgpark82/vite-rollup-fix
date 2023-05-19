package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

data class BrandPartner(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 브랜드
     */
    val brandId: String,

    /**
     * 브랜드명
     */
    val brandName: String,

    /**
     * 업체명
     */
    val partnerName: String,

    /**
     * 담당MD
     */
    val mdId: String,

    /**
     * 정상가. 필요 시 사용한다.
     */
//    val normalPrice: Long,
) : SalesStatisticsMetric(rs)