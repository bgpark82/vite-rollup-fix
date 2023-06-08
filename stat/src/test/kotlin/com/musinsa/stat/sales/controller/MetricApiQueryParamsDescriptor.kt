package com.musinsa.stat.sales.controller

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

fun 매출통계_조회_요청값_명세(): MutableList<ParameterDescriptor> {
    val 명세서: MutableList<ParameterDescriptor> = ArrayList()

    명세서.addAll(
        listOf(
            parameterWithName("startDate").description("시작날짜(yyyyMMdd). 월별매출통계의 경우만 서버에서 6자리로 trim"),
            parameterWithName("endDate").description(
                "종료날짜(yyyyMMdd). 월별매출통계의 경우만 서버에서 6자리로 trim" +
                        "최대 조회기간: 1년"
            ),
            parameterWithName("tag").description("태그. 리스트 형태(청바지,반바지)")
                .optional(),
            parameterWithName("salesStart").description(
                "link:../enum/sales-start.html[매출시점,window=blank]"
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
                "link:../enum/order-by.html[정렬키,window=blank]"
            )
        )
    )

    return 명세서
}