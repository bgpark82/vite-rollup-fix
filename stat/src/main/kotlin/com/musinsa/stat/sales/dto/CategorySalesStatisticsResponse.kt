package com.musinsa.stat.sales.dto

import com.musinsa.stat.sales.error.SalesError
import java.util.stream.Stream

/**
 * 카테고리별 매출통계의 경우 쿼리 결과가 자체적으로 '집계(ROLL UP)' 되어 나온다.
 * 무의미한 값이 포함되어 있어, 해당 Row 는 삭제한다.
 *
 * @see <a href="https://wiki.musinsa.com/pages/viewpage.action?pageId=169800267">카테고리별 매출통계 특이성</a>
 */
class CategorySalesStatisticsResponse(
    jdbcQueryResult: List<Category>,

    // 페이지 사이즈
    val pageSize: Long,

    // 페이지
    val page: Long,
    originSql: String
) {
    // 합계
    val sum: SalesStatisticsMetric

    // 평균
    val average: SalesStatisticsMetric

    // 결과값
    val content: List<Category>

    init {
        // 검색 결과 없는 경우
        if (jdbcQueryResult.isEmpty()) {
            throw SalesError.NON_EXIST_SALES_STATISTICS_DATA.throwMe()
        }

        // 대분류 값이 없는 경우는 불필요한 쿼리 결과이므로, 결과값 Row 에서 삭제
        content = jdbcQueryResult.filter {
            !it.largeCategoryCode.isNullOrBlank()
        }

        // 합계, 평균의 경우 소분류 카테고리명이 없는 결과값은 제외하고 계산한다.
        // Why? 이미 대분류/중분류에서의 합계는 하위 중/소분류의 집계 결과이기 때문에 중복 집계를 막기 위해
        val sellQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.sellQuantity }
        )
        val sellAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.sellAmount }
        )
        val refundQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.refundQuantity }
        )
        val refundAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.refundAmount }
        )
        val exchangeQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.exchangeQuantity }
        )
        val exchangeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.exchangeAmount }
        )
        val tradeQuantity = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.tradeQuantity }
        )
        val tradeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.tradeAmount }
        )
        val memberDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.memberDiscounts }
        )
        val affiliateDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.affiliateDiscounts }
        )
        val otherDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.otherDiscounts }
        )
        val couponDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.couponDiscounts }
        )
        val partnerCouponDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerCouponDiscounts }
        )
        val pointDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.pointDiscounts }
        )
        val prePointDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.prePointDiscounts }
        )
        val cartDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.cartDiscounts }
        )
        val groupDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.groupDiscounts }
        )
        val totalDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.totalDiscounts }
        )
        val discountRate = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.discountRate }
        )
        val paymentFees = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.paymentFees }
        )
        val paymentAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.paymentAmount }
        )
        val sales = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.sales }
        )
        val originalPrice = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.originalPrice }
        )
        val profit = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.profit }
        )
        val profitMargin = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.profitMargin }
        )
        val purchasesTradeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesTradeAmount }
        )
        val purchasesDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesDiscounts }
        )
        val purchasesPaymentFees = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesPaymentFees }
        )
        val purchasesPaymentAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesPaymentAmount }
        )
        val purchasesOriginalPrice = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesOriginalPrice }
        )
        val purchasesProfit = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesProfit }
        )
        val purchasesProfitMargin = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesProfitMargin }
        )
        val purchasesRatio = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.purchasesRatio }
        )
        val partnerTradeAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerTradeAmount }
        )
        val partnerDiscounts = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerDiscounts }
        )
        val partnerPaymentFees = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerPaymentFees }
        )
        val partnerPaymentAmount = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerPaymentAmount }
        )
        val partnerSellFees = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerSellFees }
        )
        val partnerFees = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerFees }
        )
        val partnerSellGrants = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerSellGrants }
        )
        val partnerProfitMargin = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.partnerProfitMargin }
        )
        val salesExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.salesExcludedVAT }
        )
        val originalPriceExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.originalPriceExcludedVAT }
        )
        val profitExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.profitExcludedVAT }
        )
        val profitMarginExcludedVAT = calculateSumAndAverage(
            jdbcQueryResult.stream()
                .filter { !it.smallCategory.isNullOrBlank() }
                .map { it.profitMarginExcludedVAT }
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
