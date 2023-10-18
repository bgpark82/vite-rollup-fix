package com.musinsa.common.redis.config

import io.lettuce.core.RedisURI
import io.lettuce.core.SocketOptions
import io.lettuce.core.TimeoutOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import io.lettuce.core.cluster.RedisClusterClient
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands
import io.lettuce.core.cluster.models.partitions.RedisClusterNode
import io.lettuce.core.resource.DefaultClientResources
import io.lettuce.core.resource.Delay
import io.lettuce.core.resource.DirContextDnsResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * 해롯 Redis 연결 정보
 *
 * @see <a href="https://docs.aws.amazon.com/AmazonElastiCache/latest/red-ug/BestPractices.Clients-lettuce.html">ElastiCache Client 개발문서</a>
 */
@Profile(value = ["dev", "prod"])
@Suppress("PrivatePropertyName")
@Configuration
class RedisReactiveDataSourceConfig(
    @Value("\${spring.data.redis.host}")
    private val CLUSTER_CONFIG_ENDPOINT: String,

    @Value("\${spring.data.redis.port}")
    private val PORT: Int
) {
    // 레디스 클러스터와 관련된 Slow 쿼리 타임아웃
    private val META_COMMAND_TIMEOUT = Duration.ofMillis(1000)

    // 레디스 읽기/쓰기를 포함한 간단한 쿼리 타임아웃
    private val DEFAULT_COMMAND_TIMEOUT = Duration.ofMillis(250)

    // 레디스 클러스터 연결 타임아웃
    private val CONNECT_TIMEOUT = Duration.ofMillis(100)

    /**
     * Redis Cluster 를 Node 기입없이 사용하기 위한 Bean
     * Redis Cluster 오토 스케일링 사용을 위해서 필수.
     */
    @Bean
    fun redisConnection(): StatefulRedisClusterConnection<String, String> {
        // Redis Endpoint 설정
        val redisUriCluster =
            RedisURI.Builder.redis(CLUSTER_CONFIG_ENDPOINT).withPort(PORT)
                .withSsl(true).build()

        // Retry 전략(Random 한 시간으로 Retry 사이의 시간을 설정)
        val clientResources = DefaultClientResources.builder()
            .reconnectDelay(
                Delay.fullJitter(
                    Duration.ofMillis(100),
                    Duration.ofSeconds(10),
                    100,
                    TimeUnit.MILLISECONDS
                )
            )
            // TODO Deprecated 처리
            .dnsResolver(DirContextDnsResolver())
            .build()

        // Redis 클러스터 Client 생성
        val redisClusterClient =
            RedisClusterClient.create(clientResources, redisUriCluster)

        // 타잉아웃 설정
        val timeoutOptions = TimeoutOptions.builder()
            .timeoutSource(
                DynamicClusterTimeout(
                    DEFAULT_COMMAND_TIMEOUT,
                    META_COMMAND_TIMEOUT
                )
            )
            .build()

        // 노드 교체나 시스템 점검으로 구성이 바뀔 시, Topology 정보 갱신
        val topologyOptions = ClusterTopologyRefreshOptions.builder()
            .enableAllAdaptiveRefreshTriggers() // 잦은 Refresh 트리거 막음
            .enablePeriodicRefresh() // Topology 정보 감지 시간(default: 60)
            .dynamicRefreshSources(true) // true: 발견된 모든 노드로부터 Topology 정보를 얻어온다.
            .build()

        // Socket 속성
        val socketOptions = SocketOptions.builder()
            .connectTimeout(CONNECT_TIMEOUT)
            .keepAlive(true) // TCP 커넥션 유지
            .build()

        // 최종 설정값 적용
        val clusterClientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(topologyOptions)
            .socketOptions(socketOptions)
            .autoReconnect(true) // 자동 재연결
            .timeoutOptions(timeoutOptions)
            .nodeFilter {
                !(
                    it.`is`(RedisClusterNode.NodeFlag.FAIL) ||
                        it.`is`(RedisClusterNode.NodeFlag.EVENTUAL_FAIL) ||
                        it.`is`(RedisClusterNode.NodeFlag.NOADDR)
                    )
            }
            .validateClusterNodeMembership(false)
            .build()

        redisClusterClient.setOptions(clusterClientOptions)

        return redisClusterClient.connect()
    }

    /**
     * Redis Commands
     */
    @Bean
    fun redisCommands(): RedisAdvancedClusterReactiveCommands<String, String>? {
        return this.redisConnection().reactive()
    }
}
