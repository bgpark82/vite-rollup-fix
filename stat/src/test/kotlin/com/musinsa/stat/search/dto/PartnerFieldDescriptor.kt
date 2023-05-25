package com.musinsa.stat.search.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation

fun 업체_명세(): MutableList<FieldDescriptor> {
    val 명세서: MutableList<FieldDescriptor> = mutableListOf()

    명세서.addAll(
        PayloadDocumentation.applyPathPrefix(
            "[].",
            listOf(
                PayloadDocumentation.fieldWithPath("partnerType")
                    .type(JsonFieldType.STRING)
                    .description("업체구분(1:공급,2:입점,3:제휴,4:판매,9:본사)"),
                PayloadDocumentation.fieldWithPath("partnerId")
                    .type(JsonFieldType.STRING)
                    .description("업체 ID"),
                PayloadDocumentation.fieldWithPath("partnerName")
                    .type(JsonFieldType.STRING)
                    .description("업체명"),
                PayloadDocumentation.fieldWithPath("ceo")
                    .type(JsonFieldType.STRING)
                    .description("대표자"),
                PayloadDocumentation.fieldWithPath("businessLicenseNumber")
                    .type(JsonFieldType.STRING)
                    .description("사업등록번호"),
                PayloadDocumentation.fieldWithPath("mdName")
                    .type(JsonFieldType.STRING)
                    .description("담당MD 이름"),
            )
        )
    )

    return 명세서
}