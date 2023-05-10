package com.musinsa.stat.sales.domain

import com.musinsa.stat.sales.dto.Daily
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * 매출통계 RowMapper
 */
object SalesStatisticsRowMapper : RowMapper<Daily> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Daily {
        return Daily(
            date = rs.getString("date"),
            sellQuantity = rs.getLong("sellQuantity"),
            sellAmount = rs.getLong("sellAmount"),
            refundQuantity = rs.getLong("refundQuantity"),
            refundAmount = rs.getLong("refundAmount"),
            exchangeQuantity = rs.getLong("exchangeQuantity"),
            exchangeAmount = rs.getLong("exchangeAmount"),
            tradeQuantity = rs.getLong("tradeQuantity"),
            tradeAmount = rs.getLong("tradeAmount"),
            memberDiscounts = rs.getLong("memberDiscounts"),
            affiliateDiscounts = rs.getLong("affiliateDiscounts"),
            otherDiscounts = rs.getLong("otherDiscounts"),
            couponDiscounts = rs.getLong("couponDiscounts"),
            partnerCouponDiscounts = rs.getLong("partnerCouponDiscounts"),
            pointDiscounts = rs.getLong("pointDiscounts"),
            prePointDiscounts = rs.getLong("prePointDiscounts"),
            cartDiscounts = rs.getLong("cartDiscounts"),
            groupDiscounts = rs.getLong("groupDiscounts"),
            totalDiscounts = rs.getLong("totalDiscounts"),
            discountRate = rs.getDouble("discountRate"),
            paymentFees = rs.getLong("paymentFees"),
            paymentAmount = rs.getLong("paymentAmount"),
            sales = rs.getLong("sales"),
            originalPrice = rs.getLong("originalPrice"),
            profit = rs.getLong("profit"),
            profitMargin = rs.getDouble("profitMargin"),
            purchasesTradeAmount = rs.getLong("purchasesTradeAmount"),
            purchasesDiscounts = rs.getLong("purchasesDiscounts"),
            purchasesPaymentFees = rs.getLong("purchasesPaymentFees"),
            purchasesPaymentAmount = rs.getLong("purchasesPaymentAmount"),
            purchasesOriginalPrice = rs.getLong("purchasesOriginalPrice"),
            purchasesProfit = rs.getLong("purchasesProfit"),
            purchasesProfitMargin = rs.getDouble("purchasesProfitMargin"),
            purchasesRatio = rs.getDouble("purchasesRatio"),
            partnerTradeAmount = rs.getLong("partnerTradeAmount"),
            partnerDiscounts = rs.getLong("partnerDiscounts"),
            partnerPaymentFees = rs.getLong("partnerPaymentFees"),
            partnerPaymentAmount = rs.getLong("partnerPaymentAmount"),
            partnerSellFees = rs.getLong("partnerSellFees"),
            partnerFees = rs.getLong("partnerFees"),
            partnerSellGrants = rs.getLong("partnerSellGrants"),
            partnerProfitMargin = rs.getDouble("partnerProfitMargin"),
            salesExcludedVAT = rs.getLong("salesExcludedVAT"),
            originalPriceExcludedVAT = rs.getLong("originalPriceExcludedVAT"),
            profitExcludedVAT = rs.getLong("profitExcludedVAT"),
            profitMarginExcludedVAT = rs.getDouble("profitMarginExcludedVAT")
        )
    }
}