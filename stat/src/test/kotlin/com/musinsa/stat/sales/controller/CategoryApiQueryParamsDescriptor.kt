package com.musinsa.stat.sales.controller

import com.musinsa.common.restdoc.ENUM_LINK_DOCS_BUILDER
import com.musinsa.stat.restdoc.GOODS_KIND_DOCUMENT_URL
import com.musinsa.stat.restdoc.ORDER_BY_DOCUMENT_URL
import com.musinsa.stat.restdoc.ORDER_DIRECTION_DOCUMENT_URL
import com.musinsa.stat.restdoc.PARTNER_TYPE_DOCUMENT_URL
import com.musinsa.stat.restdoc.SALES_FUNNEL_DOCUMENT_URL
import com.musinsa.stat.restdoc.SALES_START_DOCUMENT_URL
import com.musinsa.stat.sales.service.RETRIEVE_LIMIT_YEAR
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

private const val 리스트_설명 = "(List). 최대: "

fun 카테고리별_매출통계_조회_요청값_명세(): MutableList<ParameterDescriptor> {
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
            parameterWithName("tag").description(
                "태그".plus(리스트_설명).plus(TAG_SIZE_MAX)
            ).optional(),
            ENUM_LINK_DOCS_BUILDER(
                "salesStart",
                SALES_START_DOCUMENT_URL,
                "매출시점"
            ),
            parameterWithName("partnerId").description(
                "업체".plus(리스트_설명).plus(PARTNER_ID_SIZE_MAX)
                    .plus(GOODS_SALES_STATISTICS_VALID_PARAM_CONDITION_DOCS)
            )
                .optional(),
            parameterWithName("category").description(
                "카테고리".plus(리스트_설명).plus(
                    CATEGORY_SIZE_MAX
                )
            )
                .optional(),
            parameterWithName("styleNumber").description(
                "스타일넘버".plus(리스트_설명).plus(
                    STYLE_NUMBER_SIZE_MAX
                )
            )
                .optional(),
            parameterWithName("goodsNumber").description(
                "상품코드".plus(리스트_설명).plus(GOODS_NUMBER_SIZE_MAX)
                    .plus(GOODS_SALES_STATISTICS_VALID_PARAM_CONDITION_DOCS)
            ).optional(),
            parameterWithName("brandId").description(
                "브랜드".plus(리스트_설명).plus(
                    BRAND_ID_SIZE_MAX
                ).plus(GOODS_SALES_STATISTICS_VALID_PARAM_CONDITION_DOCS)
            )
                .optional(),
            parameterWithName("couponNumber").description(
                "쿠폰".plus(리스트_설명).plus(
                    COUPON_NUMBER_SIZE_MAX
                )
            )
                .optional(),
            parameterWithName("adCode").description(
                "광고코드".plus(리스트_설명).plus(
                    AD_CODE_SIZE_MAX
                )
            )
                .optional(),
            parameterWithName("specialtyCode").description(
                "전문관코드".plus(리스트_설명).plus(
                    SPECIALTY_CODE_SIZE_MAX
                ).plus(GOODS_SALES_STATISTICS_VALID_PARAM_CONDITION_DOCS)
            )
                .optional(),
            parameterWithName("mdId").description(
                "담당MD".plus(리스트_설명).plus(
                    MD_ID_SIZE_MAX
                ).plus(GOODS_SALES_STATISTICS_VALID_PARAM_CONDITION_DOCS)
            )
                .optional(),
            ENUM_LINK_DOCS_BUILDER(
                "partnerType",
                PARTNER_TYPE_DOCUMENT_URL,
                "업체구분"
            ).optional(),
            ENUM_LINK_DOCS_BUILDER(
                "goodsKind",
                GOODS_KIND_DOCUMENT_URL,
                "품목(상품 옵션)"
            ).optional(),
            ENUM_LINK_DOCS_BUILDER(
                "salesFunnel",
                SALES_FUNNEL_DOCUMENT_URL,
                "판매 경로"
            ).optional(),
            parameterWithName("adHours").description(
                "광고집계시간(단위: 초) : 3시간, 1일, 7일"
            ).optional(),
            ENUM_LINK_DOCS_BUILDER(
                "orderBy",
                ORDER_BY_DOCUMENT_URL,
                "정렬키. 기본값: ".plus(CATEGORY_ORDER_BY_DEFAULT_VALUE)
            ).optional(),
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
            ).optional()
        )
    )

    return 명세서
}
