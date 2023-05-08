package com.musinsa.stat.databricks.service

import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.stat.databricks.dto.RetrieveQuery
import com.musinsa.stat.util.HttpClient
import com.musinsa.stat.util.ObjectMapperFactory.readValue
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class DatabricksClient(
    private val config: DatabricksHttpConnectionConfig,
    private val httpClient: HttpClient
) {
    /**
     * 데이터브릭스에 저장된 쿼리를 가져온다.
     *
     * @param queryId 데이터브릭스 query uuid
     *
     * @return 쿼리
     */
    fun getDatabricksQuery(queryId: String): String {
        try {
            val response = httpClient.getHttpResponse(
                StringBuilder().append(config.MUSINSA_DATA_WS_DOMAIN)
                    .append(config.RETRIEVE_API_PATH).append("/")
                    .append(queryId)
                    .toString(),

                // TODO 타임아웃 주입 못받으면 에러 throw
                Duration.ofSeconds(config.TIMEOUT?.toLong() ?: 10),

                // TODO 토큰 주입 못받으면 에러 throw
                arrayOf(
                    httpClient.AUTHORIZATION,
                    config.AUTHORIZATION_TOKEN ?: "test"
                )
            )
            return readValue(response, RetrieveQuery::class.java).query
        } catch (e: Exception) {
            // TODO 예외처리 추가
            e.printStackTrace()
            throw Exception(e)
        }
    }
}