package com.musinsa.common.error

import org.springframework.http.HttpStatus

/**
 * 모든 프로젝트 공통 에러
 */
enum class CommonError(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {
    UNKNOWN_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "알 수 없는 에러"
    ),
    FAIL_CONVERT_STRING_TO_JSON(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "문자열 to JSON 변환에 실패"
    ),
    FAIL_CONVERT_JSON_TO_STRING(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "JSON to 문자열 변환에 실패"
    ),
    CONSTRAINT_VIOLATED_VALUE(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "제약조건 위반"
    ),
    INVALID_REQUEST_VALUE(
        HttpStatus.BAD_REQUEST,
        "유효하지 않은 요청값"
    )
    ;

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}