package com.musinsa.common.redis.service

import io.lettuce.core.KeyValue
import reactor.core.publisher.Flux

/**
 * 로컬, 개발, 운영환경에서 Redis Connection Pool 사용을 위해 생성한 상위 인터페이스
 *
 * StatefulRedisClusterConnection, StatefulRedisConnection 에서 reactive()를 동시에 사용할 수 없어, 추상 클래스가 아닌 인터페이스로 생성
 */
interface AbstractRedisConnection {
    /**
     * Redis CLI mget 실행
     *
     * @param keys 키 리스트
     *
     * @return 키에 매치되는 값
     */
    fun mget(keys: Array<String>): List<KeyValue<String, String>>
}
