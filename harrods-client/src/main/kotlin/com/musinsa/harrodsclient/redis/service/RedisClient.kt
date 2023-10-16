package com.musinsa.harrodsclient.redis.service

import com.musinsa.common.redis.service.AbstractRedisConnection
import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.common.util.ObjectMapperFactory.typeRefMapAny
import com.musinsa.harrodsclient.redis.dto.Search
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * Redis Client Service
 */
@Service
class RedisClient(
    @Qualifier("redisConnection") private val redisConnection: AbstractRedisConnection
) {
    /**
     * 입력된 캐시 키와 맞는 캐시에 저장된 값을 모두 가져온다.
     *
     * @param search 캐시 조건(캐시 키 List)
     *
     * @return 모든 캐시값
     */
    fun getAll(search: Search): List<Map<String, Any>> {
        return redisConnection.mget(search.keys)
            .map { keyValue ->
                when (keyValue.hasValue()) {
                    true -> mapOf(
                        keyValue.key to ObjectMapperFactory.readValues(
                            keyValue.value,
                            typeRefMapAny
                        )
                    )

                    false -> mapOf(keyValue.key to emptyMap())
                }
            }.toList()
    }
}
