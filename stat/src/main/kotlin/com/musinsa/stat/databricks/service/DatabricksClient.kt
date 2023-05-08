package com.musinsa.stat.databricks.service

import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.stat.databricks.dto.RetrieveQuery
import com.musinsa.stat.util.HttpClient.AUTHORIZATION
import com.musinsa.stat.util.HttpClient.getHttpResponse
import com.musinsa.stat.util.ObjectMapperFactory.readValue
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class DatabricksClient(private val config: DatabricksHttpConnectionConfig) {
    /**
     * 데이터브릭스에 저장된 쿼리를 가져온다.
     *
     * @param queryId 데이터브릭스 query uuid
     *
     * @return 쿼리
     */
    fun getDatabricksQuery(queryId: String): String {
        try {
            val response = getHttpResponse(
                StringBuilder().append(config.MUSINSA_DATA_WS_DOMAIN)
                    .append(config.RETRIEVE_API_PATH).append("/")
                    .append(queryId)
                    .toString(),
                Duration.ofSeconds(config.TIMEOUT!!.toLong()),
                arrayOf(AUTHORIZATION, config.AUTHORIZATION_TOKEN!!)
            )
            return readValue(
                response.get().body(),
                RetrieveQuery::class.java
            ).query
        } catch (e: Exception) {
            // TODO 예외처리 추가
            throw Exception(e)
        }
    }
}