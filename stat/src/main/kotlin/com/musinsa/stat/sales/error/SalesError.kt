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
    NON_VALID_DATE_PERIOD(HttpStatus.BAD_REQUEST, "유효하지 않은 조회기간."),
    UNKNOWN_SEARCH_PARAM(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "알려지지 않은 검색 파라미터"
    ),
    UNKNOWN_QUERY_RESULT_VALUE(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "알려 지지 않은 쿼리 결과 항목"
    )
    ;

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}