package com.musinsa.stat.sales.error

import com.musinsa.common.error.CodeAwareException
import com.musinsa.common.error.Error
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
    ),
    GOODS_STATISTICS_NEED_BRAND_PARTNER_GOODS_PARAMETERS(
        HttpStatus.BAD_REQUEST,
        "상품별 매출통계는 적어도 하나의 브랜드ID, 업체ID, 상품번호가 필요"
    )
    ;

    override fun <T> throwMe(): T {
        throw CodeAwareException(this)
    }

    override fun create(): CodeAwareException {
        return CodeAwareException(this)
    }
}