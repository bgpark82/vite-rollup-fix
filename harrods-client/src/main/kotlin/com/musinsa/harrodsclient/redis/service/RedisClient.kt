package com.musinsa.harrodsclient.redis.service

import com.musinsa.common.redis.service.AbstractRedisConnection
import com.musinsa.common.util.ObjectMapperFactory
import com.musinsa.common.util.ObjectMapperFactory.typeRefMapAny
import com.musinsa.harrodsclient.redis.dto.Search
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
//    @Qualifier("redisCommands") private val redisStringCommands: RedisStringReactiveCommands<String, String>
    @Qualifier("redisConnection") private val redisConnection: AbstractRedisConnection

) {

    // TODO 코루틴 에러 핸들러 추가
    /**
     * 입력된 캐시 키와 맞는 캐시에 저장된 값을 모두 가져온다.
     *
     * @param search 캐시 조건(캐시 키 List)
     *
     * @return 모든 캐시값
     */
    suspend fun getAll(search: Search): List<Map<String, Any>> {
//        return CoroutineScope(Dispatchers.IO).async { redisStringCommands.mget(*search.keys) }.await().map { keyValue ->
//            when (keyValue.hasValue()) {
//                true -> mapOf(
//                    keyValue.key to ObjectMapperFactory.readValues(
//                        keyValue.value,
//                        typeRefMapAny
//                    )
//                )
//
//                false -> mapOf(keyValue.key to emptyMap())
//            }
//        }.toStream().toList()
        return CoroutineScope(Dispatchers.IO).async { redisConnection.mget(search.keys) }.await().map { keyValue ->
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
