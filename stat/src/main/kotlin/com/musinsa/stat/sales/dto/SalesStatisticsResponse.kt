package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.error.SalesError
import com.musinsa.stat.sales.service.PREFIX_ANNOTATION
import java.util.stream.Stream

/**
 * 매출통계 API 응답
 */
class SalesStatisticsResponse(
    jdbcQueryResult: List<SalesStatisticsMetric>,
    val pageSize: Long,
    val page: Long,
    originSql: String
) {
    // 합계
    val sum: SalesStatisticsMetric

    // 평균
    val average: SalesStatisticsMetric

    // 모든 페이지 갯수
    val totalPages: Long

    // 결과값
    val content: List<SalesStatisticsMetric>

    // 모든 아이템 갯수
    val totalItems: Long

    // SQL
    val sql: String

    init {
        // 검색 결과 없는 경우
        if (jdbcQueryResult.isEmpty()) {
            throw SalesError.NON_EXIST_SALES_STATISTICS_DATA.throwMe()
        }

        content = jdbcQueryResult
        totalItems = jdbcQueryResult[0].total
        totalPages = when {
            totalItems % pageSize > 0 -> totalItems / pageSize + 1
            else -> totalItems / pageSize
        }

        val sellQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.sellQuantity }
        )
        val sellAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.sellAmount }
        )
        val refundQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.refundQuantity }
        )
        val refundAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.refundAmount }
        )
        val exchangeQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.exchangeQuantity }
        )
        val exchangeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.exchangeAmount }
        )
        val tradeQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.tradeQuantity }
        )
        val tradeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.tradeAmount }
        )
        val memberDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.memberDiscounts }
        )
        val affiliateDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.affiliateDiscounts }
        )
        val otherDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.otherDiscounts }
        )
        val couponDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.couponDiscounts }
        )
        val partnerCouponDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerCouponDiscounts }
        )
        val pointDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.pointDiscounts }
        )
        val prePointDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.prePointDiscounts }
        )
        val cartDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.cartDiscounts }
        )
        val groupDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.groupDiscounts }
        )
        val totalDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.totalDiscounts }
        )
        val discountRate = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.discountRate }
        )
        val paymentFees = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.paymentFees }
        )
        val paymentAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.paymentAmount }
        )
        val sales = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.sales }
        )
        val originalPrice = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.originalPrice }
        )
        val profit = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.profit }
        )
        val profitMargin = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.profitMargin }
        )
        val purchasesTradeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesTradeAmount }
        )
        val purchasesDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesDiscounts }
        )
        val purchasesPaymentFees = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesPaymentFees }
        )
        val purchasesPaymentAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesPaymentAmount }
        )
        val purchasesOriginalPrice = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesOriginalPrice }
        )
        val purchasesProfit = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesProfit }
        )
        val purchasesProfitMargin = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesProfitMargin }
        )
        val purchasesRatio = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.purchasesRatio }
        )
        val partnerTradeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerTradeAmount }
        )
        val partnerDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerDiscounts }
        )
        val partnerPaymentFees = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerPaymentFees }
        )
        val partnerPaymentAmount = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerPaymentAmount }
        )
        val partnerSellFees = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerSellFees }
        )
        val partnerFees = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerFees }
        )
        val partnerSellGrants = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerSellGrants }
        )
        val partnerProfitMargin = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.partnerProfitMargin }
        )
        val salesExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.salesExcludedVAT }
        )
        val originalPriceExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.originalPriceExcludedVAT }
        )
        val profitExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.profitExcludedVAT }
        )
        val profitMarginExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream().map { it.profitMarginExcludedVAT }
        )

        // 합계
        sum = SalesStatisticsMetric(
            sellQuantity.first,
            sellAmount.first,
            refundQuantity.first,
            refundAmount.first,
            exchangeQuantity.first,
            exchangeAmount.first,
            tradeQuantity.first,
            tradeAmount.first,
            memberDiscounts.first,
            affiliateDiscounts.first,
            otherDiscounts.first,
            couponDiscounts.first,
            partnerCouponDiscounts.first,
            pointDiscounts.first,
            prePointDiscounts.first,
            cartDiscounts.first,
            groupDiscounts.first,
            totalDiscounts.first,
            discountRate.first,
            paymentFees.first,
            paymentAmount.first,
            sales.first,
            originalPrice.first,
            profit.first,
            profitMargin.first,
            purchasesTradeAmount.first,
            purchasesDiscounts.first,
            purchasesPaymentFees.first,
            purchasesPaymentAmount.first,
            purchasesOriginalPrice.first,
            purchasesProfit.first,
            purchasesProfitMargin.first,
            purchasesRatio.first,
            partnerTradeAmount.first,
            partnerDiscounts.first,
            partnerPaymentFees.first,
            partnerPaymentAmount.first,
            partnerSellFees.first,
            partnerFees.first,
            partnerSellGrants.first,
            partnerProfitMargin.first,
            salesExcludedVAT.first,
            originalPriceExcludedVAT.first,
            profitExcludedVAT.first,
            profitMarginExcludedVAT.first,
            0
        )

        // 평균
        average = SalesStatisticsMetric(
            sellQuantity.second,
            sellAmount.second,
            refundQuantity.second,
            refundAmount.second,
            exchangeQuantity.second,
            exchangeAmount.second,
            tradeQuantity.second,
            tradeAmount.second,
            memberDiscounts.second,
            affiliateDiscounts.second,
            otherDiscounts.second,
            couponDiscounts.second,
            partnerCouponDiscounts.second,
            pointDiscounts.second,
            prePointDiscounts.second,
            cartDiscounts.second,
            groupDiscounts.second,
            totalDiscounts.second,
            discountRate.second,
            paymentFees.second,
            paymentAmount.second,
            sales.second,
            originalPrice.second,
            profit.second,
            profitMargin.second,
            purchasesTradeAmount.second,
            purchasesDiscounts.second,
            purchasesPaymentFees.second,
            purchasesPaymentAmount.second,
            purchasesOriginalPrice.second,
            purchasesProfit.second,
            purchasesProfitMargin.second,
            purchasesRatio.second,
            partnerTradeAmount.second,
            partnerDiscounts.second,
            partnerPaymentFees.second,
            partnerPaymentAmount.second,
            partnerSellFees.second,
            partnerFees.second,
            partnerSellGrants.second,
            partnerProfitMargin.second,
            salesExcludedVAT.second,
            originalPriceExcludedVAT.second,
            profitExcludedVAT.second,
            profitMarginExcludedVAT.second,
            0
        )
    }

    /**
     * SQL 에서 주석을 포함한 라인을 제거한다.
     * 개행문자는 공백으로 치환한다.
     */
    init {
        sql = originSql.lines().filterNot { it.contains(PREFIX_ANNOTATION) }
            .toList()
            .joinToString(separator = "\n").trimIndent().replace("\n", " ")
    }

    /**
     * 합계와 평균을 구한다.
     */
    @JvmName("calculateSumAndAverageLong")
    private fun calculateSumAndAverage(stream: Stream<Long>): Pair<Long, Long> {
        val list = stream.toList()
        return Pair(list.sum(), list.average().toLong())
    }

    /**
     * 합계는 0으로 표기하며, 평균을 소수점 2자리까지 구한다.
     */
    @JvmName("calculateSumAndAverageDouble")
    private fun calculateSumAndAverage(stream: Stream<Double>): Pair<Double, Double> {
        val list = stream.toList()
        return Pair(0.0, String.format("%.2f", list.average()).toDouble())
    }
}
