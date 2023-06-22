package com.musinsa.stat.restdoc

import com.musinsa.common.error.ErrorResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Error Body 아스키독 노출을 위한 테스트 전용 컨트롤러
 */
@RestController
@RequestMapping("/test/error-body")
internal class ErrorController {
    @GetMapping
    fun errorBody(): ErrorResponse {
        return ErrorResponse(
            errorCode = "INVALID_REQUEST_VALUE",
            exception = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException",
            invalidField = "metric",
            invalidValue = "Apple",
            message = "에러 메시지"
        )
    }
}