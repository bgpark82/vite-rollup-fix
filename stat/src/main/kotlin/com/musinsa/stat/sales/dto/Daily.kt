package com.musinsa.stat.sales.dto

/**
 * 일별매출통계
 */
data class Daily(
    /**
     * 8자리 일자
     * ex) 20230502
     */
    val date: String?,

    /**
     * 합계 필드 구분
     * True: 합계 필드
     */
    val isGrouping: Boolean,

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
    val profitMarginExcludedVAT: Double
)