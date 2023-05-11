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
            date = rs.getString("date") ?: "SUM",
            isGrouping = rs.getBoolean("집계"),
            sellQuantity = rs.getLong("판매수량"),
            sellAmount = rs.getLong("판매금액"),
            refundQuantity = rs.getLong("환불수량"),
            refundAmount = rs.getLong("환불금액"),
            exchangeQuantity = rs.getLong("교환수량"),
            exchangeAmount = rs.getLong("교환금액"),
            tradeQuantity = rs.getLong("거래수량"),
            tradeAmount = rs.getLong("거래금액"),
            memberDiscounts = rs.getLong("회원할인"),
            affiliateDiscounts = rs.getLong("제휴할인"),
            otherDiscounts = rs.getLong("기타할인"),
            couponDiscounts = rs.getLong("쿠폰(무신사)할인"),
            partnerCouponDiscounts = rs.getLong("쿠폰(업체)할인"),
            pointDiscounts = rs.getLong("적립금할인"),
            prePointDiscounts = rs.getLong("선적립금할인"),
            cartDiscounts = rs.getLong("장바구니할인"),
            groupDiscounts = rs.getLong("그룹할인"),
            totalDiscounts = rs.getLong("소계"),
            discountRate = rs.getDouble("할인율"),
            paymentFees = rs.getLong("결제수수료"),
            paymentAmount = rs.getLong("결제금액"),
            sales = rs.getLong("매출"),
            originalPrice = rs.getLong("원가"),
            profit = rs.getLong("이익"),
            profitMargin = rs.getDouble("이익률"),
            purchasesTradeAmount = rs.getLong("매입상품_거래금액"),
            purchasesDiscounts = rs.getLong("매입상품_할인"),
            purchasesPaymentFees = rs.getLong("매입상품_결제수수료"),
            purchasesPaymentAmount = rs.getLong("매입상품_결제금액"),
            purchasesOriginalPrice = rs.getLong("매입상품_원가"),
            purchasesProfit = rs.getLong("매입상품_이익"),
            purchasesProfitMargin = rs.getDouble("매입상품_이익율"),
            purchasesRatio = rs.getDouble("매입상품_비중"),
            partnerTradeAmount = rs.getLong("입점상품_거래금액"),
            partnerDiscounts = rs.getLong("입점상품_할인"),
            partnerPaymentFees = rs.getLong("입점상품_결제수수료"),
            partnerPaymentAmount = rs.getLong("입점상품_결제금액"),
            partnerSellFees = rs.getLong("입점상품_판매수수료"),
            partnerFees = rs.getLong("입점상품_수수료"),
            partnerSellGrants = rs.getLong("입점상품_판매지원금"),
            partnerProfitMargin = rs.getDouble("입점상품_이익률"),
            salesExcludedVAT = rs.getLong("매출(VAT별도)"),
            originalPriceExcludedVAT = rs.getLong("원가(VAT별도)"),
            profitExcludedVAT = rs.getLong("이익(VAT별도)"),
            profitMarginExcludedVAT = rs.getDouble("이익률(VAT별도)")
        )
    }
}