package com.musinsa.stat.sales.error

import com.musinsa.stat.error.CodeAwareException
import com.musinsa.stat.error.Error
import org.springframework.http.HttpStatus

/**
 * 매출통계와 관련된 에러
 */
enum class SalesError(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {
    NON_VALID_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 조회기간."),
    NON_EXIST_TARGET_FIELD(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "쿼리에 해당 문자열이 존재하지 않음"
    );

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}