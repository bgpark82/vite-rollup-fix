package com.musinsa.harrods.query.domain

import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.time.LocalDateTime

/**
 * 생성된 쿼리 및 메타 데이터를 저장하는 도메인
 */
@Entity
@Table(name = "query_store")
class Query(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    /**
     * 생성된 sql 쿼리
     */
    @Column(name = "query_text", nullable = false, columnDefinition = "TEXT")
    var query: String,

    /**
     * 생성된 캐시 키 (harrods:userId:hash:key:value)
     */
    @Column(name = "cache_key", nullable = false, unique = true)
    var cacheKey: String,

    /**
     * 캐시 만료 시간 (단위: 초)
     */
    @Column(name = "ttl", nullable = false)
    var ttl: Long,

    /**
     * cron 표현식
     */
    @Column(name = "schedule_interval", nullable = false)
    var scheduleInterval: String,

    /**
     * 등록자 아이디
     */
    @Column(name = "user_id", nullable = false)
    var userId: String,

    /**
     * 쿼리 등록 시간
     */
    @Column(name = "create_date_time", nullable = false)
    var createdDateTime: LocalDateTime,

    /**
     * 쿼리 수정 시간
     */
    @Column(name = "modified_date_time", nullable = false)
    var modifiedDateTime: LocalDateTime,

    /**
     * 마지막으로 캐시된 시간 (외부 스케쥴러에서 등록)
     */
    @Column(name = "last_cached_date_time", nullable = true)
    var lastCachedDateTime: LocalDateTime? = null,

    /**
     * 캐시 키에 추가될 파라미터 (외부 스케쥴러에서 사용)
     */
    @Type(value = JsonType::class)
    @Column(name = "cache_key_suffix", columnDefinition = "json")
    var cacheKeySuffix: List<String>

) {
    companion object {
        fun create(queries: String, cacheKey: String, ttl: Long, scheduleInterval: String, userId: String, cacheKeySuffix: List<String>): Query {
            return Query(null, queries, cacheKey, ttl, scheduleInterval, userId, LocalDateTime.now(), LocalDateTime.now(), null, cacheKeySuffix)
        }
    }
}
