package com.musinsa.stat.search.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

fun 브랜드_명세(): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = mutableListOf()

    명세서.addAll(
        applyPathPrefix(
            "[].",
            listOf(
                fieldWithPath("brandType")
                    .type(JsonFieldType.STRING)
                    .description("브랜드분류(S:시스템,U:사용자,G:해외전용)"),
                fieldWithPath("brandId")
                    .type(JsonFieldType.STRING)
                    .description("브랜드"),
                fieldWithPath("brandName")
                    .type(JsonFieldType.STRING)
                    .description("브랜드명"),
                fieldWithPath("brandNameEn")
                    .type(JsonFieldType.STRING)
                    .description("브랜드명_영문"),
                fieldWithPath("used")
                    .type(JsonFieldType.BOOLEAN)
                    .description("사용여부"),
                fieldWithPath("isGlobal")
                    .type(JsonFieldType.BOOLEAN)
                    .description("글로벌 여부")
            )
        )
    )

    return 명세서
}
