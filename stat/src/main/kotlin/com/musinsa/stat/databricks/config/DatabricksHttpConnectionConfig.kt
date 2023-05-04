package com.musinsa.stat.databricks.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * REST API로 브릭레인 접속 설정값
 */

@ConfigurationProperties(prefix = "custom.databricks")
data class DatabricksHttpConnectionConfig(
    val httpConnection: Map<String, String> = mapOf()
) {
    val TIMEOUT by lazy { httpConnection["timeout"] }
    val MUSINSA_DATA_WS_DOMAIN by lazy { httpConnection["musinsa-data-ws-domain"] }
    val RETRIEVE_API_PATH by lazy { httpConnection["retrieve-api-path"] }
    val AUTHORIZATION_TOKEN by lazy { httpConnection["authorization-token"] }
}