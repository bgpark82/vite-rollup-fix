package com.musinsa.harrods.query.dto

import com.musinsa.harrods.utils.validator.Cron
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

const val TTL_DEFAULT: Long = 3 * 24 * 60 * 60
const val TTL_MAX: Long = 9_223_370_000_000_000L
const val TTL_MIN: Long = 1L

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
    val ttl: Long = TTL_DEFAULT,

    /**
     * cron 표현식
     */
    @field:NotBlank(message = "interval은 필수값입니다")
    @field:Cron(message = "유효하지 않은 cron 표현식 입니다")
    val interval: String,

    /**
     * 등록자 아이디
     */
    @field:NotBlank(message = "등록자 아이디는 null이거나 빈 문자열이 아니어야 합니다")
    val userId: String
)
