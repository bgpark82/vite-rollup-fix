package com.musinsa.harrods.config


import com.musinsa.harrods.databricks.dto.DatabricksRequest
import org.ehcache.config.CacheConfiguration
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.ehcache.event.EventType
import org.ehcache.impl.config.event.DefaultCacheEventListenerConfiguration
import org.ehcache.jsr107.Eh107Configuration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import javax.cache.CacheManager
import javax.cache.Caching

private const val DATABRICKS_CACHE = "databricks"

@Configuration
@EnableCaching
class CacheConfig {

    /**
     * CacheManager에 Cache를 생성한다
     * 스프링은 JSR-107(JCache) specification을 지원한다
     *
     * @see JSR-107 설정 (https://www.ehcache.org/documentation/3.8/107.html)
     * @see Spring cache (https://docs.spring.io/spring-framework/reference/integration/cache/jsr-107.html)
     */
    @Bean
    fun databricksCacheManager(): CacheManager {
        val provider = Caching.getCachingProvider()
        val cacheManager = provider.cacheManager

        // 캐시 생성
        cacheManager.createCache(DATABRICKS_CACHE, createCacheConfig(createDatabricksEhCacheConfig()))

        return cacheManager
    }

    /**
     * EhCache의 설정을 JSR 107 설정으로 매핑
     */
    private fun createCacheConfig(cacheConfiguration: CacheConfiguration<DatabricksRequest, Any>?): javax.cache.configuration.Configuration<DatabricksRequest, Any>? {
        return Eh107Configuration
            .fromEhcacheCacheConfiguration(cacheConfiguration)
    }

    /**
     * EhCache 구현체의 설정
     *
     * @see ehcache tiering (https://www.ehcache.org/documentation/3.9/tiering.html#disk)
     * @see ehcache configuration (https://www.ehcache.org/documentation/3.9/getting-started.html)
     */
    private fun createDatabricksEhCacheConfig(): CacheConfiguration<DatabricksRequest, Any>? {
        val resource = ResourcePoolsBuilder.newResourcePoolsBuilder()
            .heap(2, EntryUnit.ENTRIES) // heap에 저장될 entry 개수
            .offheap(100, MemoryUnit.MB) // heap이 아닌 영역에 저장 가능한 용량

        return CacheConfigurationBuilder.newCacheConfigurationBuilder(
                DatabricksRequest::class.java, // 캐시 키
                Any::class.java, // 캐시 값
                resource)
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10))) // ttl
            .withService(createCacheEventListenerConfig()) //
            .build()
    }

    /**
     * EhCache 구현체의 CacheEventListener 설정
     *
     * @see CacheEventListener (https://www.ehcache.org/documentation/3.9/cache-event-listeners.html)
     */
    private fun createCacheEventListenerConfig(): DefaultCacheEventListenerConfiguration? {
        return CacheEventListenerConfigurationBuilder
            .newEventListenerConfiguration(
                CacheEventLogger(),
                EventType.CREATED,
                EventType.EXPIRED,
                EventType.EVICTED,
                EventType.REMOVED
            )
            .ordered()
            .asynchronous()
            .build()
    }
}
