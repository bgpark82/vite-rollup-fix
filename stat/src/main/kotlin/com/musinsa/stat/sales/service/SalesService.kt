package com.musinsa.stat.sales.service

import com.musinsa.stat.databricks.service.DatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.error.SalesError
import com.musinsa.stat.sales.service.QueryGenerator.generate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

private const val RETRIEVE_LIMIT_YEAR = 1

@Service
class SalesService(
    @Qualifier("databricksJdbcTemplate") private val jdbcTemplate: JdbcTemplate,
    private val queryStore: QueryStore,
    private val databricksClient: DatabricksClient
) {
    /**
     * 일별 매출통계를 가져온다.
     *
     * @param metric 매출통계 유형
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     * @param tag 태그. 기본값: 빈배열
     * @param salesStart 매출시점
     * @param partnerId 업체
     * @param category 카테고리
     * @param styleNumber 스타일넘버
     * @param goodsNumber 상품코드
     * @param brandId 브랜드
     * @param couponNumber 쿠폰
     * @param adCode 광고코드
     * @param specialtyCode 전문관코드
     * @param mdId 담당MD
     * @param orderBy 정렬키
     *
     * @return 매출통계 지표
     *
     */
    fun getSalesStatistics(
        metric: Metric,
        startDate: LocalDate,
        endDate: LocalDate,
        tag: List<String>? = emptyList(),
        salesStart: SalesStart,
        partnerId: String? = String(),
        category: String? = String(),
        styleNumber: String? = String(),
        goodsNumber: String? = String(),
        brandId: String? = String(),
        couponNumber: String? = String(),
        adCode: String? = String(),
        specialtyCode: String? = String(),
        mdId: String? = String(),
        orderBy: OrderBy
    ): SalesStatisticsResponse {
        // 조회기간 유효성 체크
        retrieveDateValidCheck(startDate, endDate)

        return SalesStatisticsResponse(
            jdbcTemplate.query(
                generate(
                    databricksClient.getDatabricksQuery(
                        queryStore.getQueryId(
                            metric
                        )
                    ),
                    convertDate(startDate),
                    convertDate(endDate),
                    tag,
                    salesStart,
                    partnerId,
                    category,
                    styleNumber,
                    goodsNumber,
                    brandId,
                    couponNumber,
                    adCode,
                    specialtyCode,
                    mdId,
                    orderBy.alias, metric
                ), RowMapperFactory.getRowMapper(metric)
            )
        )
    }

    /**
     * 조회기간이 유효한지 확인한다.
     *
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     */
    private fun retrieveDateValidCheck(
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        // 조회 기간 체크
        if (Period.between(startDate, endDate).years >= RETRIEVE_LIMIT_YEAR)
            return SalesError.NON_VALID_DATE_PERIOD.throwMe()

        // 조회시작 < 조회종료 체크
        if (startDate.isAfter(endDate))
            return SalesError.NON_VALID_DATE_PERIOD.throwMe()
    }

    /**
     * 날짜를 쿼리에 사용하기 위해 String 으로 변환한다.
     *
     * @param date LocalDate 형식
     */
    private fun convertDate(date: LocalDate): String {
        return date.toString().filterNot { it == '-' }
    }
}