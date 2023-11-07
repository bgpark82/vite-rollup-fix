package com.musinsa.stat.sales.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

fun 카테고리별_명세(): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = mutableListOf()

    명세서.addAll(
        listOf(
            fieldWithPath("largeCategoryCode")
                .type(STRING).description("대분류"),
            fieldWithPath("mediumCategoryCode")
                .type(STRING).description("중분류"),
            fieldWithPath("smallCategoryCode")
                .type(STRING).description("소분류"),
            fieldWithPath("largeCategory")
                .type(STRING).description("대분류 카테고리명"),
            fieldWithPath("mediumCategory")
                .type(STRING).description("중분류 카테고리명"),
            fieldWithPath("smallCategory")
                .type(STRING).description("소분류 카테고리명")
        )
    )

    명세서.addAll(매출통계_공통_명세())

    return 명세서
}
