package com.musinsa.stat.databricks.service

import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
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
        return HttpRequest.newBuilder(URI(uri)).GET().timeout(Duration.ofSeconds(config.TIMEOUT!!.toLong()))
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

    fun getDatabricksQuery(queryId: String): String {
        try {
            val response = getHttpResponse(
                StringBuilder().append(config.MUSINSA_DATA_WS_DOMAIN).append(config.RETRIEVE_API_PATH).append("/")
                    .append(queryId)
                    .toString()
            )
            return response.get().body()
        } catch (e: Exception) {
            // TODO Exception throw
        }

        return ""
    }
}