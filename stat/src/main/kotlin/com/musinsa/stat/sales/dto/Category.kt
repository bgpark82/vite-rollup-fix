package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

data class Category(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 대분류
     */
    val largeCategoryCode: String,

    /**
     * 중분류
     */
    val mediumCategoryCode: String,

    /**
     * 소분류
     */
    val smallCategoryCode: String,

    /**
     * 카테고리명
     */
    val category: String
) : SalesStatisticsMetric(rs)