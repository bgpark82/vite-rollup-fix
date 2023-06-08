package com.musinsa.stat.sales.controller

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

fun 매출통계_조회_요청값_명세(): MutableList<ParameterDescriptor> {
    val 명세서: MutableList<ParameterDescriptor> = ArrayList()

    // TODO Enum 값을 표 방식으로 제공
    명세서.addAll(
        listOf(
            parameterWithName("startDate").description("시작날짜(8자리 yyyyMMdd). 월별매출통계도 8자리를 보내면 서버에서 6자리로 자른다."),
            parameterWithName("endDate").description(
                "종료날짜(8자리 yyyyMMdd). 월별매출통계도 8자리를 보내면 서버에서 6자리로 자른다." +
                        "최대 조회기간은 1년"
            ),
            parameterWithName("tag").description("태그. 리스트 형태(청바지,반바지)로 보내면 된다.")
                .optional(),
            parameterWithName("salesStart").description(
                "매출시점. " +
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
                "link:enum/order-by.html[OrderBy 보기,window=_new]"
            )
        )
    )

    return 명세서
}