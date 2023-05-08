package com.musinsa.stat.databricks.service

import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.stat.databricks.dto.RetrieveQuery
import com.musinsa.stat.util.ObjectMapperFactory.readValue
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.CompletableFuture

@Service
class DatabricksClient(private val config: DatabricksHttpConnectionConfig) {
    /**
     * GET HTTP Request 생성
     *
     * @param uri URI
     */
    private fun getHttpRequest(uri: String): HttpRequest {
        return HttpRequest.newBuilder(URI(uri)).GET()
            .timeout(Duration.ofSeconds(config.TIMEOUT!!.toLong()))
            .header("Authorization", config.AUTHORIZATION_TOKEN)
            .build()
    }

    /**
     * 비동기로 HTTP 호출
     *
     * @param uri URI
     */
    private fun getHttpResponse(uri: String): CompletableFuture<HttpResponse<String>> {
        return HttpClient.newHttpClient().sendAsync(
            getHttpRequest(uri),
            HttpResponse.BodyHandlers.ofString()
        )
    }

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
                    .toString()
            )

            return readValue(
                response.get().body(), RetrieveQuery::class
                    .java
            ).query
        } catch (e: Exception) {
            // TODO 예외처리 추가
            throw Exception(e)
        }
    }
}