package com.musinsa.common.util

import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * HTTP Client
 */
@Component
class HttpClient {
    @Suppress("PropertyName")
    val AUTHORIZATION = "Authorization"

    /**
     * GET HTTP Request 생성
     *
     * @param uri URI
     * @param timeout 타임아웃
     * @param headers HTTP 헤더 문자열
     *
     * @return HTTPRequest
     */
    private fun getHttpRequest(
        uri: String,
        timeout: Duration,
        headers: Array<String>
    ): HttpRequest {
        return HttpRequest.newBuilder(URI(uri)).GET()
            .timeout(timeout)
            .headers(*headers)
            .build()
    }

    /**
     * 비동기로 HTTP 호출
     *
     * @param uri URI
     * @param timeout 타임아웃. 기본값: 10초
     * @param headers 헤더
     *
     * @return 결과값 Body
     */
    fun getHttpResponse(
        uri: String,
        timeout: Duration = Duration.ofSeconds(10), headers: Array<String>
    ): String {
        return HttpClient.newHttpClient().sendAsync(
            getHttpRequest(uri, timeout, headers),
            HttpResponse.BodyHandlers.ofString()
        ).get().body()
    }
}