package com.musinsa.commonwebflux.restdoc

import com.musinsa.common.error.CommonError
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.requestBody
import org.springframework.restdocs.payload.PayloadDocumentation.responseBody
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadSubsectionExtractor
import org.springframework.restdocs.snippet.Attributes
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.reactive.function.BodyInserters

/**
 * Spring REST Docs 문서 생성에 필요한 기능들
 */
private const val BASE_URL = "https://*.data.musinsa.com"
private const val ENUM_SNIPPET =
    "enum-response"    // 해당 값이 testFixtures/resources/org/springframework/restdocs/templates/asciidoctor/enum-response-fields.snippet 을 읽는다.


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

/**
 * Enum 노출용 WebTestClient 생성
 * Spring REST Docs 에 필요한 설정들을 선언한다.
 */
fun buildEnumWebTestClient(
    applicationContext: ApplicationContext,
    restDocumentation: RestDocumentationContextProvider
): WebTestClient {
    val restDocumentationConfigurer = documentationConfiguration(
        restDocumentation
    )

    // 스니펫 설정 없음
    restDocumentationConfigurer.snippets().withDefaults()

    return WebTestClient.bindToApplicationContext(applicationContext)
        .configureClient()
        .filter(restDocumentationConfigurer)
        .build()
}

/**
 * HTTP GET
 *
 * @param url 호스트
 *
 * @return WebTestClient.ResponseSpec
 *
 */
fun WebTestClient.GET(
    url: String
): WebTestClient.ResponseSpec {
    return this.get().uri(url).accept(MediaType.APPLICATION_JSON).exchange()
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
fun WebTestClient.POST(
    url: String,
    body: String
): WebTestClient.ResponseSpec {
    return this.post().uri(url).accept(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(body)).exchange()
}

/**
 * ENUM DOCS 생성
 *
 * @param documentUrl 생성 경로
 * @param enumValues Map 형태의 Enum
 */
fun WebTestClient.ResponseSpec.ENUM_DOCS_생성(
    documentUrl: String,
    enumValues: Map<String, String>
): WebTestClient.BodyContentSpec {
    return this.expectBody().consumeWith(
        document(
            documentUrl,
            enumResponseFields(
                ENUM_SNIPPET,
                enumValues.map { enum ->
                    PayloadDocumentation.fieldWithPath(enum.key)
                        .description(
                            enum.value
                        )
                }.toList()
            )
        )
    )
}

/**
 * ResponseBody 가 있는 DOCS 생성
 */
@JvmName("DOCS_생성_RESPONSE_BODY")
fun WebTestClient.ResponseSpec.DOCS_생성(
    documentUrl: String,
    responseFields: List<FieldDescriptor>
): WebTestClient.BodyContentSpec {
    return this.expectBody().consumeWith(
        document(
            documentUrl,
            responseFields(responseFields)
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

fun WebTestClient.ResponseSpec.유효하지_않은_요청값_검증(invalidField: String) {
//    this.expectStatus().isBadRequest
    this.andExpect(MockMvcResultMatchers.status().isBadRequest)
        .andExpect(
            MockMvcResultMatchers.jsonPath("errorCode")
                .value(CommonError.INVALID_REQUEST_VALUE.name)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("invalidField")
                .value(invalidField)
        )
}
