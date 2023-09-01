package com.musinsa.harrodsclient.redis.service

import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.common.util.ObjectMapperFactory.typeRefListMapAny
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
     */
    fun get(key: String): List<Map<String, Any>> {
        return ObjectMapperFactory.readValues(
            // TODO Empty key-value 에 대해서는 Empty 값 리턴하도록 수정
            redisCommands.get(key),
            typeRefListMapAny
        )
    }

    /**
     * 입력된 캐시 키와 맞는 캐시에 저장된 값을 모두 가져온다.
     *
     * @param keys 캐시 키 List
     *
     * @return 모든 캐시값
     */
    // TODO key 갯수 제한. 1000개
    fun getAll(keys: Array<String>): List<Map<String, Any>> {
        return redisCommands.mget(*keys).map { keyValue ->
            mapOf(
                keyValue.key to ObjectMapperFactory.readValues(
                    keyValue.value,
                    typeRefListMapAny
                )
            )
        }.toList()
    }
}
