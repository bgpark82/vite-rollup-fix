package com.musinsa.stat.util

import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * Spring REST Docs 문서 생성에 필요한 기능들
 */

private const val HOST = "stat.data.musinsa.com"
private const val PORT = 80
private const val UTF8 = "UTF-8"

/**
 * MockMvc 확장함수
 * Spring REST Docs 에 필요한 설정들을 선언한다.
 */
fun MockMvc.buildMockMvc(
    webApplicationContext: WebApplicationContext,
    restDocumentation: RestDocumentationContextProvider
): MockMvc {
    val mockMvcRestDocumentationConfigurer = documentationConfiguration(
        restDocumentation
    )

    // 스니펫 설정
    mockMvcRestDocumentationConfigurer.snippets().withDefaults(
        HttpDocumentation.httpRequest(),
        HttpDocumentation.httpResponse(),
        PayloadDocumentation.requestBody(),
        PayloadDocumentation.responseBody()
    )

    // 헤더 삭제, Json 값 시각화 정렬
    mockMvcRestDocumentationConfigurer.operationPreprocessors()
        .withRequestDefaults(
            prettyPrint(),
            modifyHeaders().remove("Content-Length")
        )
        .withResponseDefaults(
            prettyPrint(),
            modifyHeaders().remove("Vary").remove("Content-Length")
        )

    // Host, port 설정
    mockMvcRestDocumentationConfigurer.uris().withHost(HOST).withPort(PORT)

    return MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter(UTF8, true))
        .apply<DefaultMockMvcBuilder>(mockMvcRestDocumentationConfigurer)
        .build()
}

