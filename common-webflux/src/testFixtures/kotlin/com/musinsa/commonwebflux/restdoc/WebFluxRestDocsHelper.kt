package com.musinsa.commonwebflux.restdoc

import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.requestBody
import org.springframework.restdocs.payload.PayloadDocumentation.responseBody
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Spring REST Docs 문서 생성에 필요한 기능들
 */
private const val UTF8 = "UTF-8"
private const val BASE_URL = "https://*.data.musinsa.com"

/**
 * WebTestClient 생성
 * Spring REST Docs 에 필요한 설정들을 선언한다.
 */
fun buildWebTestClient(
    applicationContext: ApplicationContext,
    restDocumentation: RestDocumentationContextProvider
): WebTestClient {
    val restDocumentationConfigurer = documentationConfiguration(
        restDocumentation
    )

    // 스니펫 설정
    restDocumentationConfigurer.snippets().withDefaults(
        HttpDocumentation.httpRequest(),
        HttpDocumentation.httpResponse(),
        requestBody(),
        responseBody()
    )

    // 헤더 삭제, Json 값 시각화 정렬
    restDocumentationConfigurer.operationPreprocessors()
        .withRequestDefaults(
            prettyPrint(),
            modifyHeaders().remove("Content-Length")
        )
        .withResponseDefaults(
            prettyPrint(),
            modifyHeaders().remove("Vary").remove("Content-Length")
        )

    return WebTestClient.bindToApplicationContext(applicationContext)
        .configureClient().baseUrl(BASE_URL)
        .filter(restDocumentationConfigurer)
        .build()
}
