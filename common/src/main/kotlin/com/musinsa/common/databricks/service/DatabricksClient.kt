package com.musinsa.common.databricks.service

import com.musinsa.common.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.common.databricks.dto.RetrieveQuery
import com.musinsa.common.error.CommonError
import com.musinsa.common.util.HttpClient
import com.musinsa.common.util.ObjectMapperFactory.readValue
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class DatabricksClient(
    private val httpClient: HttpClient
) {
    private val config = DatabricksHttpConnectionConfig()

    /**
     * 데이터브릭스에 저장된 쿼리를 가져온다.
     *
     * @param queryId 데이터브릭스 query uuid
     *
     * @return 쿼리
     */
    fun getDatabricksQuery(queryId: String): String {
        return try {
            val response = httpClient.getHttpResponse(
                StringBuilder().append(config.MUSINSA_DATA_WS_DOMAIN)
                    .append(config.RETRIEVE_API_PATH).append("/")
                    .append(queryId)
                    .toString(),
                Duration.ofSeconds(config.TIMEOUT.toLong()),
                arrayOf(httpClient.AUTHORIZATION, config.AUTHORIZATION_TOKEN)
            )
            readValue(response, RetrieveQuery::class.java).query
        } catch (e: Exception) {
            CommonError.FAIL_RETRIEVE_DATABRICKS_QUERY_RESULT.throwMe()
        }
    }
}