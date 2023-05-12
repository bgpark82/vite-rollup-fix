package com.musinsa.stat.sales.dto

import java.sql.ResultSet

/**
 * 모든 매출통계에서 공통으로 사용
 */
open class SalesStatistics {

    /**
     * 합계 필드 구분
     * True: 합계 필드
     */
    var isGrouping: Boolean = false

    /**
     * 판매수량
     */
    var sellQuantity: Long = 0

    /**
     * 판매금액
     */
    var sellAmount: Long = 0

    /**
     * 환불수량
     */
    var refundQuantity: Long = 0

    /**
     * 환불금액
     */
    var refundAmount: Long = 0

    /**
     * 교환수량
     */
    var exchangeQuantity: Long = 0

    /**
     * 교환금액
     */
    var exchangeAmount: Long = 0

    /**
     * 거래수량
     */
    var tradeQuantity: Long = 0

    /**
     * 거래금액
     */
    var tradeAmount: Long = 0

    /**
     * 회원할인
     */
    var memberDiscounts: Long = 0

    /**
     * 제휴할인
     */
    var affiliateDiscounts: Long = 0

    /**
     * 기타할인
     */
    var otherDiscounts: Long = 0

    /**
     * 쿠폰(무신사)할인
     */
    var couponDiscounts: Long = 0

    /**
     * 쿠폰(업체)할인
     */
    var partnerCouponDiscounts: Long = 0

    /**
     * 적립금할인
     */
    var pointDiscounts: Long = 0

    /**
     * 선적립금할인
     */
    var prePointDiscounts: Long = 0

    /**
     * 장바구니할인
     */
    var cartDiscounts: Long = 0

    /**
     * 그룹할인
     */
    var groupDiscounts: Long = 0

    /**
     * 소계(총 할인액)
     */
    var totalDiscounts: Long = 0

    /**
     * 할인율
     */
    var discountRate: Double = 0.0

    /**
     * 결제수수료
     */
    var paymentFees: Long = 0

    /**
     * 결제금액
     */
    var paymentAmount: Long = 0

    /**
     * 매출
     */
    var sales: Long = 0

    /**
     * 원가
     */
    var originalPrice: Long = 0

    /**
     * 이익
     */
    var profit: Long = 0

    /**
     * 이익률
     */
    var profitMargin: Double = 0.0

    /**
     * 매입상품_거래금액
     */
    var purchasesTradeAmount: Long = 0

    /**
     * 매입상품_할인
     */
    var purchasesDiscounts: Long = 0

    /**
     * 매입상품_결제수수료
     */
    var purchasesPaymentFees: Long = 0

    /**
     * 매입상품_결제금액
     */
    var purchasesPaymentAmount: Long = 0

    /**
     * 매입상품_원가
     */
    var purchasesOriginalPrice: Long = 0

    /**
     * 매입상품_이익
     */
    var purchasesProfit: Long = 0

    /**
     * 매입상품_이익율
     */
    var purchasesProfitMargin: Double = 0.0

    /**
     * 매입상품_비중
     */
    var purchasesRatio: Double = 0.0

    /**
     * 입점상품_거래금액
     */
    var partnerTradeAmount: Long = 0

    /**
     * 입점상품_할인
     */
    var partnerDiscounts: Long = 0

    /**
     * 입점상품_결제수수료
     */
    var partnerPaymentFees: Long = 0

    /**
     * 입점상품_결제금액
     */
    var partnerPaymentAmount: Long = 0

    /**
     * 입점상품_판매수수료
     */
    var partnerSellFees: Long = 0

    /**
     * 입점상품_수수료
     */
    var partnerFees: Long = 0

    /**
     * 입점상품_판매지원금
     */
    var partnerSellGrants: Long = 0

    /**
     * 입점상품_이익률
     */
    var partnerProfitMargin: Double = 0.0

    /**
     * 매출(VAT별도)
     */
    var salesExcludedVAT: Long = 0

    /**
     * 원가(VAT별도)
     */
    var originalPriceExcludedVAT: Long = 0

    /**
     * 이익(VAT별도)
     */
    var profitExcludedVAT: Long = 0

    /**
     * 이익율(VAT별도)
     */
    var profitMarginExcludedVAT: Double = 0.0

    // JSON 역직렬화를 위해 빈 생성자 필요
    constructor()
    constructor(
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
    ) {
        this.isGrouping = isGrouping
        this.sellQuantity = sellQuantity
        this.sellAmount = sellAmount
        this.refundQuantity = refundQuantity
        this.refundAmount = refundAmount
        this.exchangeQuantity = exchangeQuantity
        this.exchangeAmount = exchangeAmount
        this.tradeQuantity = tradeQuantity
        this.tradeAmount = tradeAmount
        this.memberDiscounts = memberDiscounts
        this.affiliateDiscounts = affiliateDiscounts
        this.otherDiscounts = otherDiscounts
        this.couponDiscounts = couponDiscounts
        this.partnerCouponDiscounts = partnerCouponDiscounts
        this.pointDiscounts = pointDiscounts
        this.prePointDiscounts = prePointDiscounts
        this.cartDiscounts = cartDiscounts
        this.groupDiscounts = groupDiscounts
        this.totalDiscounts = totalDiscounts
        this.discountRate = discountRate
        this.paymentFees = paymentFees
        this.paymentAmount = paymentAmount
        this.sales = sales
        this.originalPrice = originalPrice
        this.profit = profit
        this.profitMargin = profitMargin
        this.purchasesTradeAmount = purchasesTradeAmount
        this.purchasesDiscounts = purchasesDiscounts
        this.purchasesPaymentFees = purchasesPaymentFees
        this.purchasesPaymentAmount = purchasesPaymentAmount
        this.purchasesOriginalPrice = purchasesOriginalPrice
        this.purchasesProfit = purchasesProfit
        this.purchasesProfitMargin = purchasesProfitMargin
        this.purchasesRatio = purchasesRatio
        this.partnerTradeAmount = partnerTradeAmount
        this.partnerDiscounts = partnerDiscounts
        this.partnerPaymentFees = partnerPaymentFees
        this.partnerPaymentAmount = partnerPaymentAmount
        this.partnerSellFees = partnerSellFees
        this.partnerFees = partnerFees
        this.partnerSellGrants = partnerSellGrants
        this.partnerProfitMargin = partnerProfitMargin
        this.salesExcludedVAT = salesExcludedVAT
        this.originalPriceExcludedVAT = originalPriceExcludedVAT
        this.profitExcludedVAT = profitExcludedVAT
        this.profitMarginExcludedVAT = profitMarginExcludedVAT
    }

    constructor(rs: ResultSet) : this(
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