package com.musinsa.harrods.query.dto

import com.musinsa.harrods.query.domain.Query

data class QueryResponse(

    /**
     * 생성된 sql 쿼리
     */
    var query: String,

    /**
     * 생성된 캐시 키 (ex. harrods:hash:key:value)
     */
    var key: String,

    /**
     * 캐시 만료 시간 (단위: 초)
     */
    var ttl: Int,

    /**
     * cron 표현식
     */
    var interval: String
) {
    companion object {
        fun of(query: Query): QueryResponse {
            return QueryResponse(
                query = query.queries,
                key = query.cacheKey,
                ttl = query.ttl,
                interval = query.scheduleInterval
            )
        }
    }
}
