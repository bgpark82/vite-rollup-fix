package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

/**
 * 카테고리 매출통계 응답값 DTO
 */
data class Category(
    @JsonIgnore
    val rs: ResultSet,

    /**
     * 대분류
     */
    val largeCategoryCode: String?,

    /**
     * 중분류
     */
    val mediumCategoryCode: String?,

    /**
     * 소분류
     */
    val smallCategoryCode: String?,

    /**
     * 대분류 카테고리명
     */
    val largeCategory: String?,

    /**
     * 중분류 카테고리명
     */
    val mediumCategory: String?,

    /**
     * 소분류 카테고리명
     */
    val smallCategory: String?
) : SalesStatisticsMetric(rs)
