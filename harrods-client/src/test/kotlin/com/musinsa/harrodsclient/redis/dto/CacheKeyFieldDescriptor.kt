package com.musinsa.harrodsclient.redis.dto

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation

fun 명세(): MutableList<FieldDescriptor> {
    return mutableListOf(
        PayloadDocumentation.fieldWithPath("keys")
            .type(JsonFieldType.ARRAY).description("조회할 키.".plus("최소: $KEY_SIZE_MIN. 최대: $KEY_SIZE_MAX"))
    )
}
