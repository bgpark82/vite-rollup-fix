package com.musinsa.harrods.error

import com.musinsa.common.error.CodeAwareException
import com.musinsa.common.error.Error
import org.springframework.http.HttpStatus

const val UNSUPPORTED_PARAMETER_TYPE_MESSAGE = "지원하지 않는 파라미터 타입입니다"
const val COMMENT_NOT_ALLOWED_MESSAGE = "쿼리 템플릿에 코멘트는 지원하지 않습니다"
const val QUERY_ALREADY_EXIST_MESSAGE = "이미 등록된 쿼리입니다"
const val QUERY_NOT_FOUND_MESSAGE = "등록된 템플릿이 존재하지 않습니다"

enum class ErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {

    UNSUPPORTED_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, UNSUPPORTED_PARAMETER_TYPE_MESSAGE),
    COMMENT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, COMMENT_NOT_ALLOWED_MESSAGE),
    QUERY_ALREADY_EXIST(HttpStatus.BAD_REQUEST, QUERY_ALREADY_EXIST_MESSAGE),
    QUERY_NOT_FOUND(HttpStatus.NOT_FOUND, QUERY_NOT_FOUND_MESSAGE)
    ;

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}
