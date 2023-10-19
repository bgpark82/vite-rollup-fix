package com.musinsa.common.redis.service

import io.lettuce.core.KeyValue
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection
import org.apache.commons.pool2.impl.GenericObjectPool
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

//@Profile(value = ["dev", "prod"])
//@Service("redisConnection")
class RedisConnection(private val redisConnectionPool: GenericObjectPool<StatefulRedisClusterConnection<String, String>>) :
    AbstractRedisConnection {

    override fun mget(keys: Array<String>): List<KeyValue<String, String>> {
        val connection = redisConnectionPool.borrowObject()
        val result = connection.sync().mget(*keys)
        connection.close()
        return result
    }
}
