package com.musinsa.stat.sales.error

import com.musinsa.stat.error.Error
import com.musinsa.stat.error.IntentionalRuntimeException
import org.springframework.http.HttpStatus

/**
 * 매출통계와 관련된 에러
 */
enum class SalesError(
    override val httpStatus: HttpStatus,
    override val message: String
) : Error {
    NON_EXISTENT_METRIC(HttpStatus.BAD_REQUEST, "존재하지 않는 metric"),
    NON_VALID_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 조회기간.");

    override fun <T> throwMe(): T {
        throw IntentionalRuntimeException(this)
    }

    override fun create(): IntentionalRuntimeException {
        return IntentionalRuntimeException(this)
    }
}