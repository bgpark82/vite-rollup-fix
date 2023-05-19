package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

data class Goods(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 상품번호
     */
    val goodsNumber: String,

    /**
     * 상품명
     */
    val goodsName: String,

    /**
     * 스타일넘버
     */
    val styleNumber: String,

    /**
     * 브랜드명
     */
    val brandName: String,

    /**
     * 카테고리
     */
    val category: String,

    /**
     * 담당MD
     */
    val mdId: String,

    /**
     * 상품상태
     */
    val goodsStatusName: String
) : SalesStatisticsMetric(rs)