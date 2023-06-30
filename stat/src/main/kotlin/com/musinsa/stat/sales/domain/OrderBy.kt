package com.musinsa.stat.sales.domain

// TODO enum name 을 Kotlin 공식 컨벤션에 맞춘다.
/**
 * 정렬값 관리
 */
@Suppress("unused", "EnumEntryName")
enum class OrderBy(
    /**
     * 데이터브릭스 쿼리에 저장된 Alias
     * 주의! 해당 값은 데이터브릭스 쿼리에 적용된 Alias 와 동시에 수정해야 한다.
     */
    val alias: String
) {
    adCode("광고코드"),
    adType("광고구분"),
    adName("광고명"),
    brandId("브랜드"),
    brandName("브랜드명"),
    partnerName("업체명"),
    mdId("담당MD"),
    largeCategoryCode("대분류"),
    mediumCategoryCode("중분류"),
    smallCategoryCode("소분류"),
    category("카테고리명"),
    couponNumber("쿠폰번호"),
    couponTypeDescription("쿠폰구분"),
    couponName("쿠폰명"),
    couponApplyType("쿠폰사용구분"),
    couponIssueAmount("발행금액"),
    date("date"),
    goodsNumber("상품번호"),
    goodsName("상품명"),
    styleNumber("스타일넘버"),
    goodsStatusName("상품상태"),
    partnerId("업체코드"),
    partnerType("업체구분"),
    sellQuantity("판매수량"),
    sellAmount("판매금액"),
    refundQuantity("환불수량"),
    refundAmount("환불금액"),
    exchangeQuantity("교환수량"),
    exchangeAmount("교환금액"),
    tradeQuantity("거래수량"),
    tradeAmount("거래금액"),
    memberDiscounts("회원할인"),
    affiliateDiscounts("제휴할인"),
    otherDiscounts("기타할인"),
    couponDiscounts("쿠폰(무신사)할인"),
    partnerCouponDiscounts("쿠폰(업체)할인"),
    pointDiscounts("적립금할인"),
    prePointDiscounts("선적립금할인"),
    cartDiscounts("장바구니할인"),
    groupDiscounts("그룹할인"),
    totalDiscounts("소계"),
    discountRate("할인율"),
    paymentFees("결제수수료"),
    paymentAmount("결제금액"),
    sales("매출"),
    originalPrice("원가"),
    profit("이익"),
    profitMargin("이익률"),
    purchasesTradeAmount("매입상품_거래금액"),
    purchasesDiscounts("매입상품_할인"),
    purchasesPaymentFees("매입상품_결제수수료"),
    purchasesPaymentAmount("매입상품_결제금액"),
    purchasesOriginalPrice("매입상품_원가"),
    purchasesProfit("매입상품_이익"),
    purchasesProfitMargin("매입상품_이익율"),
    purchasesRatio("매입상품_비중"),
    partnerTradeAmount("입점상품_거래금액"),
    partnerDiscounts("입점상품_할인"),
    partnerPaymentFees("입점상품_결제수수료"),
    partnerPaymentAmount("입점상품_결제금액"),
    partnerSellFees("입점상품_판매수수료"),
    partnerFees("입점상품_수수료"),
    partnerSellGrants("입점상품_판매지원금"),
    partnerProfitMargin("입점상품_이익률"),
    salesExcludedVAT("매출(VAT별도)"),
    originalPriceExcludedVAT("원가(VAT별도)"),
    profitExcludedVAT("이익(VAT별도)"),
    profitMarginExcludedVAT("이익률(VAT별도)")
}
