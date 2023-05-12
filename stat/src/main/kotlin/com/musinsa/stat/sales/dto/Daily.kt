package com.musinsa.stat.sales.dto

import java.sql.ResultSet

/**
 * 일별매출통계
 */
class Daily : SalesStatistics {
    /**
     * 8자리 일자
     * ex) 20230502
     */
    var date: String? = String()

    // JSON 역직렬화를 위해 빈 생성자 필요
    constructor()

    constructor(
        date: String?,
        isGrouping: Boolean,
        sellQuantity: Long,
        sellAmount: Long,
        refundQuantity: Long,
        refundAmount: Long,
        exchangeQuantity: Long,
        exchangeAmount: Long,
        tradeQuantity: Long,
        tradeAmount: Long,
        memberDiscounts: Long,
        affiliateDiscounts: Long,
        otherDiscounts: Long,
        couponDiscounts: Long,
        partnerCouponDiscounts: Long,
        pointDiscounts: Long,
        prePointDiscounts: Long,
        cartDiscounts: Long,
        groupDiscounts: Long,
        totalDiscounts: Long,
        discountRate: Double,
        paymentFees: Long,
        paymentAmount: Long,
        sales: Long,
        originalPrice: Long,
        profit: Long,
        profitMargin: Double,
        purchasesTradeAmount: Long,
        purchasesDiscounts: Long,
        purchasesPaymentFees: Long,
        purchasesPaymentAmount: Long,
        purchasesOriginalPrice: Long,
        purchasesProfit: Long,
        purchasesProfitMargin: Double,
        purchasesRatio: Double,
        partnerTradeAmount: Long,
        partnerDiscounts: Long,
        partnerPaymentFees: Long,
        partnerPaymentAmount: Long,
        partnerSellFees: Long,
        partnerFees: Long,
        partnerSellGrants: Long,
        partnerProfitMargin: Double,
        salesExcludedVAT: Long,
        originalPriceExcludedVAT: Long,
        profitExcludedVAT: Long,
        profitMarginExcludedVAT: Double
    ) : super(
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
    ) {
        this.date = date
    }

    constructor(rs: ResultSet, date: String?) : super(rs) {
        this.date = date
    }
}