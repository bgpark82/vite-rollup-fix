package com.musinsa.stat.sales.service

import com.musinsa.common.databricks.service.StatDatabricksClient
import com.musinsa.stat.sales.config.QueryStore
import com.musinsa.stat.sales.domain.GoodsKind
import com.musinsa.stat.sales.domain.Metric
import com.musinsa.stat.sales.domain.OrderBy
import com.musinsa.stat.sales.domain.OrderDirection
import com.musinsa.stat.sales.domain.PartnerType
import com.musinsa.stat.sales.domain.SalesFunnel
import com.musinsa.stat.sales.domain.SalesStart
import com.musinsa.stat.sales.dto.SalesStatisticsResponse
import com.musinsa.stat.sales.error.SalesError
import com.musinsa.stat.sales.service.QueryGenerator.generate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

const val RETRIEVE_LIMIT_YEAR = 1

@Service
class SalesService(
    @Qualifier("statDatabricksJdbcTemplate") private val jdbcTemplate: JdbcTemplate,
    private val queryStore: QueryStore,
    private val statDatabricksClient: StatDatabricksClient
) {
    /**
     * 매출통계를 가져온다.
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
     * @param partnerType 업체 구분
     * @param goodsKind 품목
     * @param salesFunnel 판매 경로
     * @param orderBy 정렬키
     * @param orderDirection 정렬 방향
     * @param pageSize 페이지 사이즈
     * @param page 페이지
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
        partnerId: List<String>? = emptyList(),
        category: List<String>? = emptyList(),
        styleNumber: List<String>? = emptyList(),
        goodsNumber: List<String>? = emptyList(),
        brandId: List<String>? = emptyList(),
        couponNumber: List<String>? = emptyList(),
        adCode: List<String>? = emptyList(),
        specialtyCode: List<String>? = emptyList(),
        mdId: List<String>? = emptyList(),
        partnerType: PartnerType? = null,
        goodsKind: GoodsKind? = null,
        salesFunnel: SalesFunnel,
        adHours: Long? = null,
        orderBy: OrderBy,
        orderDirection: OrderDirection,
        pageSize: Long,
        page: Long
    ): SalesStatisticsResponse {
        // 조회기간 유효성 체크
        retrieveDateValidCheck(startDate, endDate)

        // SQL 조립
        val originSql = generate(
            statDatabricksClient.getDatabricksQuery(
                queryStore.getQueryId(
                    metric
                )
            ),
            convertDate(shiftDate(startDate, metric)),
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
            partnerType = when (partnerType) {
                null -> null
                else -> partnerType.code.toString()
            },
            goodsKind = when (goodsKind) {
                null -> null
                else -> goodsKind.description
            },
            salesFunnel,
            adHours,
            orderBy.alias, metric, orderDirection.name, pageSize, page
        )

        return SalesStatisticsResponse(
            jdbcTemplate.query(
                originSql,
                RowMapperFactory.getRowMapper(metric)
            ),
            pageSize,
            page,
            originSql
        )
    }

    /**
     * 상품별 매출통계 유효성 체크를 위해 메소드 분리
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
     * @param partnerType 업체 구분
     * @param goodsKind 품목
     * @param salesFunnel 판매 경로
     * @param orderBy 정렬키
     * @param orderDirection 정렬 방향
     * @param pageSize 페이지 사이즈
     * @param page 페이지
     *
     * @return 매출통계 지표
     *
     * @throws SalesError.GOODS_STATISTICS_NEED_BRAND_PARTNER_GOODS_PARAMETERS
     */
    fun getGoodsSalesStatistics(
        metric: Metric,
        startDate: LocalDate,
        endDate: LocalDate,
        tag: List<String>? = emptyList(),
        salesStart: SalesStart,
        partnerId: List<String>? = emptyList(),
        category: List<String>? = emptyList(),
        styleNumber: List<String>? = emptyList(),
        goodsNumber: List<String>? = emptyList(),
        brandId: List<String>? = emptyList(),
        couponNumber: List<String>? = emptyList(),
        adCode: List<String>? = emptyList(),
        specialtyCode: List<String>? = emptyList(),
        mdId: List<String>? = emptyList(),
        partnerType: PartnerType? = null,
        goodsKind: GoodsKind? = null,
        salesFunnel: SalesFunnel,
        adHours: Long? = null,
        orderBy: OrderBy,
        orderDirection: OrderDirection,
        pageSize: Long,
        page: Long
    ): SalesStatisticsResponse {
        // 상품별 매출통계의 경우 적어도 하나의 업체 ID, 상품번호, 브랜드 ID, MD 값이 있어야 한다.
        if (partnerId.isNullOrEmpty() && goodsNumber.isNullOrEmpty() && brandId.isNullOrEmpty() && mdId.isNullOrEmpty()) {
            return SalesError.GOODS_STATISTICS_NEED_BRAND_PARTNER_GOODS_PARAMETERS.throwMe()
        }
        return getSalesStatistics(
            metric,
            startDate,
            endDate,
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
            partnerType,
            goodsKind,
            salesFunnel,
            adHours,
            orderBy,
            orderDirection,
            pageSize,
            page
        )
    }

    /**
     * 조회기간이 유효한지 확인한다.
     *
     * @param startDate 시작날짜
     * @param endDate 종료날짜
     *
     * @throws SalesError.NON_VALID_DATE_PERIOD
     */
    private fun retrieveDateValidCheck(
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        // 조회 기간 체크
        if (Period.between(startDate, endDate).years >= RETRIEVE_LIMIT_YEAR) {
            return SalesError.NON_VALID_DATE_PERIOD.throwMe()
        }

        // 조회시작 < 조회종료 체크
        if (startDate.isAfter(endDate)) {
            return SalesError.NON_VALID_DATE_PERIOD.throwMe()
        }
    }

    /**
     * 날짜를 쿼리에 사용하기 위해 String 으로 변환한다.
     *
     * @param date LocalDate 형식. ex) 2023-06-01
     *
     * @return "20230601"
     */
    private fun convertDate(date: LocalDate): String {
        return date.toString().filterNot { it == '-' }
    }

    /**
     * 월별 매출통계의 경우만 조회 시작 날짜를 1일로 지정한다.
     *
     * @param date 조회시작날짜
     * @param metric: 지표
     *
     * @return 조회시작날짜
     */
    private fun shiftDate(date: LocalDate, metric: Metric): LocalDate {
        if (metric == Metric.MONTLY) {
            return date.withDayOfMonth(1)
        }
        return date
    }
}
