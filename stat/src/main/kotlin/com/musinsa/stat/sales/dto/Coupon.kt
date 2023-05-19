package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

data class Coupon(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 쿠폰번호
     */
    val couponNumber: String,

    /**
     * 쿠폰구분
     */
    val couponTypeDescription: String,

    /**
     * 쿠폰명
     */
    val couponName: String,

    /**
     * 쿠폰사용구분
     */
    val couponApplyType: String,

    /**
     * 발행금액
     */
    val couponIssueAmount: String
) : SalesStatisticsMetric(rs)
