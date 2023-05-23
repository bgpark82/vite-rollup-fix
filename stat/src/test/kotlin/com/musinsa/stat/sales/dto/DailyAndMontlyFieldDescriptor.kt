package com.musinsa.stat.sales.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation

fun 일별_월별_명세(): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = mutableListOf()

    명세서.addAll(
        listOf(
            PayloadDocumentation.fieldWithPath("date")
                .type(STRING).description("일자/월"),
        ),
    )

    명세서.addAll(매출통계_공통_명세())

    return 명세서
}