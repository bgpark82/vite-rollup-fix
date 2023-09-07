package com.musinsa.harrodsclient.redis.service

import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.common.util.ObjectMapperFactory.typeRefListMapAny
import com.musinsa.harrodsclient.redis.dto.Search
import io.lettuce.core.api.sync.RedisStringCommands
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * Redis Client Service
 */
@Service
class RedisClient(
    /**
     * 키 조회만 하는 역할이기 때문에, RedisStringCommands 형태로 주입받는다.
     */
    @Qualifier("redisCommands") private val redisStringCommands: RedisStringCommands<String, String>
) {
    /**
     * 입력된 캐시 키와 맞는 캐시에 저장된 값을 모두 가져온다.
     *
     * @param search 캐시 조건(캐시 키 List)
     *
     * @return 모든 캐시값
     */
    fun getAll(search: Search): List<Map<String, Any>> {
        return redisStringCommands.mget(*search.keys).map { keyValue ->
            when (keyValue.hasValue()) {
                true -> mapOf(
                    keyValue.key to ObjectMapperFactory.readValues(
                        keyValue.value,
                        typeRefListMapAny
                    )
                )

                false -> mapOf(keyValue.key to emptyList())
            }
        }.toList()
    }
}
