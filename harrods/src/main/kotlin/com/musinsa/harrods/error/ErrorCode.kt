package com.musinsa.harrods.error

import com.musinsa.common.error.CodeAwareException
import com.musinsa.common.error.Error
import org.springframework.http.HttpStatus

enum class ErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {

    INVALID_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 타입"),
    UNSUPPORTED_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 파라미터 타입"),
    COMMENT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "쿼리 템플릿에 코멘트는 지원하지 않음")
    ;

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}
