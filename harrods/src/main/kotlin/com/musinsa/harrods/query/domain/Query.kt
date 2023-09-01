package com.musinsa.harrods.query.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 생성된 쿼리 및 메타 데이터를 저장하는 도메인
 */
@Entity
@Table(name = "queries")
class Query(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    /**
     * 생성된 sql 쿼리
     */
    var queries: String,

    /**
     * 생성된 캐시 키 (ex. harrods:hash:key:value)
     */
    var cacheKey: String,

    /**
     * 캐시 만료 시간 (단위: 초)
     */
    var ttl: Long,

    /**
     * cron 표현식
     */
    var scheduleInterval: String
) {
    companion object {
        fun create(queries: String, cacheKey: String, ttl: Long, scheduleInterval: String): Query {
            return Query(null, queries, cacheKey, ttl, scheduleInterval)
        }
    }
}
