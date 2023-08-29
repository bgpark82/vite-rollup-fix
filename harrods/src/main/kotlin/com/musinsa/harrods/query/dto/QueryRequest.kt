package com.musinsa.harrods.query.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

const val TTL_MAX = 9_223_370_000_000_000L
const val TTL_MIN = 1L

data class QueryRequest(

    /**
     * 쿼리 템플릿
     */
    @field:NotBlank(message = "템플릿은 null이거나 빈 문자열이 아니어야 합니다")
    val template: String,

    /**
     * 쿼리 파라미터
     */
    val params: Map<String, Any>?,

    /**
     * 캐시 만료 시간 (단위: 초)
     */
    @field:Min(value = TTL_MIN, message = "TTL의 최소값은 $TTL_MIN 이상 입니다")
    @field:Max(value = TTL_MAX, message = "TTL의 최대값은 $TTL_MAX 이하 입니다")
    val ttl: Long?,

    /**
     * cron 표현식
     */
    val interval: String
)
