package com.musinsa.stat.sales.dto

/**
 * 집계 row를 가져온다.
 *
 * @param dailyList 쿼리 결과값
 *
 * @return 집계 row
 */
fun getIsGrouping(dailyList: MutableList<Daily>): Daily {
    return dailyList.first { it.isGrouping }
}

/**
 * 일별매출통계
 */
data class Daily(
    /**
     * 8자리 일자
     * ex) 20230502
     */
    val date: String?,
    override val isGrouping: Boolean,
    override val sellQuantity: Long,
    override val sellAmount: Long,
    override val refundQuantity: Long,
    override val refundAmount: Long,
    override val exchangeQuantity: Long,
    override val exchangeAmount: Long,
    override val tradeQuantity: Long,
    override val tradeAmount: Long,
    override val memberDiscounts: Long,
    override val affiliateDiscounts: Long,
    override val otherDiscounts: Long,
    override val couponDiscounts: Long,
    override val partnerCouponDiscounts: Long,
    override val pointDiscounts: Long,
    override val prePointDiscounts: Long,
    override val cartDiscounts: Long,
    override val groupDiscounts: Long,
    override val totalDiscounts: Long,
    override val discountRate: Double,
    override val paymentFees: Long,
    override val paymentAmount: Long,
    override val sales: Long,
    override val originalPrice: Long,
    override val profit: Long,
    override val profitMargin: Double,
    override val purchasesTradeAmount: Long,
    override val purchasesDiscounts: Long,
    override val purchasesPaymentFees: Long,
    override val purchasesPaymentAmount: Long,
    override val purchasesOriginalPrice: Long,
    override val purchasesProfit: Long,
    override val purchasesProfitMargin: Double,
    override val purchasesRatio: Double,
    override val partnerTradeAmount: Long,
    override val partnerDiscounts: Long,
    override val partnerPaymentFees: Long,
    override val partnerPaymentAmount: Long,
    override val partnerSellFees: Long,
    override val partnerFees: Long,
    override val partnerSellGrants: Long,
    override val partnerProfitMargin: Double,
    override val salesExcludedVAT: Long,
    override val originalPriceExcludedVAT: Long,
    override val profitExcludedVAT: Long,
    override val profitMarginExcludedVAT: Double
) : SalesStatistics(
    isGrouping,
    sellQuantity,
    sellAmount,
    refundQuantity,
    refundAmount,
    exchangeQuantity,
    exchangeAmount,
    tradeQuantity,
    tradeAmount,
    memberDiscounts,
    affiliateDiscounts,
    otherDiscounts,
    couponDiscounts,
    partnerCouponDiscounts,
    pointDiscounts,
    prePointDiscounts,
    cartDiscounts,
    groupDiscounts,
    totalDiscounts,
    discountRate,
    paymentFees,
    paymentAmount,
    sales,
    originalPrice,
    profit,
    profitMargin,
    purchasesTradeAmount,
    purchasesDiscounts,
    purchasesPaymentFees,
    purchasesPaymentAmount,
    purchasesOriginalPrice,
    purchasesProfit,
    purchasesProfitMargin,
    purchasesRatio,
    partnerTradeAmount,
    partnerDiscounts,
    partnerPaymentFees,
    partnerPaymentAmount,
    partnerSellFees,
    partnerFees,
    partnerSellGrants,
    partnerProfitMargin,
    salesExcludedVAT,
    originalPriceExcludedVAT,
    profitExcludedVAT,
    profitMarginExcludedVAT
)