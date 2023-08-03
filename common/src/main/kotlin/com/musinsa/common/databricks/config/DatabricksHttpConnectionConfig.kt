package com.musinsa.common.databricks.config

/**
 * REST API 브릭레인 접속 설정값
 */
data class DatabricksHttpConnectionConfig(
    val TIMEOUT: Int = 10,
    val MUSINSA_DATA_WS_DOMAIN: String = "https://musinsa-data-ws.cloud.databricks.com",
    val RETRIEVE_API_PATH: String = "/api/2.0/preview/sql/queries",

    // TODO Move AWS Secret Manager
    val STAT_AUTHORIZATION_TOKEN: String = "Bearer dapiac03c1cb4ac710049a572888106b65e3",

    // TODO Create Harrods AUTH TOKEN
    val HARRODS_AUTHORIZATION_TOKEN: String = ""
)
