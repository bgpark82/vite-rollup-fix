package com.musinsa.stat.error

import org.springframework.http.HttpStatus

/**
 * 모든 프로젝트 공통 에러
 */
enum class CommonError(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {
    FAIL_STRING_TO_JSON(HttpStatus.INTERNAL_SERVER_ERROR, "문자열 to JSON 변환에 실패"),
    FAIL_JSON_TO_STRING(HttpStatus.INTERNAL_SERVER_ERROR, "JSON to 문자열 변환에 실패")
    ;

    override fun <T> throwMe(): T {
        throw IntentionalRuntimeException(this)
    }

    override fun create(): IntentionalRuntimeException {
        return IntentionalRuntimeException(this)
    }
}