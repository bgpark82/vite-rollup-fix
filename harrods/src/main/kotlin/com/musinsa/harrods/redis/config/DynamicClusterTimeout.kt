package com.musinsa.harrods.redis.config

import io.lettuce.core.TimeoutOptions
import io.lettuce.core.protocol.CommandType
import io.lettuce.core.protocol.ProtocolKeyword
import io.lettuce.core.protocol.RedisCommand
import java.time.Duration

/**
 * 관리형 커맨드로 인한 지연을 방지하기 위한 타임아웃 적용
 */
class DynamicClusterTimeout(
    private val defaultCommandTimeout: Duration,
    private val metaCommandTimeout: Duration
) :
    TimeoutOptions.TimeoutSource() {

    @Suppress("PrivatePropertyName")
    private val META_COMMAND_TYPES: Set<ProtocolKeyword> =
        setOf(
            CommandType.FLUSHDB,
            CommandType.FLUSHALL,
            CommandType.CLUSTER,
            CommandType.INFO,
            CommandType.KEYS
        )

    override fun getTimeout(command: RedisCommand<*, *, *>): Long {
        return if (META_COMMAND_TYPES.contains(command.type)) {
            metaCommandTimeout.toMillis()
        } else defaultCommandTimeout.toMillis()
    }
}
