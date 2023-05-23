package com.musinsa.stat.sales.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType.NUMBER
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

/**
 * Spring REST Docs 명세에 사용
 */
fun 매출통계_공통_명세(): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = mutableListOf()

    명세서.addAll(
        listOf(
            fieldWithPath("sellQuantity").type(NUMBER).description("판매수량"),
            fieldWithPath("sellAmount").type(NUMBER).description("판매금액"),
            fieldWithPath("refundQuantity").type(NUMBER).description("환불수량"),
            fieldWithPath("refundAmount").type(NUMBER).description("환불금액"),
            fieldWithPath("exchangeQuantity").type(NUMBER).description("교환수량"),
            fieldWithPath("exchangeAmount").type(NUMBER).description("교환금액"),
            fieldWithPath("tradeQuantity").type(NUMBER).description("거래수량"),
            fieldWithPath("tradeAmount").type(NUMBER).description("거래금액"),
            fieldWithPath("memberDiscounts").type(NUMBER).description("회원할인"),
            fieldWithPath("affiliateDiscounts").type(NUMBER)
                .description("제휴할인"),
            fieldWithPath("otherDiscounts").type(NUMBER).description("기타할인"),
            fieldWithPath("couponDiscounts").type(NUMBER)
                .description("쿠폰(무신사)할인"),
            fieldWithPath("partnerCouponDiscounts").type(NUMBER)
                .description("쿠폰(업체)할인"),
            fieldWithPath("pointDiscounts").type(NUMBER).description("적립금할인"),
            fieldWithPath("prePointDiscounts").type(NUMBER)
                .description("선적립금할인"),
            fieldWithPath("cartDiscounts").type(NUMBER).description("장바구니할인"),
            fieldWithPath("groupDiscounts").type(NUMBER).description("그룹할인"),
            fieldWithPath("totalDiscounts").type(NUMBER)
                .description("소계(총 할인액)"),
            fieldWithPath("discountRate").type(NUMBER).description("할인율"),
            fieldWithPath("paymentFees").type(NUMBER).description("결제수수료"),
            fieldWithPath("paymentAmount").type(NUMBER).description("결제금액"),
            fieldWithPath("sales").type(NUMBER).description("매출"),
            fieldWithPath("originalPrice").type(NUMBER).description("원가"),
            fieldWithPath("profit").type(NUMBER).description("이익"),
            fieldWithPath("profitMargin").type(NUMBER).description("이익률"),
            fieldWithPath("purchasesTradeAmount").type(NUMBER)
                .description("매입상품_거래금액"),
            fieldWithPath("purchasesDiscounts").type(NUMBER)
                .description("매입상품_할인"),
            fieldWithPath("purchasesPaymentFees").type(NUMBER)
                .description("매입상품_결제수수료"),
            fieldWithPath("purchasesPaymentAmount").type(NUMBER)
                .description("매입상품_결제금액"),
            fieldWithPath("purchasesOriginalPrice").type(NUMBER)
                .description("매입상품_원가"),
            fieldWithPath("purchasesProfit").type(NUMBER)
                .description("매입상품_이익"),
            fieldWithPath("purchasesProfitMargin").type(NUMBER)
                .description("매입상품_이익율"),
            fieldWithPath("purchasesRatio").type(NUMBER).description("매입상품_비중"),
            fieldWithPath("partnerTradeAmount").type(NUMBER)
                .description("입점상품_거래금액"),
            fieldWithPath("partnerDiscounts").type(NUMBER)
                .description("입점상품_할인"),
            fieldWithPath("partnerPaymentFees").type(NUMBER)
                .description("입점상품_결제수수료"),
            fieldWithPath("partnerPaymentAmount").type(NUMBER)
                .description("입점상품_결제금액"),
            fieldWithPath("partnerSellFees").type(NUMBER)
                .description("입점상품_판매수수료"),
            fieldWithPath("partnerFees").type(NUMBER).description("입점상품_수수료"),
            fieldWithPath("partnerSellGrants").type(NUMBER)
                .description("입점상품_판매지원금"),
            fieldWithPath("partnerProfitMargin").type(NUMBER)
                .description("입점상품_이익률"),
            fieldWithPath("salesExcludedVAT").type(NUMBER)
                .description("매출(VAT별도)"),
            fieldWithPath("originalPriceExcludedVAT").type(NUMBER)
                .description("원가(VAT별도)"),
            fieldWithPath("profitExcludedVAT").type(NUMBER)
                .description("이익(VAT별도)"),
            fieldWithPath("profitMarginExcludedVAT").type(NUMBER)
                .description("이익율(VAT별도)")
        )
    )
    return 명세서
}
