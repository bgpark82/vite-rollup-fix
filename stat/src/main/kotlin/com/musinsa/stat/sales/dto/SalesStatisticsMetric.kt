package com.musinsa.stat.sales.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.ResultSet

/**
 * 모든 매출통계에서 공통으로 사용
 */
open class SalesStatisticsMetric(
    /**
     * 판매수량
     */
    val sellQuantity: Long,
    /**
     * 판매금액
     */
    val sellAmount: Long,
    /**
     * 환불수량
     */
    val refundQuantity: Long,
    /**
     * 환불금액
     */
    val refundAmount: Long,
    /**
     * 교환수량
     */
    val exchangeQuantity: Long,
    /**
     * 교환금액
     */
    val exchangeAmount: Long,
    /**
     * 거래수량
     */
    val tradeQuantity: Long,
    /**
     * 거래금액
     */
    val tradeAmount: Long,
    /**
     * 회원할인
     */
    val memberDiscounts: Long,
    /**
     * 제휴할인
     */
    val affiliateDiscounts: Long,
    /**
     * 기타할인
     */
    val otherDiscounts: Long,
    /**
     * 쿠폰(무신사)할인
     */
    val couponDiscounts: Long,
    /**
     * 쿠폰(업체)할인
     */
    val partnerCouponDiscounts: Long,
    /**
     * 적립금할인
     */
    val pointDiscounts: Long,
    /**
     * 선적립금할인
     */
    val prePointDiscounts: Long,
    /**
     * 장바구니할인
     */
    val cartDiscounts: Long,
    /**
     * 그룹할인
     */
    val groupDiscounts: Long,
    /**
     * 소계(총 할인액)
     */
    val totalDiscounts: Long,
    /**
     * 할인율
     */
    val discountRate: Double,
    /**
     * 결제수수료
     */
    val paymentFees: Long,
    /**
     * 결제금액
     */
    val paymentAmount: Long,
    /**
     * 매출
     */
    val sales: Long,
    /**
     * 원가
     */
    val originalPrice: Long,
    /**
     * 이익
     */
    val profit: Long,
    /**
     * 이익률
     */
    val profitMargin: Double,
    /**
     * 매입상품_거래금액
     */
    val purchasesTradeAmount: Long,
    /**
     * 매입상품_할인
     */
    val purchasesDiscounts: Long,
    /**
     * 매입상품_결제수수료
     */
    val purchasesPaymentFees: Long,
    /**
     * 매입상품_결제금액
     */
    val purchasesPaymentAmount: Long,
    /**
     * 매입상품_원가
     */
    val purchasesOriginalPrice: Long,
    /**
     * 매입상품_이익
     */
    val purchasesProfit: Long,
    /**
     * 매입상품_이익율
     */
    val purchasesProfitMargin: Double,
    /**
     * 매입상품_비중
     */
    val purchasesRatio: Double,
    /**
     * 입점상품_거래금액
     */
    val partnerTradeAmount: Long,
    /**
     * 입점상품_할인
     */
    val partnerDiscounts: Long,
    /**
     * 입점상품_결제수수료
     */
    val partnerPaymentFees: Long,
    /**
     * 입점상품_결제금액
     */
    val partnerPaymentAmount: Long,
    /**
     * 입점상품_판매수수료
     */
    val partnerSellFees: Long,
    /**
     * 입점상품_수수료
     */
    val partnerFees: Long,
    /**
     * 입점상품_판매지원금
     */
    val partnerSellGrants: Long,
    /**
     * 입점상품_이익률
     */
    val partnerProfitMargin: Double,
    /**
     * 매출(VAT별도)
     */
    val salesExcludedVAT: Long,
    /**
     * 원가(VAT별도)
     */
    val originalPriceExcludedVAT: Long,
    /**
     * 이익(VAT별도)
     */
    val profitExcludedVAT: Long,
    /**
     * 이익율(VAT별도)
     */
    val profitMarginExcludedVAT: Double,

    /**
     * 총 Row 수
     */
    @JsonIgnore
    val total: Long
) {

    constructor(rs: ResultSet) : this(
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
        profitMarginExcludedVAT = rs.getDouble("이익률(VAT별도)"),
        total = rs.getLong("total")
    )
}
