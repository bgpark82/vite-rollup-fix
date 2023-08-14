package com.musinsa.common.redis.config

import io.lettuce.core.TimeoutOptions
import io.lettuce.core.protocol.CommandType
import io.lettuce.core.protocol.ProtocolKeyword
import io.lettuce.core.protocol.RedisCommand
import java.time.Duration

/**
 * Redis 관리형 커맨드로 인한 지연을 방지하기 위한 타임아웃 설정
 */
class DynamicClusterTimeout(
    private val defaultCommandTimeout: Duration,
    private val metaCommandTimeout: Duration
) : TimeoutOptions.TimeoutSource() {

    @Suppress("PrivatePropertyName")
    private val META_COMMAND_TYPES: Set<ProtocolKeyword> =
        setOf(
            // 현재 연결된 레디스 노드의 모든 Key, Value 삭제
            CommandType.FLUSHDB,

            // 전체 레디스 클러스터의 모든 Key, Value 삭제
            CommandType.FLUSHALL,

            // 클러스터 명령 관련
            CommandType.CLUSTER,

            // Redis 서버 정보와 통계값 조회
            CommandType.INFO,

            // Key 조회
            CommandType.KEYS
        )

    override fun getTimeout(command: RedisCommand<*, *, *>): Long {
        return if (META_COMMAND_TYPES.contains(command.type)) {
            metaCommandTimeout.toMillis()
        } else {
            defaultCommandTimeout.toMillis()
        }
    }
}
