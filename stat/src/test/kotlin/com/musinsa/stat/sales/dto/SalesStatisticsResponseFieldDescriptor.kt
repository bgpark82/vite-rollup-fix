package com.musinsa.stat.sales.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

/**
 * 최종 응답값 명세
 */
fun 매출통계_명세(명세: MutableList<FieldDescriptor>): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = mutableListOf()
    명세서.addAll(
        applyPathPrefix("sum.", 매출통계_공통_명세())
    )
    명세서.addAll(
        applyPathPrefix("average.", 매출통계_공통_명세())
    )
    명세서.addAll(
        applyPathPrefix("content.[].", 명세)
    )
    명세서.addAll(
        listOf(
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER)
                .description("모든 페이지 갯수"),
            fieldWithPath("page").type(JsonFieldType.NUMBER)
                .description("현재 페이지"),
            fieldWithPath("pageSize").type(JsonFieldType.NUMBER)
                .description("페이지 사이즈"),
            fieldWithPath("totalItems").type(JsonFieldType.NUMBER)
                .description("모든 아이템 갯수"),
            fieldWithPath("sql").type(JsonFieldType.STRING)
                .description("브릭레인에 실제로 실행된 SQL")
        )
    )
    return 명세서
}
