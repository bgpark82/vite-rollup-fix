package com.musinsa.stat.databricks.service

import aj.org.objectweb.asm.TypeReference
import com.musinsa.stat.databricks.config.DatabricksHttpConnectionConfig
import com.musinsa.stat.databricks.dto.RetrieveQuery
import com.musinsa.stat.util.ObjectMapperFactory
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
            val temp = ObjectMapperFactory.readValue(response.get().body(), RetrieveQuery.class)
            println(temp)
            println("TEST DONE")
            return temp.query
        } catch (e: Exception) {
            // TODO Exception throw
            println("ERROR")
            println(e.message)
        }

        return ""
    }
}