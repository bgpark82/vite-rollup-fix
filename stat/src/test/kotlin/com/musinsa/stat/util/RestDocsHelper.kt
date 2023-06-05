package com.musinsa.stat.util

import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * Spring REST Docs 문서 생성에 필요한 기능들
 */

private const val SCHEME = "https"
private const val HOST = "stat.data.musinsa.com"
private const val PORT = 443
private const val UTF8 = "UTF-8"

/**
 * MockMvc 생성
 * Spring REST Docs 에 필요한 설정들을 선언한다.
 */
fun buildMockMvc(
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
        requestBody(),
        responseBody()
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
        .withScheme(SCHEME)

    return MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter(UTF8, true))
        .apply<DefaultMockMvcBuilder>(mockMvcRestDocumentationConfigurer)
        .build()
}

/**
 * HTTP GET
 *
 * @param url 호스트
 * @param pathParam 경로 params
 * @param queryParams Query Params
 *
 * @return ResultActions
 *
 */
fun MockMvc.GET(
    url: String,
    pathParam: String,
    queryParams: MultiValueMap<String, String>
): ResultActions {
    return this.perform(
        RestDocumentationRequestBuilders.get(url, pathParam)
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(queryParams)
    )
}

/**
 * HTTP GET
 *
 * @param url 호스트
 * @param queryParams Query Params
 *
 * @return ResultActions
 *
 */
fun MockMvc.GET(
    url: String,
    queryParams: MultiValueMap<String, String>
): ResultActions {
    return this.perform(
        RestDocumentationRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .queryParams(queryParams)
    )
}

fun ResultActions.성공_검증(예상_응답: String): ResultActions {
    return this.andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(content().string(예상_응답))
}

/**
 * PathVariable, QueryParameters, ResponseBody 가 있는 DOCS 생성
 */
fun ResultActions.DOCS_생성(
    documentUrl: String,
    pathParams: List<ParameterDescriptor>,
    queryParams: List<ParameterDescriptor>,
    responseFields: List<FieldDescriptor>
): ResultActions {
    return this
        .andDo(
            document(
                documentUrl,
                pathParameters(pathParams),
                queryParameters(queryParams),
                responseFields(responseFields)
            )
        )
}

/**
 * QueryParameters, ResponseBody 가 있는 DOCS 생성
 */
fun ResultActions.DOCS_생성(
    documentUrl: String,
    queryParams: List<ParameterDescriptor>,
    responseFields: List<FieldDescriptor>
): ResultActions {
    return this
        .andDo(
            document(
                documentUrl,
                queryParameters(queryParams),
                responseFields(responseFields)
            )
        )
}

/**
 * QueryParameters 가 있는 DOCS 생성
 */
fun ResultActions.DOCS_생성(
    documentUrl: String,
    queryParams: List<ParameterDescriptor>
): ResultActions {
    return this
        .andDo(
            document(
                documentUrl,
                queryParameters(queryParams)
            )
        )
}
