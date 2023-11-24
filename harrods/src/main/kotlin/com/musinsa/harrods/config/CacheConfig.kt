package com.musinsa.harrods.config


import com.musinsa.harrods.databricks.dto.DatabricksRequest
import org.ehcache.config.CacheConfiguration
import org.ehcache.jsr107.Eh107Configuration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.cache.CacheManager
import javax.cache.Caching

private const val DATABRICKS_CACHE = "databricks"

@Configuration
@EnableCaching
class CacheConfig(
    private val databricksCacheConfig: CacheConfiguration<DatabricksRequest, Any>
) {

    /**
     * CacheManager에 Cache를 생성한다
     * 스프링은 JSR-107(JCache) specification을 지원한다
     *
     * @see JSR107 설정 (https://www.ehcache.org/documentation/3.8/107.html)
     * @see SpringCache (https://docs.spring.io/spring-framework/reference/integration/cache/jsr-107.html)
     */
    @Bean
    fun databricksCacheManager(): CacheManager {
        val provider = Caching.getCachingProvider()
        val cacheManager = provider.cacheManager

        // 캐시 생성
        cacheManager.createCache(DATABRICKS_CACHE, createCacheConfig(databricksCacheConfig))

        return cacheManager
    }

    /**
     * EhCache의 설정을 JSR 107 설정으로 매핑
     */
    private fun createCacheConfig(cacheConfiguration: CacheConfiguration<DatabricksRequest, Any>?): javax.cache.configuration.Configuration<DatabricksRequest, Any>? {
        return Eh107Configuration
            .fromEhcacheCacheConfiguration(cacheConfiguration)
    }
}
