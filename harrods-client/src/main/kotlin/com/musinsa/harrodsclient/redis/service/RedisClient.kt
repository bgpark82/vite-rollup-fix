package com.musinsa.harrodsclient.redis.service

import com.fasterxml.jackson.core.type.TypeReference
import com.musinsa.common.util.ObjectMapperFactory
import io.lettuce.core.api.sync.RedisCommands
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * Redis Client Service
 */
@Service
class RedisClient(@Qualifier("redisCommands") private val redisCommands: RedisCommands<String, String>) {
    
    /**
     * 캐시에 저장된 값을 가져온다.
     *
     * @param key 캐시 키
     *
     * @return 캐시 값
     * @throws
     */
    fun get(key: String): List<Map<String, Any>> {
        return ObjectMapperFactory.readValues(
            // TODO Empty key-value 에 대한 예외처리 추가
            redisCommands.get(key),
            object : TypeReference<List<Map<String, Any>>>() {}
        )
    }
}
