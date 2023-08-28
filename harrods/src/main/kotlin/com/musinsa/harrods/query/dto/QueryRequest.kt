package com.musinsa.harrods.query.dto

import jakarta.validation.constraints.NotBlank

data class QueryRequest(

    /**
     * 쿼리 템플릿
     */
    @field:NotBlank(message = "템플릿은 null이거나 빈 문자열이 아니어야 합니다")
    val template: String?,

    /**
     * 쿼리 파라미터
     */
    val params: Map<String, Any>,

    /**
     * 캐시 만료 시간 (단위: 초)
     */
    val ttl: Int,

    /**
     * cron 표현식
     */
    val interval: String
)
