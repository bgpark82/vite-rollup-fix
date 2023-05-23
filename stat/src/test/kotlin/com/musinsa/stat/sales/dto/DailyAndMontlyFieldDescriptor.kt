package com.musinsa.stat.sales.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation

fun MutableList<FieldDescriptor>.일별_월별_명세() {
    this.addAll(
        listOf(
            PayloadDocumentation.fieldWithPath("date")
                .type(STRING).description("일자/월")
        ),
    )
    this.매출통계_공통_명세()
}