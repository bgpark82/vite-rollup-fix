package com.musinsa.stat.sales.dto

/**
 * 모든 매출통계에서 공통으로 사용
 */
abstract class SalesStatistics(

    /**
     * 합계 필드 구분
     * True: 합계 필드
     */
    open val isGrouping: Boolean,

    /**
     * 판매수량
     */
    open val sellQuantity: Long,

    /**
     * 판매금액
     */
    open val sellAmount: Long,

    /**
     * 환불수량
     */
    open val refundQuantity: Long,

    /**
     * 환불금액
     */
    open val refundAmount: Long,

    /**
     * 교환수량
     */
    open val exchangeQuantity: Long,

    /**
     * 교환금액
     */
    open val exchangeAmount: Long,

    /**
     * 거래수량
     */
    open val tradeQuantity: Long,

    /**
     * 거래금액
     */
    open val tradeAmount: Long,

    /**
     * 회원할인
     */
    open val memberDiscounts: Long,

    /**
     * 제휴할인
     */
    open val affiliateDiscounts: Long,

    /**
     * 기타할인
     */
    open val otherDiscounts: Long,

    /**
     * 쿠폰(무신사)할인
     */
    open val couponDiscounts: Long,

    /**
     * 쿠폰(업체)할인
     */
    open val partnerCouponDiscounts: Long,

    /**
     * 적립금할인
     */
    open val pointDiscounts: Long,

    /**
     * 선적립금할인
     */
    open val prePointDiscounts: Long,

    /**
     * 장바구니할인
     */
    open val cartDiscounts: Long,

    /**
     * 그룹할인
     */
    open val groupDiscounts: Long,

    /**
     * 소계(총 할인액)
     */
    open val totalDiscounts: Long,

    /**
     * 할인율
     */
    open val discountRate: Double,

    /**
     * 결제수수료
     */
    open val paymentFees: Long,

    /**
     * 결제금액
     */
    open val paymentAmount: Long,

    /**
     * 매출
     */
    open val sales: Long,

    /**
     * 원가
     */
    open val originalPrice: Long,

    /**
     * 이익
     */
    open val profit: Long,

    /**
     * 이익률
     */
    open val profitMargin: Double,

    /**
     * 매입상품_거래금액
     */
    open val purchasesTradeAmount: Long,

    /**
     * 매입상품_할인
     */
    open val purchasesDiscounts: Long,

    /**
     * 매입상품_결제수수료
     */
    open val purchasesPaymentFees: Long,

    /**
     * 매입상품_결제금액
     */
    open val purchasesPaymentAmount: Long,

    /**
     * 매입상품_원가
     */
    open val purchasesOriginalPrice: Long,

    /**
     * 매입상품_이익
     */
    open val purchasesProfit: Long,

    /**
     * 매입상품_이익율
     */
    open val purchasesProfitMargin: Double,

    /**
     * 매입상품_비중
     */
    open val purchasesRatio: Double,

    /**
     * 입점상품_거래금액
     */
    open val partnerTradeAmount: Long,

    /**
     * 입점상품_할인
     */
    open val partnerDiscounts: Long,

    /**
     * 입점상품_결제수수료
     */
    open val partnerPaymentFees: Long,

    /**
     * 입점상품_결제금액
     */
    open val partnerPaymentAmount: Long,

    /**
     * 입점상품_판매수수료
     */
    open val partnerSellFees: Long,

    /**
     * 입점상품_수수료
     */
    open val partnerFees: Long,

    /**
     * 입점상품_판매지원금
     */
    open val partnerSellGrants: Long,

    /**
     * 입점상품_이익률
     */
    open val partnerProfitMargin: Double,

    /**
     * 매출(VAT별도)
     */
    open val salesExcludedVAT: Long,

    /**
     * 원가(VAT별도)
     */
    open val originalPriceExcludedVAT: Long,

    /**
     * 이익(VAT별도)
     */
    open val profitExcludedVAT: Long,

    /**
     * 이익율(VAT별도)
     */
    open val profitMarginExcludedVAT: Double
)