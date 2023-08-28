package com.musinsa.harrods.error

import com.musinsa.common.error.CodeAwareException
import com.musinsa.common.error.Error
import org.springframework.http.HttpStatus

enum class ErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {

    INVALID_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 타입");

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}
