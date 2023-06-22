package com.musinsa.common.databricks.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.NotBlank

/**
 * 데이터브릭스 쿼리 가져오기 API 응답값
 *
 * @see "https://docs.databricks.com/sql/api/queries-dashboards.html#operation/sql-analytics-get-query"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class RetrieveQuery(
    /**
     * 쿼리
     */
    @NotBlank
    @JsonAlias("query")
    val query: String
)