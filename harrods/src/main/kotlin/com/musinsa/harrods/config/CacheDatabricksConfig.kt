package com.musinsa.harrods.config

import com.musinsa.harrods.databricks.dto.DatabricksRequest
import org.ehcache.config.CacheConfiguration
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.ehcache.impl.config.event.DefaultCacheEventListenerConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

private const val CACHE_HEAP_ENTRY_SIZE: Long = 2
private const val CACHE_OFF_HEAP_MEMORY_SIZE: Long = 100
private const val CACHE_TTL: Long = 10

@Configuration
class CacheDatabricksConfig(
    private val cacheEventListenerConfig: DefaultCacheEventListenerConfiguration
) {

    /**
     * EhCache 구현체의 설정
     *
     * @see EhCache Tiering (https://www.ehcache.org/documentation/3.9/tiering.html#disk)
     * @see EhCache Configuration (https://www.ehcache.org/documentation/3.9/getting-started.html)
     */
    @Bean
    fun databricksCacheConfig(): CacheConfiguration<DatabricksRequest, Any>? {
        val resource = ResourcePoolsBuilder.newResourcePoolsBuilder()
            .heap(CACHE_HEAP_ENTRY_SIZE, EntryUnit.ENTRIES) // heap에 저장될 entry 개수
            .offheap(CACHE_OFF_HEAP_MEMORY_SIZE, MemoryUnit.MB) // heap이 아닌 영역에 저장 가능한 용량

        return CacheConfigurationBuilder.newCacheConfigurationBuilder(
                DatabricksRequest::class.java, // 캐시 키
                Any::class.java, // 캐시 값
                resource)
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(CACHE_TTL))) // ttl
            .withService(cacheEventListenerConfig) // 캐시 로그 (필요한 경우 사용)
            .build()
    }
}
