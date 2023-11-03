package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.error.SalesError

/**
 * 카테고리별 매출통계의 경우 쿼리 결과가 자체적으로 '집계(ROLL UP)' 되어 나온다.
 * 무의미한 값이 포함되어 있어, 해당 Row 는 삭제한다.
 *
 * @see <a href="https://wiki.musinsa.com/pages/viewpage.action?pageId=169800267">카테고리별 매출통계 특이성</a>
 */
class CategorySalesStatisticsResponse(
    jdbcQueryResult: List<SalesStatisticsMetric>,
    val pageSize: Long,
    val page: Long,
    originSql: String
) {

    init {
        // 검색 결과 없는 경우
        if (jdbcQueryResult.isEmpty()) {
            throw SalesError.NON_EXIST_SALES_STATISTICS_DATA.throwMe()
        }

        // 대분류 값이 없는 경우 결과값 Row 에서 삭제
        // TODO
    }
}
