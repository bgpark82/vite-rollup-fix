package com.musinsa.stat.sales.controller

import com.musinsa.stat.restdoc.ENUM_LINK_DOCS_BUILDER
import com.musinsa.stat.restdoc.enumcontroller.ORDER_BY_DOCUMENT_URL
import com.musinsa.stat.restdoc.enumcontroller.ORDER_DIRECTION_DOCUMENT_URL
import com.musinsa.stat.restdoc.enumcontroller.SALES_START_DOCUMENT_URL
import com.musinsa.stat.sales.service.RETRIEVE_LIMIT_YEAR
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

fun 매출통계_조회_요청값_명세(): MutableList<ParameterDescriptor> {
    val 명세서: MutableList<ParameterDescriptor> = ArrayList()

    명세서.addAll(
        listOf(
            parameterWithName("startDate").description(
                "시작날짜(".plus(DATE_FORMAT).plus(")")
            ),
            parameterWithName("endDate").description(
                "종료날짜(".plus(DATE_FORMAT).plus("). ")
                    .plus("최대 조회기간: ".plus(RETRIEVE_LIMIT_YEAR).plus("년"))
            ),
            parameterWithName("tag").description("태그. 리스트 형태(청바지,반바지)")
                .optional(),
            ENUM_LINK_DOCS_BUILDER(
                "salesStart",
                SALES_START_DOCUMENT_URL,
                "매출시점"
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
            ENUM_LINK_DOCS_BUILDER("orderBy", ORDER_BY_DOCUMENT_URL, "정렬키"),
            ENUM_LINK_DOCS_BUILDER(
                "orderDirection",
                ORDER_DIRECTION_DOCUMENT_URL,
                "정렬방향. 기본값: ".plus(ORDER_DIRECTION_DEFAULT_VALUE)
            ).optional(),
            parameterWithName("pageSize").description(
                "페이지 사이즈. 기본값: ".plus(
                    PAGE_SIZE_DEFAULT_VALUE
                )
            )
                .optional(),
            parameterWithName("page").description(
                "페이지. 기본값: ".plus(
                    PAGE_DEFAULT_VALUE
                )
            ).optional(),
        )
    )

    return 명세서
}