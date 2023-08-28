package com.musinsa.harrods.query.dto

data class QueryRequest(

    /**
     * 쿼리 템플릿
     */
    val template: String,

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
    val schedule: String
)
