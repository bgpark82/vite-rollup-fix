package com.musinsa.stat.sales.controller

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

fun 매출통계_조회_요청값_명세(): MutableList<ParameterDescriptor> {
    val 명세서: MutableList<ParameterDescriptor> = ArrayList()

    // TODO 필수값 여부를 REST Docs 에 바로 노출
    // TODO Enum 값을 표 방식으로 제공
    명세서.addAll(
        listOf(
            parameterWithName("startDate").description("시작날짜(8자리 yyyyMMdd). 필수값"),
            parameterWithName("endDate").description("종료날짜(8자리 yyyyMMdd). 필수값"),
            parameterWithName("tag").description("태그").optional(),
            parameterWithName("salesStart").description(
                "매출시점. 필수값" +
                        "SHIPPING_REQUEST(\"출고요청\")," +
                        "PURCHASE_CONFIRM(\"구매확정\")"
            ),
            parameterWithName("partnerId").description("업체").optional(),
            parameterWithName("category").description("카테고리").optional(),
            parameterWithName("styleNumber").description("스타일넘버").optional(),
            parameterWithName("goodsNumber").description("상품코드").optional(),
            parameterWithName("brandId").description("브랜드").optional(),
            parameterWithName("couponNumber").description("쿠폰").optional(),
            parameterWithName("adCode").description("광고코드").optional(),
            parameterWithName("specialtyCode").description("전문관코드").optional(),
            parameterWithName("mdId").description("담당MD").optional(),
            parameterWithName("orderBy").description(
                "정렬키. 필수값" +
                        "    adCode(\"광고코드\"),\n" +
                        "    adType(\"광고구분\"),\n" +
                        "    adName(\"광고명\"),\n" +
                        "    brandId(\"브랜드\"),\n" +
                        "    brandName(\"브랜드명\"),\n" +
                        "    partnerName(\"업체명\"),\n" +
                        "    mdId(\"담당MD\"),\n" +
                        "    largeCategoryCode(\"대분류\"),\n" +
                        "    mediumCategoryCode(\"중분류\"),\n" +
                        "    smallCategoryCode(\"소분류\"),\n" +
                        "    category(\"카테고리명\"),\n" +
                        "    couponNumber(\"쿠폰번호\"),\n" +
                        "    couponTypeDescription(\"쿠폰구분\"),\n" +
                        "    couponName(\"쿠폰명\"),\n" +
                        "    couponApplyType(\"쿠폰사용구분\"),\n" +
                        "    couponIssueAmount(\"발행금액\"),\n" +
                        "    date(\"date\"),\n" +
                        "    goodsNumber(\"상품번호\"),\n" +
                        "    goodsName(\"상품명\"),\n" +
                        "    styleNumber(\"스타일넘버\"),\n" +
                        "    goodsStatusName(\"상품상태\"),\n" +
                        "    partnerId(\"업체코드\"),\n" +
                        "    partnerType(\"업체구분\"),\n" +
                        "    sellQuantity(\"판매수량\"),\n" +
                        "    sellAmount(\"판매금액\"),\n" +
                        "    refundQuantity(\"환불수량\"),\n" +
                        "    refundAmount(\"환불금액\"),\n" +
                        "    exchangeQuantity(\"교환수량\"),\n" +
                        "    exchangeAmount(\"교환금액\"),\n" +
                        "    tradeQuantity(\"거래수량\"),\n" +
                        "    tradeAmount(\"거래금액\"),\n" +
                        "    memberDiscounts(\"회원할인\"),\n" +
                        "    affiliateDiscounts(\"제휴할인\"),\n" +
                        "    otherDiscounts(\"기타할인\"),\n" +
                        "    couponDiscounts(\"쿠폰(무신사)할인\"),\n" +
                        "    partnerCouponDiscounts(\"쿠폰(업체)할인\"),\n" +
                        "    pointDiscounts(\"적립금할인\"),\n" +
                        "    prePointDiscounts(\"선적립금할인\"),\n" +
                        "    cartDiscounts(\"장바구니할인\"),\n" +
                        "    groupDiscounts(\"그룹할인\"),\n" +
                        "    totalDiscounts(\"소계\"),\n" +
                        "    discountRate(\"할인율\"),\n" +
                        "    paymentFees(\"결제수수료\"),\n" +
                        "    paymentAmount(\"결제금액\"),\n" +
                        "    sales(\"매출\"),\n" +
                        "    originalPrice(\"원가\"),\n" +
                        "    profit(\"이익\"),\n" +
                        "    profitMargin(\"이익률\"),\n" +
                        "    purchasesTradeAmount(\"매입상품_거래금액\"),\n" +
                        "    purchasesDiscounts(\"매입상품_할인\"),\n" +
                        "    purchasesPaymentFees(\"매입상품_결제수수료\"),\n" +
                        "    purchasesPaymentAmount(\"매입상품_결제금액\"),\n" +
                        "    purchasesOriginalPrice(\"매입상품_원가\"),\n" +
                        "    purchasesProfit(\"매입상품_이익\"),\n" +
                        "    purchasesProfitMargin(\"매입상품_이익율\"),\n" +
                        "    purchasesRatio(\"매입상품_비중\"),\n" +
                        "    partnerTradeAmount(\"입점상품_거래금액\"),\n" +
                        "    partnerDiscounts(\"입점상품_할인\"),\n" +
                        "    partnerPaymentFees(\"입점상품_결제수수료\"),\n" +
                        "    partnerPaymentAmount(\"입점상품_결제금액\"),\n" +
                        "    partnerSellFees(\"입점상품_판매수수료\"),\n" +
                        "    partnerFees(\"입점상품_수수료\"),\n" +
                        "    partnerSellGrants(\"입점상품_판매지원금\"),\n" +
                        "    partnerProfitMargin(\"입점상품_이익률\"),\n" +
                        "    salesExcludedVAT(\"매출(VAT별도)\"),\n" +
                        "    originalPriceExcludedVAT(\"원가(VAT별도)\"),\n" +
                        "    profitExcludedVAT(\"이익(VAT별도)\"),\n" +
                        "    profitMarginExcludedVAT(\"이익률(VAT별도)\")" +
                        ""
            )
        )
    )

    return 명세서
}