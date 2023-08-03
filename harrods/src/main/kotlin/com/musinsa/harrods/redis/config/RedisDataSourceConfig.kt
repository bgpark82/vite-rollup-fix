package com.musinsa.harrods.redis.config

import io.lettuce.core.RedisURI
import io.lettuce.core.SocketOptions
import io.lettuce.core.TimeoutOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import io.lettuce.core.cluster.RedisClusterClient
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection
import io.lettuce.core.cluster.models.partitions.RedisClusterNode
import io.lettuce.core.resource.DefaultClientResources
import io.lettuce.core.resource.Delay
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.concurrent.TimeUnit


/**
 * 해롯 Redis 연결 정보
 *
 * @see <a href="https://docs.aws.amazon.com/AmazonElastiCache/latest/red-ug/BestPractices.Clients-lettuce.html">ElastiCache Client 개발문서</a>
 */
@Suppress("PrivatePropertyName")
@Configuration
class RedisDataSourceConfig(
    @Value("custom.redis.cluster.configuration-endpoint")
    val clusterConfigurationEndpoint: String
) {
    private val META_COMMAND_TIMEOUT = Duration.ofMillis(1000)
    private val DEFAULT_COMMAND_TIMEOUT = Duration.ofMillis(250)
    private val CONNECT_TIMEOUT = Duration.ofMillis(100)

    @Bean
    fun redisDataSource(): StatefulRedisClusterConnection<String, String> {
        // Redis Endpoint 연결
        val redisUriCluster =
            RedisURI.Builder.redis(clusterConfigurationEndpoint).withPort(6379)
                .withSsl(true).build()

        // 연결 설정값 수정
        val clientResources = DefaultClientResources.builder()
            .reconnectDelay(
                Delay.fullJitter(
                    Duration.ofMillis(100),  // minimum 100 millisecond delay
                    Duration.ofSeconds(10),  // maximum 10 second delay
                    100, TimeUnit.MILLISECONDS // 100 millisecond base
                )
            )
            // dnsResolver 메소드 Deprecated 되어 DNS Resolver 디폴트값 사용
//            .dnsResolver(DirContextDnsResolver())
            .build()

        // Redis 클러스터 Client 생성
        val redisClusterClient =
            RedisClusterClient.create(clientResources, redisUriCluster)

        val timeoutOptions = TimeoutOptions.builder()
            .timeoutSource(
                DynamicClusterTimeout(
                    DEFAULT_COMMAND_TIMEOUT,
                    META_COMMAND_TIMEOUT
                )
            )
            .build()

        // Configure the topology refreshment optionts
        val topologyOptions = ClusterTopologyRefreshOptions.builder()
            .enableAllAdaptiveRefreshTriggers()
            .enablePeriodicRefresh()
            .dynamicRefreshSources(true)
            .build()

        // Configure the socket options
        val socketOptions = SocketOptions.builder()
            .connectTimeout(CONNECT_TIMEOUT)
            .keepAlive(true)
            .build()

        // Configure the client's options
        val clusterClientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(topologyOptions)
            .socketOptions(socketOptions)
            .autoReconnect(true)
            .timeoutOptions(timeoutOptions)
            .nodeFilter { it: RedisClusterNode ->
                !(it.`is`(RedisClusterNode.NodeFlag.FAIL)
                    || it.`is`(RedisClusterNode.NodeFlag.EVENTUAL_FAIL)
                    || it.`is`(RedisClusterNode.NodeFlag.NOADDR))
            }
            .validateClusterNodeMembership(false)
            .build()

        redisClusterClient.setOptions(clusterClientOptions)

        return redisClusterClient.connect()
    }
}
