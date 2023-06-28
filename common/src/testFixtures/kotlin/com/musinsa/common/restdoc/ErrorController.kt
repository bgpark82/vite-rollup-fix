package com.musinsa.common.restdoc

import com.musinsa.common.error.ErrorResponse
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Error Body 아스키독 노출을 위한 테스트 전용 컨트롤러
 */
@RestController
@RequestMapping("/test/common/error-body")
class ErrorController {
    @GetMapping
    fun errorBody(): ErrorResponse {
        return ErrorResponse(
            errorCode = "INVALID_REQUEST_VALUE",
            exception = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException",
            invalidField = "metric",
            invalidValue = "APPLE",
            message = "에러 메시지"
        )
    }
}

fun 에러_명세_가져오기(): List<FieldDescriptor> {
    return listOf(
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
            .description("예외 메시지")
    )
}