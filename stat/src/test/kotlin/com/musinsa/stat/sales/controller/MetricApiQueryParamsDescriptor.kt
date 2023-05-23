package com.musinsa.stat.sales.controller

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

fun 매출통계_조회_요청값_명세(): MutableList<ParameterDescriptor> {
    val 명세서: MutableList<ParameterDescriptor> = ArrayList()

    명세서.addAll(
        listOf(
            parameterWithName("startDate").description("시작날짜(8자리 yyyyMMdd)"),
            parameterWithName("endDate").description("종료날짜(8자리 yyyyMMdd)"),
            parameterWithName("tag").description("태그").optional(),
            parameterWithName("salesStart").description("매출시점"),
            parameterWithName("partnerId").description("업체").optional(),
            parameterWithName("category").description("카테고리").optional(),
            parameterWithName("styleNumber").description("스타일넘버").optional(),
            parameterWithName("goodsNumber").description("상품코드").optional(),
            parameterWithName("brandId").description("브랜드").optional(),
            parameterWithName("couponNumber").description("쿠폰").optional(),
            parameterWithName("adCode").description("광고코드").optional(),
            parameterWithName("specialtyCode").description("전문관코드").optional(),
            parameterWithName("mdId").description("담당MD").optional(),
            parameterWithName("orderBy").description("정렬키")
        )
    )

    return 명세서
}