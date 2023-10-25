package com.musinsa.common.restdoc

import com.musinsa.common.error.CommonError
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.string
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestBody
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseBody
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadSubsectionExtractor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * Spring REST Docs 문서 생성에 필요한 기능들
 */
private const val SCHEME = "https"
private const val HOST = "data.musinsa.com"
private const val PORT = 443
private const val UTF8 = "UTF-8"
private const val ENUM_SNIPPET =
    "enum-response"    // 해당 값이 testFixtures/resources/org/springframework/restdocs/templates/asciidoctor/enum-response-fields.snippet 을 읽는다.

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
 * Enum 노출용 MockMvc 생성
 * Spring REST Docs 에 필요한 설정들을 선언한다.
 */
fun buildEnumMockMvc(
    webApplicationContext: WebApplicationContext,
    restDocumentation: RestDocumentationContextProvider
): MockMvc {
    val mockMvcRestDocumentationConfigurer = documentationConfiguration(
        restDocumentation
    )
    // 스니펫 설정 없음
    mockMvcRestDocumentationConfigurer.snippets().withDefaults()

    return MockMvcBuilders.webAppContextSetup(webApplicationContext)
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

/**
 * HTTP GET
 *
 * @param url 호스트
 *
 * @return ResultActions
 *
 */
fun MockMvc.GET(
    url: String
): ResultActions {
    return this.perform(
        RestDocumentationRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON)
    )
}

/**
 * HTTP POST
 *
 * @param url 호스트
 * @param body RequestBody
 *
 * @return ResultActions
 *
 */
fun MockMvc.POST(
    url: String,
    body: String
): ResultActions {
    return this.perform(
        RestDocumentationRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
    )
}

fun ResultActions.성공_검증(예상_응답: String): ResultActions {
    return this.andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(content().string(예상_응답))
}

/**
 * Coroutine suspend 메소드 테스트용도
 *
 * @see https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework/async-requests.html
 */
fun ResultActions.성공_검증_AWAIT(예상_응답: Any): ResultActions {
    return this.andExpect(MockMvcResultMatchers.status().isOk)
        .andExpect(request().asyncStarted())
        .andExpect(request().asyncResult(예상_응답))
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
@JvmName("DOCS_생성_QUERY_PARAMETERS")
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

/**
 * ResponseBody 가 있는 DOCS 생성
 */
@JvmName("DOCS_생성_RESPONSE_BODY")
fun ResultActions.DOCS_생성(
    documentUrl: String,
    responseFields: List<FieldDescriptor>
): ResultActions {
    return this
        .andDo(
            document(
                documentUrl,
                responseFields(responseFields)
            )
        )
}

/**
 * ENUM DOCS 생성
 *
 * @param documentUrl 생성 경로
 * @param enumValues Map 형태의 Enum
 */
fun ResultActions.ENUM_DOCS_생성(
    documentUrl: String,
    enumValues: Map<String, String>
): ResultActions {
    return this
        .andDo(
            document(
                documentUrl,
                enumResponseFields(
                    ENUM_SNIPPET,
                    enumValues.map { enum ->
                        fieldWithPath(enum.key).description(
                            enum.value
                        )
                    }.toList()
                )
            )
        )
}

/**
 * Enum Snippet 을 읽고, Docs 로 생성한다.
 *
 * @param type snippet template 이름(ENUM_SNIPPET)
 * @param descriptors Docs 변환할 값(API 응답값)
 * @param attributes Docs 로 생성될 속성값
 * @param subsectionExtractor descriptors 가 포함된 경로(응답값 json 경로)
 */
private fun enumResponseFields(
    type: String,
    descriptors: List<FieldDescriptor>,
    attributes: Map<String, Any> = Attributes.attributes(
        Attributes.key("name").value("description")
    ),
    subsectionExtractor: PayloadSubsectionExtractor<*>? = null
): EnumResponseFieldsSnippet {
    return EnumResponseFieldsSnippet(
        type = type,
        descriptors = descriptors,
        attributes = attributes,
        subsectionExtractor = subsectionExtractor
    )
}

/**
 * Enum 값을 링크 형태로 노출시킬때의 공통 로직
 *
 * @param name 변수명
 * @param documentUrl Enum Docs 생성 위치
 * @param description 변수명 설명
 */
fun ENUM_LINK_DOCS_BUILDER(
    name: String,
    documentUrl: String,
    description: String
): ParameterDescriptor {
    return parameterWithName(name).description(
        "link:../$documentUrl.html[$description,window=blank]"
    )
}

fun ResultActions.유효하지_않은_요청값_검증(invalidField: String) {
    this.andExpect(MockMvcResultMatchers.status().isBadRequest)
        .andExpect(
            jsonPath("errorCode")
                .value(CommonError.INVALID_REQUEST_VALUE.name)
        ).andExpect(
            jsonPath("invalidField")
                .value(invalidField)
        )
}
