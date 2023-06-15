package com.musinsa.stat.restdoc

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

@WebMvcTest(controllers = [ErrorController::class])
class ErrorControllerTest : RestDocsControllerHelper() {
    @Test
    fun 에러_BODY_가져오기() {
        mockMvc.GET("/test/error-body")
            .DOCS_생성(
                "error-body", listOf(
                    fieldWithPath("errorCode")
                        .type(JsonFieldType.STRING)
                        .description("에러코드"),
                    fieldWithPath("exception")
                        .type(JsonFieldType.STRING)
                        .description("예외종류"),
                    fieldWithPath("invalidField")
                        .type(JsonFieldType.STRING)
                        .description("입력오류 필드. default: Empty"),
                    fieldWithPath("invalidValue")
                        .type(JsonFieldType.STRING)
                        .description("입력오류 값. default: Empty"),
                    fieldWithPath("message")
                        .type(JsonFieldType.STRING)
                        .description("예외 메시지"),
                )
            )
    }
}