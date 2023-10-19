package com.musinsa.common.redis.service

import io.lettuce.core.KeyValue
import io.lettuce.core.api.StatefulRedisConnection
import org.apache.commons.pool2.impl.GenericObjectPool
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

//@Profile(value = ["local", "test"])
//@Service("redisConnection")
class LocalRedisConnection(private val redisConnectionPool: GenericObjectPool<StatefulRedisConnection<String, String>>) :
    AbstractRedisConnection {

    override fun mget(keys: Array<String>): List<KeyValue<String, String>> {
        val connection = redisConnectionPool.borrowObject()
        val result = connection.sync().mget(*keys)
        connection.close()
        return result
    }
}
