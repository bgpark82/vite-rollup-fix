package com.musinsa.harrods.config

import org.ehcache.event.CacheEvent
import org.ehcache.event.CacheEventListener
import org.slf4j.LoggerFactory

/**
 * 캐시 변경 확인용 로그
 */
class CacheEventLogger: CacheEventListener<Any, Any> {

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun onEvent(event: CacheEvent<out Any, out Any>?) {
        log.info("key=${event?.key}, oldValue=${event?.oldValue}, newValue=${event?.newValue}")
    }
}
