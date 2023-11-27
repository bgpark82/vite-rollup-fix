package com.musinsa.harrods.config

import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder
import org.ehcache.event.EventType
import org.ehcache.impl.config.event.DefaultCacheEventListenerConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheEventListenerConfig {

    /**
     * EhCache 구현체의 CacheEventListener 설정
     *
     * @see CacheEventListener (https://www.ehcache.org/documentation/3.9/cache-event-listeners.html)
     */
    @Bean
    fun customCacheEventListenerConfig(): DefaultCacheEventListenerConfiguration? {
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
