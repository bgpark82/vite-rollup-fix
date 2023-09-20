package com.musinsa.harrods.query.dto

import com.musinsa.harrods.query.domain.Query
import java.time.LocalDateTime

data class QueryResponse(

    /**
     * 쿼리 아이디
     */
    var id: Long,

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
    var ttl: Long,

    /**
     * cron 표현식
     */
    var interval: String,

    /**
     * 등록자 아이디
     */
    var userId: String,

    /**
     * 쿼리 등록 시간
     */
    var createdDateTime: LocalDateTime,

    /**
     * 쿼리 수정 시간
     */
    var modifiedDateTime: LocalDateTime,

    /**
     * 별칭
     */
    var alias: List<String>
) {
    companion object {
        fun of(query: Query): QueryResponse {
            return QueryResponse(
                id = query.id!!,
                userId = query.userId,
                query = query.query,
                key = query.cacheKey,
                ttl = query.ttl,
                interval = query.scheduleInterval,
                createdDateTime = query.createdDateTime,
                modifiedDateTime = query.modifiedDateTime,
                alias = query.cacheKeySuffix
            )
        }
    }
}
